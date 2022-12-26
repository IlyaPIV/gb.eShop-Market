package gb.spring.emarket.core.repositories;

import gb.spring.emarket.core.entity.Order;
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
class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    public void testGetAllOrders() {
        List<Order> all = repository.findAll();

        all.forEach(System.out::println);

        assertThat(all.size()).isGreaterThan(0);
    }

}