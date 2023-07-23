package bot.farm.redeemer.repository;

import bot.farm.redeemer.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
  @Query("select (count(p) > 0) from PromoCode p where upper(p.code) = upper(?1)")
  boolean existsByCodeIgnoreCase(String code);
}
