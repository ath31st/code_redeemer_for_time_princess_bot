package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.PromoCode;
import bot.farm.redeemer.exception.PromoCodeException;
import bot.farm.redeemer.repository.PromoCodeRepository;
import bot.farm.redeemer.util.RegExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
  private final PromoCodeRepository promoCodeRepository;

  public void savePromoCode(String code) {
    checkCorrectPromoCode(code);
    checkExistsPromoCode(code);

    PromoCode pc = new PromoCode();
    pc.setCode(code);
    promoCodeRepository.save(pc);
  }

  private void checkExistsPromoCode(String code) {
    if (promoCodeRepository.existsByCodeIgnoreCase(code)) {
      throw new PromoCodeException(
          "Такой промокод уже существует в базе данных и был применен на аккаунты");
    }
  }

  private void checkCorrectPromoCode(String code) {
    if (!code.matches(RegExpression.PROMO_CODE)) {
      throw new PromoCodeException(
          "Неправильный формат промокода. Он должен состоять только из английских букв и цифр");
    }
  }
}
