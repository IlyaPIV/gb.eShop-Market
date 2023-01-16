package gb.spring.emarket.orders.paypal;

import com.paypal.orders.*;
import gb.spring.emarket.orders.entities.Order;
import gb.spring.emarket.orders.errors.OrderNotFoundException;
import gb.spring.emarket.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final OrderService orderService;

    public OrderRequest createOrderRequest(Long orderId) {
        Order order = orderService.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found in DB"));

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("GB eShop Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("GB eShop Market order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(String.valueOf(order.getTotalCost()))
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money()
                                .currencyCode("USD")
                                .value(String.valueOf(order.getTotalCost())))))
                .items(order.getOrderDetails().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProductId().toString())
                                .unitAmount(new Money().currencyCode("USD").value(String.valueOf(orderItem.getUnitPrice())))
                                .quantity(String.valueOf(orderItem.getQuantity()))
                        )
                        .collect(Collectors.toList())
                )
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUserName()))
                        .addressPortable(new AddressPortable()
                                .addressLine1(order.getShippingAddress())
                                .addressLine2("")
                                .adminArea2("San Diego")
                                .adminArea1("CA")
                                .postalCode("94107")
                                .countryCode("US")
                        )
                );
        purchaseUnitRequests.add(purchaseUnitRequest);

        orderRequest.purchaseUnits(purchaseUnitRequests);

        return orderRequest;
    }
}
