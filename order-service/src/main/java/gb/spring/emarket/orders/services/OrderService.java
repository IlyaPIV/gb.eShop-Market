package gb.spring.emarket.orders.services;

import gb.spring.emarket.api.dto.*;
import gb.spring.emarket.orders.entities.Order;
import gb.spring.emarket.orders.entities.OrderItemPosition;
import gb.spring.emarket.orders.entities.OrderStatus;
import gb.spring.emarket.orders.entities.PaymentMethod;
import gb.spring.emarket.orders.errors.OrderNotFoundException;
import gb.spring.emarket.orders.integrations.ShoppingCartServiceIntegration;
import gb.spring.emarket.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartServiceIntegration cartServiceIntegration;

    private final CleanCartGrpcClientService grpcService;

    @Transactional
    public Integer createOrder(String userName, CheckoutDTO checkoutDTO) {

        Order order = createAndFillNewOrder(userName, checkoutDTO);
        orderRepository.save(order);

        // 1st option to clean shopping cart
        if (grpcService.sentCleanCartCommand(userName)) {
            System.out.println("Cleaning command success.");
        }
        // 2nd option to clean shopping cart
        cartServiceIntegration.clearCart();

        return order.getId();
    }

    private Order createAndFillNewOrder(String userName, CheckoutDTO checkoutDTO) {

        ShoppingCartDTO currentCart = cartServiceIntegration.getCartDTO(userName);

        Order order = new Order(userName);

        order.setPaymentMethod(PaymentMethod.valueOf(checkoutDTO.getPaymentMethod()));
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
            orderItem.setProductId(dto.getProductId());
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setUnitPrice(dto.getCostPerItem());
            orderItem.setShippingCost(dto.getShippingCost());
            orderItem.setProductCost(dto.getCostPerItem() * dto.getQuantity());
            orderItem.setTotalCost(dto.getTotalCost());

            positions.add(orderItem);
        }

        return positions;
    }


    public OrdersListDTO getUsersOrders(String username) {
        List<Order> allByUserName = orderRepository.findAllByUserName(username);

        return convertListToDTO(allByUserName);
    }

    private OrdersListDTO convertListToDTO(List<Order> allByUserName) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        allByUserName.forEach(order -> {
            OrderDTO dto = fillDtoFromData(order);

            orderDTOS.add(dto);
        });
        return new OrdersListDTO(orderDTOS);
    }

    private static OrderDTO fillDtoFromData(Order order) {

        return new OrderDTO.Builder(order.getId(), order.getUserName())
                .wasCreated(order.getCreatedAt())
                .currentStatus(order.getStatus().toString())
                .deliverToDate(order.getDeliveryDays())
                .toAddress(order.getShippingAddress())
                .totalProductsCost(order.getTotalProducts())
                .totalShippingCost(order.getShippingCost())
                .totalCost(order.getTotalCost())
                .payWith(order.getPaymentMethod().toString())
                .build();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(Math.toIntExact(id));
    }

    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " is not found in DB."));

        return fillDtoFromData(order);
    }

    public void orderIsPayedByPayPal(long orderId) {
        Order order = orderRepository.findById(Math.toIntExact(orderId)).orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " is not found in DB."));
        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(PaymentMethod.PAYPAL);
        orderRepository.save(order);
    }
}
