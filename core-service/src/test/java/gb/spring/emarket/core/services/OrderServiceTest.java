package gb.spring.emarket.core.services;

import gb.spring.emarket.api.dto.CartItemDTO;
import gb.spring.emarket.api.dto.CheckoutDTO;
import gb.spring.emarket.api.dto.ShoppingCartDTO;
import gb.spring.emarket.core.integrations.ShoppingCartServiceIntegration;
import gb.spring.emarket.core.repositories.OrderRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private ShoppingCartServiceIntegration cartServiceIntegration;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void testCreateOrder() {

        String userName = "Test User";

        CheckoutDTO checkoutDTO = createTestCheckoutDTO();
        ShoppingCartDTO cartDTO = createTestCartDTO();

        Mockito.doReturn(cartDTO).when(cartServiceIntegration).getCartDTO();

        orderService.createOrder(userName, checkoutDTO);
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    private ShoppingCartDTO createTestCartDTO() {
        List<CartItemDTO> cartItemDTOList = createTestItemsList();
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setShippingCostPerItem(0.7f);
        dto.setProducts(cartItemDTOList);
        dto.setTotalItems(3);
        dto.setTotalProductsCost(9.35f);


        return dto;
    }

    private List<CartItemDTO> createTestItemsList() {
        CartItemDTO firstItemDTO = new CartItemDTO();
        firstItemDTO.setProductId(9L);
        firstItemDTO.setTitle("Bread");
        firstItemDTO.setCostPerItem(2.11f);
        firstItemDTO.setQuantity(2);
        firstItemDTO.setShippingCost(1.4f);
        firstItemDTO.setTotalCost(5.62f);

        CartItemDTO secondItemDTO = new CartItemDTO();
        secondItemDTO.setProductId(14L);
        secondItemDTO.setTitle("Toilet paper");
        secondItemDTO.setQuantity(1);
        secondItemDTO.setCostPerItem(3.03f);
        secondItemDTO.setShippingCost(0.7f);
        secondItemDTO.setTotalCost(3.73f);

        return new ArrayList<>(List.of(firstItemDTO, secondItemDTO));
    }

    private CheckoutDTO createTestCheckoutDTO() {
        CheckoutDTO dto = new CheckoutDTO();
        dto.setAddress("test address");
        dto.setDeliveryDays(3);
        dto.setPaymentMethod("PAYPAL");

        return dto;
    }
}