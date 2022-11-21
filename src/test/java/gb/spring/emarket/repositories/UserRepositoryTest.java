package gb.spring.emarket.repositories;

import gb.spring.emarket.entity.Role;
import gb.spring.emarket.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithoutRoles() {
        User user = new User();
        user.setUsername("manager");
        user.setEmail("manager@mail.ru");
        user.setPassword("manager");

        repository.save(user);
    }

    @Test
    public void testAddRolesToUser() {

        Role role = entityManager.find(Role.class, 1);
        User user = entityManager.find(User.class, 2);

        user.addRole(role);

        User savedUser = repository.save(user);

        assertEquals(savedUser.getRoles().size(), 2);
    }
}