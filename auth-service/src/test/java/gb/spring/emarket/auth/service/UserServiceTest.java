package gb.spring.emarket.auth.service;


import gb.spring.emarket.auth.model.User;
import gb.spring.emarket.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

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