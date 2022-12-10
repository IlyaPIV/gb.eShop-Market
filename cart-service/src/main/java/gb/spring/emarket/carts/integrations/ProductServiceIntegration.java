package gb.spring.emarket.carts.integrations;

import gb.spring.emarket.api.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;


    public Optional<ProductDTO> getByID(Long id) {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:9090/core/api/v2/products/" + id,
                ProductDTO.class));
    }
}
