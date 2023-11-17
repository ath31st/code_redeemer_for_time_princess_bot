package bot.farm.redeemer.service;

import bot.farm.redeemer.config.ConfigFromFile;
import bot.farm.redeemer.exception.IggAccountException;
import bot.farm.redeemer.exception.PromoCodeException;
import bot.farm.redeemer.util.Link;
import bot.farm.redeemer.util.Phrases;
import bot.farm.redeemer.util.UserState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class SwitchService {
  private final MessageService messageService;
  private final PromoCodeService promoCodeService;
  private final IggAccountService iggAccountService;
  private final PromoCodeRedeemService promoCodeRedeemService;
  private final ConfigFromFile configFromFile;
  // Объявление кеш-хранилища для состояний пользователей
  private final Map<String, UserState> userStateHashMap
      = Collections.synchronizedMap(new LinkedHashMap<>() {
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, UserState> eldest) {
      return size() > 10;
    }
  });
  private Link outputSource = Link.DUPLICATE_IN_GROUP_ON;

  public List<SendMessage> handleMessage(Message message) {
    List<SendMessage> messages = new ArrayList<>();
    String chatId = String.valueOf(message.getChatId());
    final String text = message.getText();

    switch (getStateForUser(chatId)) {
      case INPUT_ID -> {
        String response;
        try {
          response = iggAccountService.saveAccounts(text);
        } catch (IggAccountException e) {
          response = e.getMessage();
        } finally {
          setStateForUser(chatId, UserState.DEFAULT);
        }
        messages.add(messageService.createMessage(chatId, response));
      }
      case INPUT_PROMO -> {
        try {
          promoCodeService.checkCorrectPromoCode(text);
          promoCodeService.checkExistsPromoCode(text);
          String response = promoCodeRedeemService.redeemPromoCode(
              text, iggAccountService.getAccounts());
          messages.add(messageService.createMessage(chatId, response));
          if (outputSource == Link.DUPLICATE_IN_GROUP_ON && configFromFile.getIdGroup() != 0) {
            String groupId = String.valueOf(configFromFile.getIdGroup());
            String groupResp = getResponseForGroupChat(text, message);
            messages.add(messageService.createMessage(groupId, groupResp));
          }
        } catch (PromoCodeException e) {
          messages.add(messageService.createMessage(chatId, e.getMessage()));
        } finally {
          setStateForUser(chatId, UserState.DEFAULT);
        }
      }
      case DELETE_ID -> {
        String response;
        try {
          response = iggAccountService.deleteAccounts(text);
        } catch (IggAccountException e) {
          response = e.getMessage();
        } finally {
          setStateForUser(chatId, UserState.DEFAULT);
        }
        messages.add(messageService.createMessage(chatId, response));
      }
      default -> messages.add(configFromFile.getIdSet().contains(message.getChatId())
          ? messageService.createMenuMessage(chatId, Phrases.CHOOSE_ACTION.getText())
          : messageService.createShortMenuMessage(chatId, Phrases.CHOOSE_ACTION.getText()));
    }

    return messages;
  }

  public SendMessage handleCallback(CallbackQuery callback) {
    SendMessage sendMessage;
    String chatId = String.valueOf(callback.getMessage().getChatId());
    final String text = callback.getData();

    switch (text) {
      case "/input_promo" -> {
        setStateForUser(chatId, UserState.INPUT_PROMO);
        sendMessage = messageService.createMessage(chatId, Phrases.ENTER_PROMO_CODE.getText());
      }
      case "/input_igg_id" -> {
        setStateForUser(chatId, UserState.INPUT_ID);
        sendMessage = messageService.createMessage(chatId, Phrases.ENTER_ACCOUNT_DETAILS.getText());
      }
      case "/list_igg_id" -> sendMessage = messageService.createListMessageWithDeleteMenuButton(
          chatId, iggAccountService.getAccounts());
      case "/delete_igg_id" -> {
        setStateForUser(chatId, UserState.DELETE_ID);
        sendMessage = messageService.createMessage(chatId,
            Phrases.ENTER_ACCOUNT_DETAILS_FOR_DELETE.getText());
      }
      case "/duplicate_output" -> sendMessage = messageService.createSwitchOutputMenuMessage(chatId,
          Phrases.ENABLE_DISABLE_INFO.getText());
      case "/duplicate_in_group_off" -> {
        outputSource = Link.DUPLICATE_IN_GROUP_OFF;
        sendMessage = messageService.createMessage(chatId, Phrases.PROMO_REPORTS_INFO.getText());
      }
      case "/duplicate_in_group_on" -> {
        outputSource = Link.DUPLICATE_IN_GROUP_ON;
        sendMessage = messageService.createMessage(chatId,
            Phrases.FULL_PROMO_REPORTS_INFO.getText());
      }
      default -> {
        setStateForUser(chatId, UserState.DEFAULT);
        sendMessage = messageService.createMenuMessage(chatId, Phrases.CHOOSE_ACTION.getText());
      }
    }
    return sendMessage;
  }

  private UserState getStateForUser(String chatId) {
    return userStateHashMap.getOrDefault(chatId, UserState.DEFAULT);
  }

  private void setStateForUser(String chatId, UserState state) {
    userStateHashMap.put(chatId, state);
  }

  public boolean isUserChat(Update update) {
    return (update.hasMessage() && update.getMessage().getChat().isUserChat())
        || (update.hasCallbackQuery()
        && update.getCallbackQuery().getMessage().getChat().isUserChat());
  }

  private String getResponseForGroupChat(String text, Message message) {
    String nameOrUsername = message.getFrom().getFirstName() != null
        ? message.getFrom().getFirstName() : message.getFrom().getUserName();
    return String.format("Гордость гильдии %s успешно применил(а) промокод %s.",
        nameOrUsername, text);
  }
}
