package bot.farm.redeemer.exception;

import lombok.Getter;

/**
 * Custom exception for IGG (Indie Gala Gift) account-related errors.
 */
@Getter
public class IggAccountException extends RuntimeException {
  public IggAccountException(String errorMessage) {
    super(errorMessage);
  }
}
