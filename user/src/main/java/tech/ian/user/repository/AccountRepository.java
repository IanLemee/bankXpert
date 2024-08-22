package tech.ian.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ian.user.entity.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findByEmail(String email);

    Account findByDocument(String document);
}
