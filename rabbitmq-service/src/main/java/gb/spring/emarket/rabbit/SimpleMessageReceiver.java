package gb.spring.emarket.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SimpleMessageReceiver {

    private final AtomicInteger latch = new AtomicInteger();

    public void receiveAndLogMessage(String message) {
        System.out.println("Received #" + latch.get() + " : " + message);
        latch.getAndIncrement();
    }

}
