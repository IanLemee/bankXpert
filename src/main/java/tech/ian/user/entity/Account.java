package tech.ian.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal balance;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String document;

    @OneToOne(mappedBy = "account")
    private User user;

    @OneToMany()
    @JoinColumn(name = "account_transactions")
    private List<TransactionalAccount> transactions;
}
