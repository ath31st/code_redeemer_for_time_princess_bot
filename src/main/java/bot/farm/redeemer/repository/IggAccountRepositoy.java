package bot.farm.redeemer.repository;

import bot.farm.redeemer.entity.IggAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IggAccountRepositoy extends JpaRepository<IggAccount, Long> {
}
