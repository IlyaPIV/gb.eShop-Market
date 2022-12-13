package gb.spring.emarket.auth.repository;


import gb.spring.emarket.auth.model.Role;
import gb.spring.emarket.auth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        user.setUsername("superadmin");
        user.setEmail("superadmin@gmail.com");
        user.setPassword("superadmin");

        repository.save(user);
    }

    @Test
    public void testAddRolesToUser() {

        Role role = entityManager.find(Role.class, 3);
        User user = entityManager.find(User.class, 3);

        user.addRole(role);

        User savedUser = repository.save(user);

        assertThat(savedUser.getRoles()).hasSizeGreaterThan(0);
        //assertEquals(savedUser.getRoles().size(), 2);
    }

    @Test
    public void testEncodePasswords() {
        User user = entityManager.find(User.class, 1);

    }
}