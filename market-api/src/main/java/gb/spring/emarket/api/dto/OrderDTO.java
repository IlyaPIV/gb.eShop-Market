package gb.spring.emarket.api.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class OrderDTO {

    private final int id;
    private final String userName;
    private final Date created;
    private final String status;
    private final String shippingAddress;
    private final Date deliveryDate;
    private final float totalProducts;
    private final float shippingCost;
    private final float totalCost;
    private final String paymentMethod;

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public float getTotalProducts() {
        return totalProducts;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getUserName() {
        return userName;
    }

    public OrderDTO(Builder builder) {
        this.id = builder.id;
        this.userName = builder.userName;
        this.created = builder.created;
        this.paymentMethod = builder.paymentMethod;
        this.totalCost = builder.totalCost;
        this.totalProducts = builder.totalProductsCost;
        this.shippingAddress = builder.shippingAddress;
        this.status = builder.status;
        this.shippingCost = builder.shippingCost;
        this.deliveryDate = builder.deliveryDate;
    }

    public static class Builder {
        private final int id;
        private final String userName;
        private Date created;
        private String status;
        private String shippingAddress;
        private Date deliveryDate;
        private float totalProductsCost;
        private float shippingCost;
        private float totalCost;
        private String paymentMethod;

        public Builder(int id, String userName) {
            this.id = id;
            this.userName = userName;
        }

        public Builder wasCreated(LocalDateTime created) {
            this.created = new java.sql.Date(java.sql.Date.from(created
                    .atZone(ZoneId.systemDefault())
                    .toInstant()).getTime());
            return this;
        }

        public Builder toAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder currentStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder deliverToDate(int deliveryDays) {
            this.deliveryDate = new java.sql.Date(created.getTime() + TimeUnit.DAYS.toMillis(deliveryDays));
            return this;
        }

        public Builder totalProductsCost(float totalProductsCost) {
            this.totalProductsCost = totalProductsCost;
            return this;
        }

        public Builder totalShippingCost(float shippingCost) {
            this.shippingCost = shippingCost;
            return this;
        }

        public Builder totalCost(float totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Builder payWith(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(this);
        }
    }
}
