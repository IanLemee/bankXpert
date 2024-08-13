package tech.ian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ian.user.dto.AccountTransactionalResponse;
import tech.ian.user.entity.Account;
import tech.ian.user.entity.TransactionalAccount;
import tech.ian.user.entity.User;
import tech.ian.user.producers.UserProducer;
import tech.ian.user.repository.AccountRepository;
import tech.ian.user.repository.TransactionalRepository;
import tech.ian.user.repository.UserRepository;



import java.math.BigDecimal;

@Service
public class TransactionalService {

    @Autowired
    private TransactionalRepository transactionalRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer;

    @Transactional
    public AccountTransactionalResponse transaction(String emailOrDocument, BigDecimal amount) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMail = authentication.getName();

        User user = userRepository.findUserEntityByEmail(userMail);

        Account senderAccount = user.getAccount();

     var receiverAccount = accountRepository.findByEmail(emailOrDocument);

     if (receiverAccount == null) {
         receiverAccount = accountRepository.findByDocument(emailOrDocument);
     }

     if (receiverAccount == null) {
         throw new Exception("Receiver not found");
     }

     if (amount.compareTo(senderAccount.getBalance()) > 0) {
         throw new Exception("Insuficient balance");
     }

        TransactionalAccount transactional = new TransactionalAccount();

        transactional.setSender(senderAccount);
        transactional.setReceiver(receiverAccount);
        transactional.setAmount(amount);

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

        var transactionalSaved =transactionalRepository.save(transactional);
        accountRepository.save(receiverAccount);
        var senderSaved = accountRepository.save(senderAccount);

         userProducer.publishTransactionalEmail(senderSaved, transactionalSaved);

        return new AccountTransactionalResponse(amount, senderAccount.getBalance());
    }

}
