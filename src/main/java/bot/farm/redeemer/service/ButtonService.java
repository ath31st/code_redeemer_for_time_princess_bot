package bot.farm.redeemer.service;

import bot.farm.redeemer.util.Link;
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
    List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("Ввести промокод");
    inlineKeyboardButton1.setCallbackData(Link.INPUT_PROMO.value);

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText("Ввести IGG ID");
    inlineKeyboardButton2.setCallbackData(Link.INPUT_IGG_ID.value);

    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton3.setText("Список IGG ID");
    inlineKeyboardButton3.setCallbackData(Link.LIST_IGG_ID.value);

    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
    inlineKeyboardButton4.setText("Вывод отчетов");
    inlineKeyboardButton4.setCallbackData(Link.SWITCH_OUTPUT.value);

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
    inlineKeyboardButton1.setText("Перейти в режим удаления IGG ID");
    inlineKeyboardButton1.setCallbackData(Link.DELETE_IGG_ID.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

  public List<List<InlineKeyboardButton>> createInlineShortMenuButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("Ввести промокод");
    inlineKeyboardButton1.setCallbackData(Link.INPUT_PROMO.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

  public List<List<InlineKeyboardButton>> createSwitchOutputButton() {
    final List<List<InlineKeyboardButton>> inlineKeyButtonList = new ArrayList<>();
    final List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText("В приват");
    inlineKeyboardButton1.setCallbackData(Link.OUTPUT_TO_PRIVATE.value);

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText("В группу");
    inlineKeyboardButton2.setCallbackData(Link.OUTPUT_TO_GROUP.value);

    inlineKeyboardButtonsRow1.add(inlineKeyboardButton1);
    inlineKeyboardButtonsRow1.add(inlineKeyboardButton2);

    inlineKeyButtonList.add(inlineKeyboardButtonsRow1);
    return inlineKeyButtonList;
  }

}
