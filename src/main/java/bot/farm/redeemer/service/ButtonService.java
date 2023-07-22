package bot.farm.redeemer.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class ButtonService {

  public List<List<InlineKeyboardButton>> createInlineButton() {
    List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("INSERT TEXT");
    inlineKeyboardButton1.setCallbackData("/INSERT LINK");

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

  public InlineKeyboardMarkup setInlineKeyMarkup(List<List<InlineKeyboardButton>> inlineList) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    inlineKeyboardMarkup.setKeyboard(inlineList);
    return inlineKeyboardMarkup;
  }

}
