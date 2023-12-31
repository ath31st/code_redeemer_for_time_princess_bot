package bot.farm.redeemer.util;

import lombok.Getter;

/**
 * Enumeration representing phrases used in the bot messages.
 */
@Getter
public enum Phrases {
  FORMAT_EXAMPLE("Пример формата: 12345:rus,67890:eng"),
  CHOOSE_ACTION("Выберите действие:"),
  ENTER_PROMO_CODE("Введите промокод для активации на аккаунты из списка"),
  ENTER_ACCOUNT_DETAILS("Введите или скопируйте из списка аккаунтов ID и язык(только rus "
      + "или eng) аккаунтов, которые нужно добавить. ID и язык должны быть разделены двоеточием, "
      + "а сами записи разделены запятыми. Пробелы роли не играют. "
      + FORMAT_EXAMPLE.getText()),
  ENTER_ACCOUNT_DETAILS_FOR_DELETE("Введите или скопируйте из списка аккаунтов ID и "
      + "язык(только rus или eng) аккаунтов, которые нужно удалить. ID и язык должны быть "
      + "разделены двоеточием, а сами записи разделены запятыми. Пробелы роли не играют. "
      + FORMAT_EXAMPLE.getText()),
  ENABLE_DISABLE_INFO("Включить или отключить дополнительный вывод информации об успешном "
      + "применении промокода в группу:"),
  PROMO_REPORTS_INFO("Отчеты о применении промокодов будут приходить только в личные "
      + "сообщения отправившим"),
  FULL_PROMO_REPORTS_INFO("Полный отчет о применении промокода будет приходить в личные "
      + "сообщения применившим. Дополнительно будет выводиться короткий отчет в группу"),

  // buttons text
  B_ENTER_PROMO_CODE("Ввести промокод"),
  B_ENTER_IGG_ID("Ввести IGG ID"),
  B_LIST_IGG_ID("Список IGG ID"),
  B_ADDITIONAL_OUTPUT("Доп. вывод отчетов в группу"),
  B_DELETE_MODE("Перейти в режим удаления IGG ID"),
  B_OFF("Отключить"),
  B_ON("Включить"),

  // igg account service
  ADDED_IGG_IDS("Были добавлены следующие IGG ID: "),
  DATABASE_ALREADY_CONTAINS("В базе данных уже находятся: "),
  INVALID_INPUT_FORMAT("Введенные IGG ID не были добавлены в базу данных. Проверьте формат "
      + "ввода. Числа не должны быть длиннее 18 цифр, язык только eng или rus. "
      + FORMAT_EXAMPLE.getText()),
  REMOVED_IGG_IDS("Были удалены следующие IGG ID: "),
  DATABASE_MISSING("В базе данных отсутствуют: "),
  UNABLE_TO_REMOVE("Введенные IGG ID не были удалены из базы данных. Проверьте формат ввода. "
      + "Числа не должны быть длиннее 18 цифр, язык только eng или rus. "
      + FORMAT_EXAMPLE.getText()),

  // promo code service
  PROMO_CODE_ACTIVATED("Промокод был активирован на следующие IGG ID:\n"),
  PROMO_CODE_NOT_APPLIED("Промокод не был применен к следующим аккаунтам по причинам:\n"),
  PROMO_CODE_PREMATURELY_ENDED("Применение промокода завершено досрочно по причине:\n"),
  PROMO_CODE_SELF_APPLIED("Промокод был применен самостоятельно"),
  PROMO_CODE_EXPIRED("Время действия промокода истекло"),
  PROMO_CODE_NOT_EXIST("Такого промокода не существует"),
  PROMO_CODE_ALREADY_EXISTS("Такой промокод уже существует в базе данных и был применен на аккаунты"),
  INVALID_PROMO_CODE_FORMAT("Неправильный формат промокода. "
      + "Он должен состоять только из английских букв и цифр");

  private final String text;

  Phrases(String text) {
    this.text = text;
  }

}
