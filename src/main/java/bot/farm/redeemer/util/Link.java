package bot.farm.redeemer.util;

/**
 * Enumeration of links used in the bot's functionality.
 * Each link corresponds to a specific action or operation in the bot. The enum
 * provides constant values for various endpoints or actions.
 */
public enum Link {
  INPUT_PROMO("/input_promo"),
  INPUT_IGG_ID("/input_igg_id"),
  LIST_IGG_ID("/list_igg_id"),
  DUPLICATE_OUTPUT("/duplicate_output"),
  DUPLICATE_IN_GROUP_OFF("/duplicate_in_group_off"),
  DUPLICATE_IN_GROUP_ON("/duplicate_in_group_on"),
  DELETE_IGG_ID("/delete_igg_id");

  public final String value;

  Link(String value) {
    this.value = value;
  }
}
