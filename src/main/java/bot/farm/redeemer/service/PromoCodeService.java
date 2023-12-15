package bot.farm.redeemer.service;

import static bot.farm.redeemer.util.Phrases.INVALID_PROMO_CODE_FORMAT;
import static bot.farm.redeemer.util.Phrases.PROMO_CODE_ALREADY_EXISTS;

import bot.farm.redeemer.entity.PromoCode;
import bot.farm.redeemer.exception.PromoCodeException;
import bot.farm.redeemer.repository.PromoCodeRepository;
import bot.farm.redeemer.util.RegExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to promo codes.
 */
@Service
@RequiredArgsConstructor
public class PromoCodeService {
  private final PromoCodeRepository promoCodeRepository;

  /**
   * Saves a new promo code in the database.
   *
   * @param code The promo code to be saved.
   */
  public void savePromoCode(String code) {
    PromoCode pc = new PromoCode();
    pc.setCode(code);
    promoCodeRepository.save(pc);
  }

  /**
   * Checks if a promo code already exists in a case-insensitive manner.
   * Throws an exception if the promo code already exists.
   *
   * @param code The promo code to check.
   */
  public void checkExistsPromoCode(String code) {
    if (promoCodeRepository.existsByCodeIgnoreCase(code)) {
      throw new PromoCodeException(PROMO_CODE_ALREADY_EXISTS.getText());
    }
  }

  /**
   * Checks if a promo code has the correct format (only English letters and digits).
   * Throws an exception if the format is incorrect.
   *
   * @param code The promo code to check.
   */
  public void checkCorrectPromoCode(String code) {
    if (!code.matches(RegExpression.PROMO_CODE)) {
      throw new PromoCodeException(INVALID_PROMO_CODE_FORMAT.getText());
    }
  }
}
