package tech.ian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.ian.user.client.BrapiClient;
import tech.ian.user.client.dtoClient.ResponseBrapiDto;
import tech.ian.user.entity.Account;
import tech.ian.user.entity.AccountInvestment;
import tech.ian.user.entity.TransactionalAccount;
import tech.ian.user.enums.TransactionalType;
import tech.ian.user.repository.AccountInvestmentRepository;
import tech.ian.user.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class StockService {

    @Value("${TOKEN}")
    private String brapiToken;

    @Autowired
    private BrapiClient brapiClient;

    @Autowired
    private AccountInvestmentRepository accountInvestmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public BigDecimal buyStocks(Integer quantity, String stockName, AccountInvestment accountInvestment, TransactionalAccount transactionalAccount) throws Exception {
        var response = searchStocks(stockName);
        var result = response.results().get(0).regularMarketDayHigh();
        var price = result.multiply(BigDecimal.valueOf(quantity));

        var accountInvestmentOpt = this.accountInvestmentRepository.findByEmail(accountInvestment.getAccount().getEmail());
        if (accountInvestmentOpt.isEmpty()) {
            accountInvestmentOpt = this.accountInvestmentRepository.findByDocument(accountInvestment.getAccount().getDocument());
            if (accountInvestmentOpt.isEmpty()) {
                throw new Exception("Account not found");
            }
        }

        accountInvestment = accountInvestmentOpt.get();

        if (accountInvestment.getAccount().getBalance().compareTo(price) < 0) {
            throw new Exception("Saldo insuficiente");
        } else {
            accountInvestment.getAccount().setBalance(accountInvestment.getAccount().getBalance().subtract(price));

            transactionalAccount.setTransactionalType(TransactionalType.PURCHASE);

            accountInvestment.setStockBalance(accountInvestment.getStockBalance().add(price));

            this.accountRepository.save(accountInvestment.getAccount());
            this.accountInvestmentRepository.save(accountInvestment);

            return accountInvestment.getStockBalance();
        }
    }

    public ResponseBrapiDto searchStocks(String stockName) {
        var response = brapiClient.searchStocks(brapiToken, stockName);

        return response;
    }
}
