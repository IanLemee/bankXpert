package tech.ian.user.dto;

import tech.ian.user.entity.Account;

import java.math.BigDecimal;

public record AccountTransactionalRequest(String emailOrDocument, BigDecimal amout, Account senderAccount) {
}
