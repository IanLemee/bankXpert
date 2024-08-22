package tech.ian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ian.user.dto.CreateUserResponse;
import tech.ian.user.entity.Account;
import tech.ian.user.entity.AccountInvestment;
import tech.ian.user.entity.User;
import tech.ian.user.producers.UserProducer;
import tech.ian.user.repository.AccountInvestmentRepository;
import tech.ian.user.repository.AccountRepository;
import tech.ian.user.repository.UserRepository;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountInvestmentRepository accountInvestmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProducer userProducer;

    @Transactional
    public CreateUserResponse registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Esse email ja existe");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            Account account = new Account();
            account.setUser(user);
            account.setEmail(user.getEmail());
            account.setBalance(BigDecimal.ZERO);
            account.setDocument(user.getDocument());
            accountRepository.save(account);

            user.setAccount(account);
            User userSaved = userRepository.save(user);

            AccountInvestment accountInvestment = new AccountInvestment();
            accountInvestment.setAccount(account);
            accountInvestment.setStockBalance(BigDecimal.ZERO);
            accountInvestment.setEmail(account.getEmail());
            accountInvestment.setDocument(account.getDocument());
            accountInvestmentRepository.save(accountInvestment);

            account.setAccountInvestment(accountInvestment);
            accountRepository.save(account);

            CreateUserResponse userResponse = new CreateUserResponse(
                    userSaved.getId(),
                    userSaved.getName(),
                    userSaved.getEmail(),
                    userSaved.getPassword(),
                    userSaved.getDocument());

            userProducer.publishMessageEmail(userResponse);
            return userResponse;
        }
    }
}
