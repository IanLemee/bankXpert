package tech.ian.user.dto;

import java.math.BigDecimal;

public record AccountTransactionalResponse(BigDecimal amount, BigDecimal senderBalance) {
}
