package gb.spring.emarket.core.controllers;

import gb.spring.emarket.core.aop.Statistic;
import gb.spring.emarket.core.aop.StatisticDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
