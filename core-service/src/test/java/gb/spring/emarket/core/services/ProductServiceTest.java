package gb.spring.emarket.core.services;

import gb.spring.emarket.api.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void getPageWithoutFiltersTest() {
        int pageNum = 2;
        Page<ProductDTO> page = productService.getPage(pageNum, null, null, null);
        assertThat(page.getTotalPages()).isGreaterThan(0);
        assertThat(page.getTotalElements()).isGreaterThan(10);
    }

    @Test
    void getPageWithFiltersTest() {
        int pageNum = 1;
        int minPrice = 85;
        int maxPrice = 999;
        String partName = "test";
        Page<ProductDTO> page = productService.getPage(pageNum, minPrice, maxPrice, partName);
        assertThat(page.getTotalElements()).isLessThan(5);
    }
}