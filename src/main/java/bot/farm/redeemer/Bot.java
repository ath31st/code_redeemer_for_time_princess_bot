package bot.farm.redeemer;

import bot.farm.redeemer.service.SwitchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
  @Value("${telegram.bot.name}")
  private String botName;
  @Value("${telegram.bot.token}")
  private String botToken;
  private final SwitchService switchService;

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMsg = null;
    if (update.hasMessage() && update.getMessage().hasText()) {
      sendMsg = switchService.handleMessage(update.getMessage());
    }

    if (update.hasCallbackQuery()) {
      sendMsg = switchService.handleCallback(update.getCallbackQuery());
    }

    try {
      execute(sendMsg);
    } catch (TelegramApiException e) {
      if (e.getMessage().endsWith("[403] Forbidden: bot was blocked by the user")) {
        log.info("User with chatId {} has received the \"inactive\" status", sendMsg.getChatId());
      } else {
        log.error(e.getMessage());
      }
    }
  }
}
