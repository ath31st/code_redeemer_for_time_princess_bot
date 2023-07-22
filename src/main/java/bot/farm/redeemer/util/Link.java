package bot.farm.redeemer.util;

public enum Link {
  INPUT_PROMO("/input_promo"),
  INPUT_IGG_ID("/input_igg_id"),
  LIST_IGG_ID("/list_igg_id"),
  DELETE_IGG_ID("/delete_igg_id");

  public final String value;

  Link(String value) {
    this.value = value;
  }
}
