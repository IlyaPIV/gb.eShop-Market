package gb.spring.emarket.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("secret.properties")
public class MarketOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketOrdersApplication.class, args);
    }

}
