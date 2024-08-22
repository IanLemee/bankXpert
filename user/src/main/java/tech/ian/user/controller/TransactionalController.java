package tech.ian.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ian.user.dto.BuyStocksResponse;
import tech.ian.user.dto.AccountTransactionalResponse;
import tech.ian.user.dto.BuyStocksRequest;
import tech.ian.user.service.StockService;
import tech.ian.user.service.TransactionalService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransactionalController {

    @Autowired
    private TransactionalService transactionalService;

    @Autowired
    private StockService stockService;

    @PostMapping("/")
    public ResponseEntity<AccountTransactionalResponse> transfer(@RequestBody Map<String, String> request) throws Exception {
        String emailOrDocument = request.get("emailOrDocument");
        BigDecimal amount = new BigDecimal(request.get("amount"));
        return ResponseEntity.ok(transactionalService.transaction(emailOrDocument, amount));
    }

    @PostMapping("/stocksBuy")
    public ResponseEntity<BuyStocksResponse> buyStocks(@RequestBody BuyStocksRequest buyStocksRequest) throws Exception {
        return ResponseEntity.ok(stockService.buyStocks(buyStocksRequest.stockName(), buyStocksRequest.quantity()));
    }

    @PostMapping("/stocksSell")
    public ResponseEntity<String> sellStocks(@RequestBody BuyStocksRequest buyStocksRequest) throws Exception {
        return ResponseEntity.ok(stockService.sellStock(buyStocksRequest.stockName(), buyStocksRequest.quantity()));
    }
}
