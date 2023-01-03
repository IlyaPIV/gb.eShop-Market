package gb.spring.emarket.core;

import org.postgresql.core.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MarketCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketCoreApplication.class, args);
    }

}
