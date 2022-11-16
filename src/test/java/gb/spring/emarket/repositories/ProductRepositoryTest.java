package gb.spring.emarket.repositories;

import gb.spring.emarket.entity.Product;
import gb.spring.emarket.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void testFindAllNoSpecification() {
        Specification<Product> specification = Specification.where(null);

        int pageNum = 2;
        int prodsPerPage = 10;
        Pageable pageable = PageRequest.of(pageNum - 1, prodsPerPage);
        Page<Product> products = repository.findAll(specification, pageable);

        assertThat(products.getSize()).isNotNull();
    }

    @Test
    void testAddNewProduct() {
        String title = "Cheese 1kg";
        float cost = 19.73f;

        Product product = new Product();
        product.setCost(cost);
        product.setTitle(title);
        repository.save(product);
    }
}