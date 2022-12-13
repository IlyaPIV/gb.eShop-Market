package gb.spring.emarket.core.services;

import gb.spring.emarket.api.dto.CartItemDTO;
import gb.spring.emarket.api.dto.CheckoutDTO;
import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.core.entity.*;
import gb.spring.emarket.core.integrations.ShoppingCartServiceIntegration;
import gb.spring.emarket.core.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartServiceIntegration cartServiceIntegration;

    @Transactional
    public void createOrder(User user, CheckoutDTO checkoutDTO) {

        Order order = createAndFillNewOrder(user, checkoutDTO);
        orderRepository.save(order);
        cartServiceIntegration.clearCart();

    }

    private Order createAndFillNewOrder(User user, CheckoutDTO checkoutDTO) {

        ShoppingCartDTO currentCart = cartServiceIntegration.getCartDTO();

        Order order = new Order(user);

        order.setPaymentMethod(PaymentMethod.CASH); //checkoutDTO. paymetn
        order.setStatus(OrderStatus.NEW);
        order.setShippingAddress(checkoutDTO.getAddress());
        order.setDeliveryDays(checkoutDTO.getDeliveryDays());
        order.setTotalProducts(currentCart.getTotalProductsCost());
        order.setShippingCost(currentCart.getShippingCostPerItem() * currentCart.getTotalItems());
        order.setTotalCost(order.getShippingCost() + order.getTotalProducts());

        order.setOrderDetails(getPositionsSetFromCart(currentCart, order));

        return order;
    }

    private Set<OrderItemPosition> getPositionsSetFromCart(ShoppingCartDTO currentCart, Order order) {
        Set<OrderItemPosition> positions = new HashSet<>();
        for (CartItemDTO dto :
                currentCart.getProducts()) {
            OrderItemPosition orderItem = new OrderItemPosition();
            orderItem.setOrder(order);
            orderItem.setProduct(new Product(dto.getProductId()));
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setUnitPrice(dto.getCostPerItem());
            orderItem.setShippingCost(dto.getShippingCost());
            orderItem.setProductCost(dto.getCostPerItem() * dto.getQuantity());
            orderItem.setTotalCost(dto.getTotalCost());

            positions.add(orderItem);
        }

        return positions;
    }


}
