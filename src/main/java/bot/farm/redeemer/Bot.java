package bot.farm.redeemer;

import bot.farm.redeemer.service.SwitchService;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Telegram bot implementation using TelegramLongPollingBot.
 * This class represents a Telegram bot that extends TelegramLongPollingBot,
 * handling updates and messages through the onUpdateReceived method. It is
 * annotated as a Spring Service and uses the SwitchService for handling messages.
 *
 * @see org.telegram.telegrambots.bots.TelegramLongPollingBot
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
  @Value("${telegram.bot.name}")
  private String botName;
  @Getter
  @Value("${telegram.bot.token}")
  private String botToken;
  private final SwitchService switchService;

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public void onUpdateReceived(Update update) {
    List<SendMessage> messages = new ArrayList<>();
    if (update.hasMessage() && update.getMessage().hasText() && switchService.isUserChat(update)) {
      messages.addAll(switchService.handleMessage(update.getMessage()));
    }

    if (update.hasCallbackQuery() && switchService.isUserChat(update)) {
      messages.add(switchService.handleCallback(update.getCallbackQuery()));
    }

    try {
      if (!messages.isEmpty()) {
        for (SendMessage m : messages) {
          execute(m);
        }
      }
    } catch (TelegramApiException e) {
      if (e.getMessage().endsWith("[403] Forbidden: bot was blocked by the user")) {
        log.info("Bot was blocked user from list receivers. Error text: {}", e.getMessage());
      } else {
        log.error(e.getMessage());
      }
    }
  }
}
