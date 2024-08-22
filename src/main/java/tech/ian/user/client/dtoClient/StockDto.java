package tech.ian.user.client.dtoClient;

import java.math.BigDecimal;

public record StockDto(BigDecimal regularMarketPrice, String currency, String symbol, String logourl) {
}
