package bot.farm.redeemer.repository;

import bot.farm.redeemer.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepositoy extends JpaRepository<PromoCode, Long> {
}
