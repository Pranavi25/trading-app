package ca.jrvs.apps.trading.Controller;

import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "Trader")
@Controller
@RequestMapping("/trader")
/**
 * Controller binds path and methods together
 */
public class TraderController {
    /**
     * Dependencies are quote service
     * quoteDao
     * marketdao
     */
    private FundTransferService fundTransferService;
    private RegisterService registerService;

    @Autowired
    public TraderController(FundTransferService fundTransferService, RegisterService registerService) {
        this.fundTransferService = fundTransferService;
        this.registerService = registerService;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public TraderAccountView createTrader(@RequestBody Trader trader) {
        try {
            return registerService.createTraderAndAccount( trader );
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException( e );
        }

    }

}
