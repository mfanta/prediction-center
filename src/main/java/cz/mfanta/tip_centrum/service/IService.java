package cz.mfanta.tip_centrum.service;

/**
 * A common interface for all the service classes.
 * 
 * @author Martin Fanta (martin tod fanta ta gmail tod com)
 * 
 */
public interface IService {

	public void start() throws ServiceException;

	public void stop() throws ServiceException;

}
