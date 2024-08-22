package tech.ian.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ian.mail.entity.EmailEntity;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {
}
