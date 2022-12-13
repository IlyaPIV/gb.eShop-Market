package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDTO> getProductById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:9090/eshop/api/v2/products/" + id,
                ProductDTO.class));
    }
}
