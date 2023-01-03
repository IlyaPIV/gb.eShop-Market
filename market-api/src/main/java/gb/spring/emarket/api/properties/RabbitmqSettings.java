package gb.spring.emarket.api.properties;

public class RabbitmqSettings {
    public static final String EXCHANGER_NAME = "eMarketProcessingExchanger";
    public static final String QUEUE_NAME = "eMarketProcessingQueue";

    public static final String ROUTING_KEY_CORE = "gb.spring.emarket.core";
    public static final String ROUTING_KEY_CARD = "gb.spring.emarket.card";
    public static final String ROUTING_KEY_AUTH = "gb.spring.emarket.auth";
}
