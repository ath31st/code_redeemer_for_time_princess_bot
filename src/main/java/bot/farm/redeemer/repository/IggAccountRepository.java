package bot.farm.redeemer.repository;

import bot.farm.redeemer.entity.IggAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for managing IGG (Indie Gala Gift) accounts.
 * This repository provides CRUD (Create, Read, Update, Delete) operations for
 * {@code IggAccount} entities.
 * It extends {@code JpaRepository} to inherit
 * common methods for working with JPA entities.
 */
@Repository
public interface IggAccountRepository extends JpaRepository<IggAccount, Long> {
}
