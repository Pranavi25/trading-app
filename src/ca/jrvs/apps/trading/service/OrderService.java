package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import ca.jrvs.apps.trading.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger( OrderService.class );

    private Accountdao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(Accountdao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }
    /**
     * Execute a market order
     *
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     *   - buy order : check account balance
     *   - sell order: check position for the ticker/symbol
     *   - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     *
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketorderDto orderDto) {
        SecurityOrder securityOrder = new SecurityOrder();
        //validate the order size and ticker
        if(securityOrder.getSize() == null || securityOrder.getTicker() == null){
            throw new IllegalArgumentException("size and ticker cannot be empty");
        }
        Quote quote = quoteDao.findById( orderDto.getTicker());
        if(quote == null){
            throw new IllegalArgumentException( "cannt find the ticker" );
        }

        securityOrder.setAccountId( orderDto.getAccountId() );
        securityOrder.setTicker( orderDto.getTicker() );
        securityOrder.setSize( orderDto.getSize() );
        //update
        Account account = accountDao.findByTraderIdForUpdate( orderDto.getAccountId() );
        if(orderDto.getSize() > 0){
            securityOrder.setPrice( quote.getAskPrice() );
            handleBuyMarketOrder( orderDto,securityOrder,account );
        }else{
            securityOrder.setPrice( quote.getBidPrize() );
            handleSellMarketOrder( orderDto,securityOrder,account );
        }
        return securityOrder;

    }

    protected void handleBuyMarketOrder(MarketorderDto marketorderDto, SecurityOrder securityOrder, Account account){
        Double buy = marketorderDto.getSize() * securityOrder.getPrice();
        if(account.getAmount() >= buy){
            double amount_update = account.getAmount() - buy;
            accountDao.updateAmountById( marketorderDto.getAccountId(),amount_update );
            securityOrder.setStatus( OrderStatus.FILLED );
        }else{
            securityOrder.setStatus( OrderStatus.CANCELED );
        }
    }

    protected void handleSellMarketOrder(MarketorderDto marketorderDto, SecurityOrder securityOrder, Account account){
        Long position = positionDao.findByIdAndTicker( marketorderDto.getAccountId(),marketorderDto.getTicker() );
        if(position + securityOrder.getSize() >= 0){
            Double amount_sell = -securityOrder.getSize() * securityOrder.getPrice();
            Double update_amount = account.getAmount() + amount_sell;
            securityOrder.setStatus( OrderStatus.FILLED );
        }else{
            securityOrder.setStatus( OrderStatus.CANCELED );
        }
    }
}