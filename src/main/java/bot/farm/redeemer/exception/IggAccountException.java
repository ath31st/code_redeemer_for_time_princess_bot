package bot.farm.redeemer.exception;

import lombok.Getter;

@Getter
public class IggAccountException extends RuntimeException {
  public IggAccountException(String errorMessage) {
    super(errorMessage);
  }
}
