package gb.spring.emarket.products;

import gb.spring.emarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);
}
