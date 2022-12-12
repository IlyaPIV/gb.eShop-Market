package gb.spring.emarket.aop;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
    private long productService;
    private long userService;
    private long cartService;
    private long orderService;
}
