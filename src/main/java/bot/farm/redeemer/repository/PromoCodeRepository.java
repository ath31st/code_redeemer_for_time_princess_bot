package bot.farm.redeemer.repository;

import bot.farm.redeemer.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with PromoCode entities in the database.
 */
@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

  /**
   * Checks if a promo code exists in a case-insensitive manner.
   *
   * @param code The promo code to check.
   * @return {@code true} if the promo code exists, {@code false} otherwise.
   */
  @Query("select (count(p) > 0) from PromoCode p where upper(p.code) = upper(?1)")
  boolean existsByCodeIgnoreCase(String code);
}
