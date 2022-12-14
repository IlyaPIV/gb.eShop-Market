package gb.spring.emarket.core.repositories;

import gb.spring.emarket.core.entity.Product;
import gb.spring.emarket.core.specifications.ProductSpecification;
import org.junit.jupiter.api.Assertions;
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
    void testFindAllWithSpecifications() {

        int minPrice = 85;
        int maxPrice = 999;
        String partName = "test";

        Specification<Product> specification = Specification.where(null);
        specification = specification.and(ProductSpecification.costGreaterThanOrEqualTo(minPrice));
        specification = specification.and(ProductSpecification.costLessThanOrEqualTo(maxPrice));
        specification = specification.and(ProductSpecification.titleLike(partName));

        Pageable pageable = PageRequest.of(0, 5);

        Page<Product> products = repository.findAll(specification, pageable);

        Assertions.assertEquals(products.getTotalElements(), 0);

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