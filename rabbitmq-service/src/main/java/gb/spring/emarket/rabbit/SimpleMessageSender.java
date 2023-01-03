package gb.spring.emarket.rabbit;

import gb.spring.emarket.api.properties.RabbitmqSettings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageSender implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public SimpleMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        String message = "Initial testing message...";
        rabbitTemplate.convertAndSend(RabbitmqSettings.EXCHANGER_NAME,
                "gb.spring.emarket.rabbitmq",
                message);
    }
}
