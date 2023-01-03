package gb.spring.emarket.carts.rabbitmq;

import gb.spring.emarket.api.properties.RabbitmqSettings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqMessageSender {

    public final RabbitTemplate rabbitTemplate;
    private static final String SERVICE_PREFIX = "CORE-SERVICE >> ";

    public RabbitmqMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToLogger(String logMessage) {
        rabbitTemplate.convertAndSend(RabbitmqSettings.EXCHANGER_NAME,
                RabbitmqSettings.ROUTING_KEY_CARD,
                SERVICE_PREFIX + logMessage);
    }
}
