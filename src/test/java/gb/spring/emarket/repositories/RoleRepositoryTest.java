package gb.spring.emarket.repositories;

import gb.spring.emarket.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    public void testAddNewRole() {
        Role role = new Role();
        role.setName("Manager");
        Role savedRole = repository.save(role);

        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAllRoles() {
        List<Role> allRoles = (List<Role>) repository.findAll();
        assertThat(allRoles.size()).isGreaterThan(0);
    }


}