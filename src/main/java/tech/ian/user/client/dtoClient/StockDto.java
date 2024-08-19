package tech.ian.user.client.dtoClient;

import java.math.BigDecimal;

public record StockDto(BigDecimal regularMarketDayHigh, String currency, String symbol, String logourl) {
}
