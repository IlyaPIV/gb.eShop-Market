package gb.spring.emarket.api.dto;

import java.util.List;

public class OrdersListDTO {
    private final List<OrderDTO> ordersList;

    public OrdersListDTO(List<OrderDTO> ordersList) {
        this.ordersList = ordersList;
    }

    public List<OrderDTO> getOrdersList() {
        return ordersList;
    }
}
