package br.com.airon.actions.actionsapi.services;

import java.util.List;

import javax.ws.rs.NotFoundException;

import br.com.airon.actions.actionsapi.exceptions.NotEnoughMoneyException;
import br.com.airon.actions.actionsdomains.AccountCompanyStockDTO;
import br.com.airon.actions.actionsdomains.AccountDTO;

public interface AccountService extends CrudService<AccountDTO, Long>{

	/** 
	 * Execute account balance update
	 * @param account
	 * @param amount - Accept positive and negative values
	 * @throws NotEnoughMoneyException
	 */
	public void executeAccountBalanceUpdate(AccountDTO account, Double amount) throws NotEnoughMoneyException;

	/**
	 * Update owned stocks
	 * 
	 * @param account
	 * @param companyStockId
	 * @param quantity - Accept positive and negative values
	 * @throws Exception - if zero quantity on new Owned Stock
	 */
	public void updateOwnedStocks(AccountDTO account, Long companyStockId, int quantity) throws Exception;

	/**
	 * Return owned stock by id Company Stock
	 * @param account
	 * @param companyStockId
	 * @return
	 * @throws NotFoundException
	 */
	public AccountCompanyStockDTO getOwnedStock(AccountDTO account, Long companyStockId) throws NotFoundException;

	/**
	 * Find account by ID
	 * @param accountId
	 * @return
	 */
	public AccountDTO findByID(Long accountId);

	/**
	 * Create account on Database and insert the movimentation historic
	 * @param account
	 * @return
	 */
	public AccountDTO createAccount(AccountDTO account);

	/**
	 * Make a deposit
	 * @param accountId
	 * @param amount
	 * @param currency - Accept "real" or "dolar"
	 * @throws Exception
	 */
	public void deposit(String accountId, String amount, String currency) throws Exception;

	/**
	 * Make a withdraw
	 * @param accountId
	 * @param amount
	 * @param currency - Accept "real" or "dolar"
	 * @throws Exception
	 */
	public void withdraw(String accountId, String amount, String currency) throws Exception;

	/**
	 * Find All Active
	 * @return
	 */
	public List<AccountDTO> findAllActive();
	
}
