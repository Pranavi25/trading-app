package ca.jrvs.apps.trading.Controller;

import ca.jrvs.apps.trading.dao.Autowired;
import ca.jrvs.apps.trading.dao.MarketDatadao;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import ca.jrvs.apps.trading.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;

public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {this.orderService = orderService;}

    //public SecurityOrder postMarketDao(@RequestBody MarketorderDto orderDto) {
       // try{
       //     return orderService.executeMarketOrder(orderDto);
   // }catch (Exception e){
       //     throw ResponseExceptionUtil.getReponseStatusException(e);
      // }
}
