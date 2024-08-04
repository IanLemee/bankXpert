package tech.ian.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.ian.user.dto.CreateUserResponse;
import tech.ian.user.entity.User;
import tech.ian.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUserResponse registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Esse email ja existe");
        } else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            User userSaved = userRepository.save(user);

            CreateUserResponse userResponse = new CreateUserResponse(
                    userSaved.getId(),
                    userSaved.getName(),
                    userSaved.getEmail(),
                    userSaved.getPassword(),
                    userSaved.getDocument());

            return userResponse;
        }
    }
}
