package gb.spring.emarket.orders.repositories;


import gb.spring.emarket.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAllByUserName(String username);
}
