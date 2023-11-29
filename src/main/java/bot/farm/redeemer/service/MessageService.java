package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.IggAccount;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Service for creating and customizing Telegram messages with various functionalities.
 */
@Service
@RequiredArgsConstructor
public class MessageService {
  private final ButtonService buttonService;

  /**
   * Creates a simple text message.
   *
   * @param chatId  The ID of the chat where the message will be sent.
   * @param message The text content of the message.
   * @return A SendMessage object representing the text message.
   */
  public SendMessage createMessage(String chatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdownV2(true);
    sendMessage.enableHtml(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    return sendMessage;
  }

  /**
   * Creates a text message with an inline keyboard containing common actions.
   *
   * @param chatId  The ID of the chat where the message will be sent.
   * @param message The text content of the message.
   * @return A SendMessage object with an inline keyboard.
   */
  public SendMessage createMenuMessage(String chatId, String message) {
    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup
        = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  /**
   * Creates a text message with a short inline menu for specific actions.
   *
   * @param chatId  The ID of the chat where the message will be sent.
   * @param message The text content of the message.
   * @return A SendMessage object with a short inline menu.
   */
  public SendMessage createShortMenuMessage(String chatId, String message) {
    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup
        = buttonService.setInlineKeyMarkup(buttonService.createInlineShortMenuButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  /**
   * Creates a text message with an inline menu for switching output options.
   *
   * @param chatId  The ID of the chat where the message will be sent.
   * @param message The text content of the message.
   * @return A SendMessage object with an inline menu for switching output options.
   */
  public SendMessage createSwitchOutputMenuMessage(String chatId, String message) {
    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup
        = buttonService.setInlineKeyMarkup(buttonService.createSwitchOutputButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  /**
   * Creates a text message with a list of IggAccount objects and an inline button for deletion.
   *
   * @param chatId     The ID of the chat where the message will be sent.
   * @param listIggIds The list of IggAccount objects to be displayed.
   * @return A SendMessage object with a list and an inline button for deletion.
   */
  public SendMessage createListMessageWithDeleteMenuButton(
      String chatId, List<IggAccount> listIggIds) {
    String ids = listIggIds
        .stream()
        .map(IggAccount::toString)
        .collect(Collectors.joining("\n"));

    SendMessage sendMessage = createMessage(chatId, ids);
    InlineKeyboardMarkup inlineKeyboardMarkup =
        buttonService.setInlineKeyMarkup(buttonService.createInlineDeleteButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }
}