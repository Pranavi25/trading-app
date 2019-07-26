package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {
    private TraderDao traderDao;
    private Accountdao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public RegisterService(TraderDao traderDao, Accountdao accountDao,
                           PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param trader trader info
     * @return traderAccountView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundExceptionDao if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException           if unable to retrieve data
     * @throws IllegalArgumentException                              for invalid input
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        if (trader.getId() == null) {
            throw new IllegalArgumentException( "Id cannot be null" );
        }
        TraderAccountView traderAccountView = new TraderAccountView();
        if (StringUtil.isEmpty( trader.getFirstname(), trader.getLastname(), trader.getCountry(), trader.getDob(), trader.getEmail() )) {
            throw new IllegalArgumentException( "Need to have all the fields" );
        }
        traderAccountView.setTrader( traderDao.save( trader ) );
        Account account = new Account();
        account.setAmount( 0.0 );
        account.setTraderId( trader.getId() );
        traderAccountView.setAccount( (List<TraderAccountView.account>) accountDao.save( account ) );
        return traderAccountView;
    }

    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundExceptionDao if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException           if unable to retrieve data
     * @throws IllegalArgumentException                              for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException( "Id cannot be null" );
        }
        Account account = new Account();
        if (account.getAmount() == 0) {
            throw new IllegalArgumentException( "the amount cannot be zero" );
        }
        List<Position> position = positionDao.findByAccountId( account.getId() );
        position.forEach( position1 -> {
            if (position1.getSize() != 0) {
                throw new IllegalArgumentException( "Cannot be zero since cannot delete if it is zero" );
            }
        } );

        accountDao.deleteById( account.getId() );
        traderDao.deleteById( traderId );
        securityOrderDao.deleteById( account.getId() );
    }

}
