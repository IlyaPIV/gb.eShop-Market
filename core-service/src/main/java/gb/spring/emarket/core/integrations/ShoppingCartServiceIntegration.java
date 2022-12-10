package gb.spring.emarket.core.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ShoppingCartServiceIntegration {

    private final RestTemplate restTemplate;

    //@Value("${urls.carts}")
    private final String cartURL = "http://localhost:9091/shopping-carts/api/v1/carts";

    public ShoppingCartDTO getCartDTO() {
        return restTemplate.getForObject(cartURL, ShoppingCartDTO.class);
    }

    public void clearCart() {
        restTemplate.delete(cartURL + "/all");
    }
}
