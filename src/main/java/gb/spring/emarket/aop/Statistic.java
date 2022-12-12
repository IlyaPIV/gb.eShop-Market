package gb.spring.emarket.aop;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@NoArgsConstructor
public class Statistic {
    private static AtomicLong productServiceTime;
    private static AtomicLong userServiceTime;
    private static AtomicLong cartServiceTime;
    private static AtomicLong orderServiceTime;

    @PostConstruct
    private static void initTime() {
        productServiceTime = new AtomicLong();
        userServiceTime = new AtomicLong();
        cartServiceTime = new AtomicLong();
        orderServiceTime = new AtomicLong();
    }

    public static void addProductsTime(long ms) {
        productServiceTime.getAndAdd(ms);
    }

    public static void addUsersTime(long ms) {
        userServiceTime.getAndAdd(ms);
    }

    public static void addCartTime(long ms) {
        cartServiceTime.getAndAdd(ms);
    }

    public static void addOrderTime(long ms) {
        orderServiceTime.getAndAdd(ms);
    }

    public static StatisticDTO getStatistic() {
        return new StatisticDTO(productServiceTime.get(), userServiceTime.get(), cartServiceTime.get(), orderServiceTime.get());
    }
}
