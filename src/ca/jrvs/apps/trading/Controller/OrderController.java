package ca.jrvs.apps.trading.Controller;

import ca.jrvs.apps.trading.dao.Autowired;
import ca.jrvs.apps.trading.dao.MarketDatadao;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import ca.jrvs.apps.trading.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "Order",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @PostMapping(path = "MarketOrder")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SecurityOrder postMarketDao(@RequestBody MarketorderDto orderDto) {
        try {
            return orderService.executeMarketOrder( orderDto );
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException( e );
        }
    }
}
