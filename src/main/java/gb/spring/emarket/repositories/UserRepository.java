package gb.spring.emarket.repositories;

import gb.spring.emarket.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

}
