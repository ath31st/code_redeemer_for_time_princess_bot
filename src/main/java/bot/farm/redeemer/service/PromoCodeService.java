package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.PromoCode;
import bot.farm.redeemer.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
  private final PromoCodeRepository promoCodeRepository;

  public boolean checkExistsPromoCode(String code) {
    return promoCodeRepository.existsByCodeIgnoreCase(code);
  }

  public void savePromoCode(String code) {
    PromoCode pc = new PromoCode();
    pc.setCode(code);
    promoCodeRepository.save(pc);
  }
}
