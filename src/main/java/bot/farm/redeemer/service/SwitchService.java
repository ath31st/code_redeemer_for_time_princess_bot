package bot.farm.redeemer.service;

import bot.farm.redeemer.config.IdSetReader;
import bot.farm.redeemer.exception.IggAccountException;
import bot.farm.redeemer.exception.PromoCodeException;
import bot.farm.redeemer.util.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class SwitchService {
  private final MessageService messageService;
  private final PromoCodeService promoCodeService;
  private final IggAccountService iggAccountService;
  private final PromoCodeRedeemService promoCodeRedeemService;
  private final IdSetReader idSetReader;
  private UserState state = UserState.DEFAULT;

  public SendMessage handleMessage(Message message) {
    SendMessage sendMessage;
    final String chatId = String.valueOf(message.getChatId());
    final String text = message.getText();

    switch (state) {
      case INPUT_ID -> {
        String response;
        try {
          response = iggAccountService.saveAccounts(text);
        } catch (IggAccountException e) {
          response = e.getMessage();
        } finally {
          state = UserState.DEFAULT;
        }
        sendMessage = messageService.createMessage(chatId, response);
      }
      case INPUT_PROMO -> {
        try {
          promoCodeService.checkCorrectPromoCode(text);
          promoCodeService.checkExistsPromoCode(text);
          String response = promoCodeRedeemService.redeemPromoCode(
              text, iggAccountService.getAccounts());
          sendMessage = messageService.createMessage(chatId, response);
        } catch (PromoCodeException e) {
          sendMessage = messageService.createMessage(chatId, e.getMessage());
        } finally {
          state = UserState.DEFAULT;
        }
      }
      case DELETE_ID -> {
        String response;
        try {
          response = iggAccountService.deleteAccounts(text);
        } catch (IggAccountException e) {
          response = e.getMessage();
        } finally {
          state = UserState.DEFAULT;
        }
        sendMessage = messageService.createMessage(chatId, response);
      }
      default -> sendMessage = idSetReader.getIdSet().contains(message.getChatId())
          ? messageService.createMenuMessage(chatId, "Выберите действие:")
          : messageService.createShortMenuMessage(chatId, "Выберите действие:");
    }

    return sendMessage;
  }

  public SendMessage handleCallback(CallbackQuery callback) {
    SendMessage sendMessage;
    String chatId = String.valueOf(callback.getMessage().getChatId());
    final String text = callback.getData();

    switch (text) {
      case "/input_promo" -> {
        state = UserState.INPUT_PROMO;
        sendMessage = messageService.createMessage(chatId,
            "Введите промокод для активации на аккаунты из списка");
      }
      case "/input_igg_id" -> {
        state = UserState.INPUT_ID;
        sendMessage = messageService.createMessage(chatId,
            "Введите ID, которые нужно добавить. "
                + "ID должны быть разделены запятыми. Пробелы роли не играют.");
      }
      case "/list_igg_id" -> sendMessage = messageService.createListMessageWithDeleteMenuButton(
          chatId, iggAccountService.getAccounts());
      case "/delete_igg_id" -> {
        state = UserState.DELETE_ID;
        sendMessage = messageService.createMessage(chatId,
            "Введите ID, которые нужно удалить. "
                + "ID должны быть разделены запятыми. Пробелы роли не играют.");
      }
      default -> {
        state = UserState.DEFAULT;
        sendMessage = messageService.createMenuMessage(chatId, "Выберите действие:");
      }
    }
    return sendMessage;
  }
}
