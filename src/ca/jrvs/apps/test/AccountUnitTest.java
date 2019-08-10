package ca.jrvs.apps.test;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.dto.MarketorderDto;
import ca.jrvs.apps.trading.service.RegisterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Captor;
import org.slf4j.Marker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class AccountUnitTest  {
    private Trader trader;
    private Account accountt;
    private Accountdao accountdao;
    private MarketorderDto marketorderDto;
    private RegisterService registerService;
    @Before
    public void setup(){
        marketorderDto = new MarketorderDto();
        accountt = new Account();
        accountt.setAmount( 10.0 );
        accountt.setId( 1 );
        accountt.setTraderId( 2 );
        accountt.setId( marketorderDto.getAccountId() );
        trader = new Trader();
        trader.setId( 1 );
        trader.setFirstname( "Pranavi" );
        /*trader.setLastname( "Punyamanthula" );
        trader.setEmail( "pranavi.punyamanthula@jrvs.ca" );
        trader.setCountry( "India" );
        trader.setDob( "09-25-95" );*/

    }
    @Test
    public void createTraderAndAccount() {
        when(accountdao.findByTraderId( anyInt())).thenReturn( accountt );
        when(accountdao.findByTraderIdForUpdate(marketorderDto.getAccountId())).thenReturn(accountt);
        registerService.createTraderAndAccount( trader );
        assertEquals(trader.getFirstname(),trader);
    }


}
