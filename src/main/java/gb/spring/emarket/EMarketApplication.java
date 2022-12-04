package gb.spring.emarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMarketApplication.class, args);
    }

}
