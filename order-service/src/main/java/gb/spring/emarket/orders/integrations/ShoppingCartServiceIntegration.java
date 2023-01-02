package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceIntegration {

    private final WebClient cartServiceWebClient;

    public ShoppingCartDTO getCartDTO() {
        return cartServiceWebClient.get()
                .uri("/api/v1/carts")
                .retrieve()
                .bodyToMono(ShoppingCartDTO.class)
                .block();

    }

    public void clearCart() {
        log.debug("отправляем запрос на очистку корзины");
        cartServiceWebClient.delete().uri("/api/v1/carts/all").retrieve().toBodilessEntity().block();
    }
}
