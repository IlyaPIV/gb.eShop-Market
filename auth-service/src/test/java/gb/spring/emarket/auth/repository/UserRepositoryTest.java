package gb.spring.emarket.auth.repository;

import gb.spring.emarket.auth.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        String username = "bob";
        Optional<User> bob = userRepository.findByUsername(username);
        assert (bob.isPresent());
        assertThat(bob.get().getUsername()).isEqualTo(username);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> users = userRepository.findAll(pageable);

        Assertions.assertEquals(2, users.getTotalElements());
    }
}