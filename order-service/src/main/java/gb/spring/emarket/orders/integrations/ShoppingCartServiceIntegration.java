package gb.spring.emarket.orders.integrations;

import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.api.errors.ShoppingCartWebServiceError;
import gb.spring.emarket.orders.errors.CartServiceIntegrationError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceIntegration {

    private final WebClient cartServiceWebClient;

    public ShoppingCartDTO getCartDTO(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/carts")
                .header("username", username)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError(),
                        clientResponse -> clientResponse.bodyToMono(ShoppingCartWebServiceError.class).map(
                                body -> {
                                    if (body.getErrorCode().equals(ShoppingCartWebServiceError.CartServiceErrors.CART_NOT_FOUND.name())) {
                                        return new CartServiceIntegrationError("Incorrect request: shopping cart is not found");
                                    }
                                    if (body.getErrorCode().equals(ShoppingCartWebServiceError.CartServiceErrors.CART_IS_BROKEN.name())) {
                                        return new CartServiceIntegrationError("Incorrect request: shopping cart is broken");
                                    }
                                    return new CartServiceIntegrationError("Incorrect request: unknown error");
                                }
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationError("Cart service is broken")))
                .bodyToMono(ShoppingCartDTO.class)
                .block();

    }

    public void clearCart() {
        log.debug("отправляем запрос на очистку корзины");
        cartServiceWebClient.delete().uri("/api/v1/carts/all").retrieve().toBodilessEntity().block();
    }
}
