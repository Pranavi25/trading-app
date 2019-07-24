package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.Accountdao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundExceptionDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FundTransferService {
    private Accountdao accountdao;

    @Autowired
    public FundTransferService(Accountdao quoteDao){
        this.accountdao = accountdao;
    }

    /**
     * Deposit a fund to the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader id
     * @param fund found amount (can't be 0)
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundExceptionDao if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public Account deposit(Integer traderId, Double fund) {
        Account validateuserinput = null;
        validateuserinput = accountdao.findByTraderIdForUpdate(traderId);
        if(validateuserinput == null){
            throw new ResourceNotFoundExceptionDao( "Given Trader id is not found in the database" );
        }
        Account depositaccount =  accountdao.updateAmountById(traderId,fund);
        if(traderId == null || fund == null){
            throw new IllegalArgumentException( "Trader id or Amount cannot be null" );
        }
        return depositaccount;
    }

    /**
     * Withdraw a fund from the account which is associated with the traderId
     *
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader ID
     * @param fund amount can't be 0
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundExceptionDao if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public Account withdraw(Integer traderId, Double fund) {
        Account validateuserinput = null;
        validateuserinput = accountdao.findByTraderIdForUpdate(traderId);
        if(validateuserinput == null){
            throw new ResourceNotFoundExceptionDao( "Given Trader id is not found in the database" );
        }
        Account withdrawaccount =  accountdao.updateAmountById(traderId,fund);
        if(traderId == null || fund == null){
            throw new IllegalArgumentException( "Trader id or Amount cannot be null" );
        }
        return withdrawaccount;
    }
}
