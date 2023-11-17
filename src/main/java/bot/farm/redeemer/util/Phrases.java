package bot.farm.redeemer.util;

public enum Phrases {
  CHOOSE_ACTION("Выберите действие:"),
  ENTER_PROMO_CODE("Введите промокод для активации на аккаунты из списка"),
  ENTER_ACCOUNT_DETAILS("Введите или скопируйте из списка аккаунтов ID и язык(только rus "
      + "или eng) аккаунтов, которые нужно добавить. ID и язык должны быть разделены двоеточием, "
      + "а сами записи разделены запятыми. Пробелы роли не играют. Пример формата: "
      + "12345:rus,67890:eng"),
  ENTER_ACCOUNT_DETAILS_FOR_DELETE("Введите или скопируйте из списка аккаунтов ID и "
      + "язык(только rus или eng) аккаунтов, которые нужно удалить. ID и язык должны быть "
      + "разделены двоеточием, а сами записи разделены запятыми. Пробелы роли не играют. "
      + "Пример формата: 12345:rus,67890:eng"),
  ENABLE_DISABLE_INFO("Включить или отключить дополнительный вывод информации об успешном "
      + "применении промокода в группу:"),
  PROMO_REPORTS_INFO("Отчеты о применении промокодов будут приходить только в личные "
      + "сообщения отправившим"),
  FULL_PROMO_REPORTS_INFO("Полный отчет о применении промокода будет приходить в личные "
      + "сообщения применившим. Дополнительно будет выводиться короткий отчет в группу");

  private final String text;

  Phrases(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
