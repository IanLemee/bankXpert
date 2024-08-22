package tech.ian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.ian.user.client.BrapiClient;
import tech.ian.user.client.dtoClient.ResponseBrapiDto;
import tech.ian.user.dto.BuyStocksResponse;
import tech.ian.user.entity.*;
import tech.ian.user.enums.TransactionalType;
import tech.ian.user.repository.*;

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
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private TransactionalRepository transactionalRepository;

    public BuyStocksResponse buyStocks(String stockName, Integer quantity) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();

        User user = userRepository.findUserEntityByEmail(userMail);
        AccountInvestment accountInvestment = accountInvestmentRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new Exception("AccountInvestment not found "));

        var response = searchStocks(stockName);
        var result = response.results().get(0).regularMarketPrice();
        var price = result.multiply(BigDecimal.valueOf(quantity));
        var dayPrice = result;

        if (accountInvestment.getAccount().getBalance().compareTo(price) < 0) {
            throw new Exception("Saldo insuficiente");
        }

        accountInvestment.getAccount().setBalance(accountInvestment.getAccount().getBalance().subtract(price));

        accountInvestment.setStockBalance(accountInvestment.getStockBalance().add(price));

        TransactionalAccount transactionalAccount = new TransactionalAccount();
        transactionalAccount.setTransactionalType(TransactionalType.PURCHASE);
        transactionalAccount.setAmount(price);

        this.transactionalRepository.save(transactionalAccount);
        this.accountRepository.save(accountInvestment.getAccount());
        this.accountInvestmentRepository.save(accountInvestment);

        var existingStockOpt = this.stocksRepository.findByStockId(stockName);

        Stocks stocksEntity;

        if (existingStockOpt.isEmpty()) {
            stocksEntity = new Stocks();
            stocksEntity.setStockId(stockName);
            stocksEntity.setPrice(dayPrice);
            stocksEntity.setQuantity(quantity);
            stocksEntity.setTotalValue(dayPrice.multiply(BigDecimal.valueOf(quantity)));
            stocksEntity.setAccountInvestment(accountInvestment);
            stocksRepository.save(stocksEntity);
        } else {
            stocksEntity = existingStockOpt.get();
            stocksEntity.setQuantity(stocksEntity.getQuantity() + quantity);
            stocksEntity.setPrice(dayPrice);
            stocksEntity.setTotalValue(stocksEntity.getTotalValue().add(dayPrice.multiply(BigDecimal.valueOf(quantity))));
            stocksRepository.save(stocksEntity);
        }


        return new BuyStocksResponse(accountInvestment.getAccount().getBalance());
    }

    public String sellStock(String stockName, Integer quantity) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();

        User user = userRepository.findUserEntityByEmail(userMail);
        AccountInvestment accountInvestment = accountInvestmentRepository.findByAccount(user.getAccount())
                .orElseThrow(() -> new Exception("AccountInvestment not found "));

        var response = searchStocks(stockName);
        var result = response.results().get(0).regularMarketPrice();
        var price = result.multiply(BigDecimal.valueOf(quantity));
        var dayPrice = result;

        var stock = this.stocksRepository.findByStockId(stockName);

        Stocks stocksEntity;

        if (stock.isEmpty()) {
            throw new Exception("Acao nao encontrada");
        } else {
            stocksEntity = stock.get(); // Obtendo a entidade existente
            stocksEntity.setQuantity(stocksEntity.getQuantity() - quantity);

            if (stocksEntity.getTotalValue() == null) {
                stocksEntity.setTotalValue(BigDecimal.ZERO);
            }

            stocksEntity.setTotalValue(stocksEntity.getTotalValue().subtract(dayPrice.multiply(BigDecimal.valueOf(quantity))));
            this.stocksRepository.save(stocksEntity);

            accountInvestment.setStockBalance(accountInvestment.getStockBalance().subtract(dayPrice.multiply(BigDecimal.valueOf(quantity))));
            this.accountInvestmentRepository.save(accountInvestment);

            Account account = user.getAccount();
            account.setBalance(account.getBalance().add(dayPrice.multiply(BigDecimal.valueOf(quantity))));
            this.accountRepository.save(account);

            TransactionalAccount transactionalAccount = new TransactionalAccount();
            transactionalAccount.setTransactionalType(TransactionalType.SELL);
            transactionalAccount.setAmount(price);

            transactionalRepository.save(transactionalAccount);
        }

        return "Venda Realizada com sucesso";
    }

    public ResponseBrapiDto searchStocks(String stockName) {
        var response = brapiClient.searchStocks(brapiToken, stockName);

        return response;
    }
}
