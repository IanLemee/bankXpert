package tech.ian.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ian.user.entity.TransactionalAccount;

import java.util.UUID;

public interface TransactionalRepository extends JpaRepository<TransactionalAccount, UUID> {
}
