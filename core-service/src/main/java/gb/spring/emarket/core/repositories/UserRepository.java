package gb.spring.emarket.core.repositories;

import gb.spring.emarket.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
