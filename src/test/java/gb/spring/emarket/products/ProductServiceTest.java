package gb.spring.emarket.products;

import gb.spring.emarket.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Test
    void findById() {
        Long prodId = 3L;
        ProductDTO dto = service.findById(prodId);

        System.out.println(dto);
        assertNotNull(dto);
    }

    @Test
    void getPage() {
    }

    @Test
    void update() {
    }
}