package bot.farm.redeemer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class BotService {
  private final MessageService messageService;

  public SendMessage handleMessage(Message message) {
    final Long chatId = message.getChatId();
    //final String text = message.getText();

    return messageService.createMenuMessage(String.valueOf(chatId), "Выберите действие:");
  }

  public SendMessage handleCallback(CallbackQuery callback) {
    Message message = callback.getMessage();
    String chatId = String.valueOf(callback.getMessage().getChatId());
    final String text = callback.getData();
    //Long userId = callback.getFrom().getId();

    switch (text) {
      case "/insert_promo" -> {
        return messageService.createMessage(chatId, "Pooop");
      }
      case "/insert_igg_id" -> {
        return messageService.createMessage(chatId, "Qoop");
      }
      case "/list_igg_id" -> {
        return messageService.createMessage(chatId, List.of("1", "23").toString());
      }
      case "/delete_igg_id" -> {
        return messageService.createMessage(chatId, "Будет удален id");
      }
      default -> {
        return messageService.createMenuMessage(chatId, "Выберите действие:");
      }
    }
  }
}
