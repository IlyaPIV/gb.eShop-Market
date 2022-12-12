package gb.spring.emarket.controllers;

import gb.spring.emarket.aop.Statistic;
import gb.spring.emarket.aop.StatisticDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/statistic")
public class StatisticRestController {

    @GetMapping()
    public ResponseEntity<StatisticDTO> getStatistic() {
        return new ResponseEntity<>(Statistic.getStatistic(), HttpStatus.OK);
    }
}
