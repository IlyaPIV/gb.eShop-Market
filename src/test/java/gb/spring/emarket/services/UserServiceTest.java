package gb.spring.emarket.services;

import gb.spring.emarket.entity.User;
import gb.spring.emarket.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void updatePasswords() {
        List<User> usersList = (List<User>) userRepository.findAll();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        usersList.forEach(user -> {
            System.out.println("before > " + user);
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println("after > " + user);
        });
        userRepository.saveAll(usersList);

    }
}