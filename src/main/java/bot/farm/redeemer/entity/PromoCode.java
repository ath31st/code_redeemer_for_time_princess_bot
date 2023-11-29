package bot.farm.redeemer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a promotional code.
 * Each instance corresponds to a unique promo code.
 */
@Entity
@Setter
@Getter
@Table(name = "promo_codes")
public class PromoCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "promo_code_id", nullable = false)
  private Long id;
  @Column(name = "code", nullable = false)
  @NotEmpty
  private String code;
}
