package tech.ian.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity(name = "stocks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockId;  // Nome do ativo, ex: BBAS3
    private int quantity;    // Quantidade de ações
    private BigDecimal price;  // Preço da ação no momento da compra
    private BigDecimal totalValue;  // Valor total investido nesse ativo

    @ManyToOne
    @JoinColumn(name = "account_investment_id")
    private AccountInvestment accountInvestment;

}
