package bot.farm.redeemer.service;

import static bot.farm.redeemer.util.Phrases.B_ADDITIONAL_OUTPUT;
import static bot.farm.redeemer.util.Phrases.B_DELETE_MODE;
import static bot.farm.redeemer.util.Phrases.B_ENTER_IGG_ID;
import static bot.farm.redeemer.util.Phrases.B_ENTER_PROMO_CODE;
import static bot.farm.redeemer.util.Phrases.B_LIST_IGG_ID;
import static bot.farm.redeemer.util.Phrases.B_OFF;
import static bot.farm.redeemer.util.Phrases.B_ON;

import bot.farm.redeemer.util.Link;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class ButtonService {
  public List<List<InlineKeyboardButton>> createInlineButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText(B_ENTER_PROMO_CODE.getText());
    inlineKeyboardButton1.setCallbackData(Link.INPUT_PROMO.value);

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText(B_ENTER_IGG_ID.getText());
    inlineKeyboardButton2.setCallbackData(Link.INPUT_IGG_ID.value);

    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton3.setText(B_LIST_IGG_ID.getText());
    inlineKeyboardButton3.setCallbackData(Link.LIST_IGG_ID.value);

    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
    inlineKeyboardButton4.setText(B_ADDITIONAL_OUTPUT.getText());
    inlineKeyboardButton4.setCallbackData(Link.DUPLICATE_OUTPUT.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);
    inlineKeyboardButtonsRow1.add(inlineKeyboardButton2);
    inlineKeyboardButtonsRow2.add(inlineKeyboardButton3);
    inlineKeyboardButtonsRow2.add(inlineKeyboardButton4);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    inlineKeyButtonList.add(inlineKeyboardButtonsRow2);
    return inlineKeyButtonList;
  }

  public InlineKeyboardMarkup setInlineKeyMarkup(List<List<InlineKeyboardButton>> inlineList) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    inlineKeyboardMarkup.setKeyboard(inlineList);
    return inlineKeyboardMarkup;
  }

  public List<List<InlineKeyboardButton>> createInlineDeleteButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText(B_DELETE_MODE.getText());
    inlineKeyboardButton1.setCallbackData(Link.DELETE_IGG_ID.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

  public List<List<InlineKeyboardButton>> createInlineShortMenuButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText(B_ENTER_PROMO_CODE.getText());
    inlineKeyboardButton1.setCallbackData(Link.INPUT_PROMO.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

  public List<List<InlineKeyboardButton>> createSwitchOutputButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText(B_OFF.getText());
    inlineKeyboardButton1.setCallbackData(Link.DUPLICATE_IN_GROUP_OFF.value);

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText(B_ON.getText());
    inlineKeyboardButton2.setCallbackData(Link.DUPLICATE_IN_GROUP_ON.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);
    inlineKeyboardButtonsRow1.add(inlineKeyboardButton2);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

}
