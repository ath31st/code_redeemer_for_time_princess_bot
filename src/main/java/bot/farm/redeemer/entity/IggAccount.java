package bot.farm.redeemer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "igg_accounts")
public class IggAccount {
  @Id
  @Column(name = "igg_id", nullable = false)
  private Long iggId;
  @Column(name = "lang", nullable = false)
  private String lang;

  @Override
  public String toString() {
    return iggId + ":" + lang;
  }
}
