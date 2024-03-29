package gb.spring.emarket.orders.controllers;

import gb.spring.emarket.api.dto.CheckoutDTO;
import gb.spring.emarket.api.dto.StringResponseDTO;
import gb.spring.emarket.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestHeader String username, @RequestBody CheckoutDTO checkoutDTO) {
        System.out.println();
        Integer orderId = orderService.createOrder(username, checkoutDTO);
        return new ResponseEntity<>(new StringResponseDTO(orderId.toString()), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getOrders(@RequestHeader String username) {

        return new ResponseEntity<>(orderService.getUsersOrders(username), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }
}