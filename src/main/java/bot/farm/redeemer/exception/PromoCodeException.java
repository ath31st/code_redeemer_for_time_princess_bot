package bot.farm.redeemer.exception;

import lombok.Getter;

/**
 * Custom exception class for handling errors related to promotional codes.
 * Extends the RuntimeException class.
 */
@Getter
public class PromoCodeException extends RuntimeException {
  public PromoCodeException(String errorMessage) {
    super(errorMessage);
  }
}
