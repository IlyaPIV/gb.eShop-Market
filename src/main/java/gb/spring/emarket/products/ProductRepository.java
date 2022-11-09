package gb.spring.emarket.products;

import gb.spring.emarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.cost > ?1 AND p.cost < ?2")
    public List<Product> findAllBetweenMinMaxPrice(float minPrice, float maxPrice);

    @Query("SELECT p FROM Product p WHERE p.cost > ?1")
    public List<Product> findAllWithPriceHigherThan(Float min);


}
