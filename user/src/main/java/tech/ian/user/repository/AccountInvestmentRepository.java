package tech.ian.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ian.user.entity.Account;
import tech.ian.user.entity.AccountInvestment;

import java.util.Optional;
import java.util.UUID;

public interface AccountInvestmentRepository extends JpaRepository<AccountInvestment, UUID> {
    Optional<AccountInvestment> findByAccount(Account account);
}
