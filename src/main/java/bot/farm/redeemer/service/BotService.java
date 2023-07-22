package bot.farm.redeemer.service;

import bot.farm.redeemer.util.UserState;
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
  private UserState state = UserState.DEFAULT;

  public SendMessage handleMessage(Message message) {
    final String chatId = String.valueOf(message.getChatId());
    final String text = message.getText();

    switch (state) {
      case INPUT_ID -> {

        return messageService.createMessage(chatId, "Выберите действие:");
      }
      default -> {
        return messageService.createMenuMessage(chatId, "Выберите действие:");
      }
    }
  }

  public SendMessage handleCallback(CallbackQuery callback) {
    Message message = callback.getMessage();
    String chatId = String.valueOf(callback.getMessage().getChatId());
    final String text = callback.getData();
    //Long userId = callback.getFrom().getId();

    switch (text) {
      case "/input_promo" -> {
        state = UserState.INPUT_PROMO;
        return messageService.createMessage(chatId,
            "Введите промокод для активации на аккаунты из списка");
      }
      case "/input_igg_id" -> {
        state = UserState.INPUT_ID;
        return messageService.createMessage(chatId,
            "Введите ID, которые нужно добавить. ID должны быть разделены запятыми. Пробелы роли не играют.");
      }
      case "/list_igg_id" -> {
        return messageService.createListMessageWithDeleteMenuButton(chatId, List.of("1", "23"));
      }
      case "/delete_igg_id" -> {
        state = UserState.DELETE_ID;
        return messageService.createMessage(chatId,
            "Введите ID, которые нужно удалить. ID должны быть разделены запятыми. Пробелы роли не играют.");
      }
      default -> {
        state = UserState.DEFAULT;
        return messageService.createMenuMessage(chatId, "Выберите действие:");
      }
    }
  }
}
