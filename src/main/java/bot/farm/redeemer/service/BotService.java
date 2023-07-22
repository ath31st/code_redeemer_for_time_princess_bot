package bot.farm.redeemer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class BotService {
  public SendMessage handleMessage(Message message) {
    final Long chatId = message.getChatId();
    final String text = message.getText();

    return sendMessage(chatId, text);
  }

  public SendMessage handleCallback(CallbackQuery callback) {
    Message message = callback.getMessage();
    Long chatId = message.getChatId();
    Long userId = callback.getFrom().getId();

    return sendMessage(chatId, "Wooop");
  }

  public SendMessage sendMessage(Long chatId, String text){
    return SendMessage.builder()
        .parseMode("HTML")
        .chatId(chatId.toString())
        //.replyMarkup(keyboard)
        .text(text)
        .build();
  }

  private EditMessageText editMessage(Message message, String text, InlineKeyboardMarkup keyboard){
    return EditMessageText.builder()
        .parseMode("HTML")
        .chatId(message.getChatId())
        .messageId(message.getMessageId())
        .replyMarkup(keyboard)
        .text(text)
        .build();
  }
}
