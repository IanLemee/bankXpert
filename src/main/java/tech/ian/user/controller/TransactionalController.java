package tech.ian.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ian.user.dto.AccountTransactionalRequest;
import tech.ian.user.dto.AccountTransactionalResponse;
import tech.ian.user.service.TransactionalService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransactionalController {

    @Autowired
    private TransactionalService transactionalService;

    @PostMapping("/")
    public ResponseEntity<AccountTransactionalResponse> transfer(@RequestBody Map<String, String> request) throws Exception {
        String emailOrDocument = request.get("emailOrDocument");
        BigDecimal amount = new BigDecimal(request.get("amount"));
        return ResponseEntity.ok(transactionalService.transaction(emailOrDocument, amount));
    }
}
