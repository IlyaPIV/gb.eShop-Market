package gb.spring.emarket.carts.integrations;

import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.errors.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    private final WebClient productServiceWebClient;

    public ProductDTO getByID(Long id) {

        return productServiceWebClient.get()
                .uri("/api/v2/products/" + id)
                .retrieve() // получение ответа
                .onStatus(httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ProductNotFoundException("Товар не найден в продуктовом микроконтроллере"))
                )
                .bodyToMono(ProductDTO.class) // преобразование тела в объект
                .block();

    }
}
