package bot.farm.redeemer.util;

public enum Link {
  INPUT_PROMO("/input_promo"),
  INPUT_IGG_ID("/input_igg_id"),
  LIST_IGG_ID("/list_igg_id"),
  SWITCH_OUTPUT("/switch_output"),
  OUTPUT_TO_PRIVATE("/output_to_private"),
  OUTPUT_TO_GROUP("/output_to_group"),
  DELETE_IGG_ID("/delete_igg_id");

  public final String value;

  Link(String value) {
    this.value = value;
  }
}
