package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundExceptionDao extends RuntimeException{
    /**
     * this is a customized exception
     * when resources in dao is not found this will evoke 404 error
     * @param msg
     */
    public ResourceNotFoundExceptionDao(String msg){ super(msg);}
    public ResourceNotFoundExceptionDao(String msg, Exception ex){ super(msg,ex);}
}
