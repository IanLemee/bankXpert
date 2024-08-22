package tech.ian.user.dto;

import java.math.BigDecimal;

public record StocksResponse(String stockId, int quantity, BigDecimal price, BigDecimal totalValue) {
}
