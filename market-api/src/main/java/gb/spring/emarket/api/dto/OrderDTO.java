package gb.spring.emarket.api.dto;

import java.util.Date;

public class OrderDTO {

    public int id;
    public Date created;
    public String status;
    public String shippingAddress;
    public Date deliveryDate;
    public float totalProducts;
    public float shippingCost;
    public float totalCost;
    public String paymentMethod;

}
