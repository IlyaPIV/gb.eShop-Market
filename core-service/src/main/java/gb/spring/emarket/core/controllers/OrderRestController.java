package gb.spring.emarket.core.controllers;

import gb.spring.emarket.api.dto.CheckoutDTO;
import gb.spring.emarket.api.dto.StringResponseDTO;
import gb.spring.emarket.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestHeader String username, @RequestBody CheckoutDTO checkoutDTO) {

        orderService.createOrder(username, checkoutDTO);
        return new ResponseEntity<>(new StringResponseDTO("New order has been placed"), HttpStatus.CREATED);
    }
}
