package ca.jrvs.apps.test;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import ca.jrvs.apps.trading.service.OrderService;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest{

    @InjectMocks
    private MarketorderDto marketorderDto;
    @Mock
    private Accountdao accountdao;
    @Mock
    private QuoteDao quoteDao;
    @InjectMocks
    private OrderService orderService;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Captor
    ArgumentCaptor<SecurityOrder> securityOrderArgumentCaptor = ArgumentCaptor.forClass(SecurityOrder.class);

    @Mock
    private SecurityOrder securityOrder;

    public void setup(){

        MarketorderDto marketorderDto = new MarketorderDto();
        marketorderDto.setAccountId(1);
        marketorderDto.setSize(1);
        marketorderDto.setTicker( "AAPL" );
    }

    public void executeMarketOrderTest(){
        Account account = new Account();
        account.setAmount( 10.0 );
        account.setId( marketorderDto.getAccountId() );
        Mockito.when(accountdao.findByTraderIdForUpdate(marketorderDto.getAccountId())).thenReturn(account);
        Mockito.when(accountdao.existsById( marketorderDto.getAccountId() )).thenReturn( true );

        //QuoteDao test
        Quote quote = new Quote();
        quote.setBidPrize( 10.0 );
        quote.setAskSize( 20 );
        Mockito.when(quoteDao.findById( marketorderDto.getTicker() )).thenReturn( quote );
        Mockito.when(quoteDao.existsById( marketorderDto.getTicker() )).thenReturn( true );

        orderService.executeMarketOrder( marketorderDto );
        verify( securityOrderDao ).save(securityOrderArgumentCaptor.capture());
        //We will retrieve the captured argument value using ArgumentCaptor.getValue().
        assertEquals(securityOrderArgumentCaptor.getValue().getStatus(), OrderStatus.FILLED );

    }

}
