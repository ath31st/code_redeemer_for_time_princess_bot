package bot.farm.redeemer.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final ButtonService buttonService;

  public SendMessage createMessage(String chatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdownV2(true);
    sendMessage.enableHtml(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    return sendMessage;
  }

  public SendMessage createMenuMessage(String chatId, String message) {

    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }

  public SendMessage createNewsMessage(String chatId, String message) {
    SendMessage sendMessage = createMessage(chatId, message);
    InlineKeyboardMarkup inlineKeyboardMarkup =
        buttonService.setInlineKeyMarkup(buttonService.createInlineDeleteButton());
    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    return sendMessage;
  }
}