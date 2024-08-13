package tech.ian.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;

    @ManyToOne()
    @JoinColumn(name = "sender_account_id")
    private Account sender;

    @ManyToOne()
    @JoinColumn(name = "receiver_account_id")
    private Account receiver;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
