package gb.spring.emarket.auth.repository;


import gb.spring.emarket.auth.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
