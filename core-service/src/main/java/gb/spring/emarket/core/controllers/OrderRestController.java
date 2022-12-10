package gb.spring.emarket.core.controllers;

import gb.spring.emarket.api.dto.CheckoutDTO;
import gb.spring.emarket.api.dto.StringResponseDTO;
import gb.spring.emarket.core.entity.User;
import gb.spring.emarket.core.services.OrderService;
import gb.spring.emarket.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/v2/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> placeOrder(Principal principal, @RequestBody CheckoutDTO checkoutDTO) {
        String userName = principal.getName();
        User user = userName == null ? null : userService.findByUsername(userName);
        orderService.createOrder(user, checkoutDTO);
        return new ResponseEntity<>(new StringResponseDTO("New order has been placed"), HttpStatus.CREATED);
    }
}
