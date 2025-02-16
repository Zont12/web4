package org.example.back1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@SessionScope
public class AuthService {
    @Autowired
    private PointService pointsList;
    private User user;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String login, String password) {
        try {
            String hashPassword = HashPassword.hashPassword(password);
            Optional<User> user = userRepository.findByUsernameAndPassword(login, hashPassword);
            return user.isPresent();
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public boolean createAccount(String login, String password) {
        try {
            String hashPassword = HashPassword.hashPassword(password);
            Optional<User> existingUser = userRepository.findByUsername(login);

            if (existingUser.isPresent()) {
                return false;
            }

            User newUser = new User(login, hashPassword);
            userRepository.save(newUser);
            return true;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

}
