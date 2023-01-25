package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;
import org.springframework.web.reactive.function.client.WebClient;


public class WebClientCartIntegrationService extends ShoppingCartServiceIntegration implements CartIntegrationService {

    public WebClientCartIntegrationService(WebClient cartServiceWebClient) {
        super(cartServiceWebClient);
    }

    @Override
    public ShoppingCartDTO getShoppingCart(String userName) {
        return getCartDTO(userName);
    }

    @Override
    public void cleanShoppingCart(String userName) {
        clearCart();
    }
}
