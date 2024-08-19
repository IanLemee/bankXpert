package tech.ian.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name = "account_investment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInvestment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal stockBalance;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String document;

    @OneToOne(mappedBy = "accountInvestment")
    private Account account;

    @OneToMany(mappedBy = "accountInvestment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stocks> stocks;
}
