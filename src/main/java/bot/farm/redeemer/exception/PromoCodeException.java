package bot.farm.redeemer.exception;

import lombok.Getter;

@Getter
public class PromoCodeException extends RuntimeException {
  public PromoCodeException(String errorMessage) {
    super(errorMessage);
  }
}
