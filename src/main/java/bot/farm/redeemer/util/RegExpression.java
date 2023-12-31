package bot.farm.redeemer.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class containing regular expressions for validation purposes.
 */
@UtilityClass
public class RegExpression {
  public static final String PROMO_CODE = "[A-Za-z0-9]{2,50}";
  public static final String IGG_ID = "^[0-9]{2,18}:((rus)|(eng))$";
}