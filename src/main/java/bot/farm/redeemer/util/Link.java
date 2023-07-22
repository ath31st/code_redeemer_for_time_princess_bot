package bot.farm.redeemer.util;

public enum Link {
  INSERT_PROMO("/insert_promo"),
  INSERT_IGG_ID("/insert_igg_id"),
  LIST_IGG_ID("/list_igg_id"),
  DELETE_IGG_ID("/delete_igg_id");

  public final String value;

  Link(String value) {
    this.value = value;
  }
}
