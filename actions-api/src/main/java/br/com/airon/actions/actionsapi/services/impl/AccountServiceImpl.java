package br.com.airon.actions.actionsapi.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airon.actions.actionsapi.dao.AccountDAO;
import br.com.airon.actions.actionsapi.dao.AccountMovimentationDAO;
import br.com.airon.actions.actionsapi.exceptions.NotEnoughMoneyException;
import br.com.airon.actions.actionsapi.services.AccountService;
import br.com.airon.actions.actionsapi.services.BaseCrudService;
import br.com.airon.actions.actionsapi.util.StringUtils;
import br.com.airon.actions.actionsapi.webservices.quotation.client.QuotationClient;
import br.com.airon.actions.actionsdomains.AccountCompanyStockDTO;
import br.com.airon.actions.actionsdomains.AccountDTO;
import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;

@Service("accountService")
public class AccountServiceImpl extends BaseCrudService<AccountDTO, Long> implements AccountService{

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	public AccountServiceImpl(AccountDAO dao) {
		super(dao);
		this.accountDAO = dao;
	}

	private static final int ID = 1;
	private static final int EMAIL_NOTIFICATION = 2;
	private static final int ACCOUNT_OWNER = 3;
	private static final int BALANCE = 4;
	
	private static final String CURRENCY_REAL = "REAL";
	private static final String CURRENCY_DOLAR = "DOLAR";
	
	private AccountDAO accountDAO;
	
	@Autowired
	private QuotationClient quotationClient;

	@Autowired
	private AccountMovimentationDAO accountMovimentationDAO;
	
	@Override
	public AccountDTO findByID(Long accountId) {
		return accountDAO.findById(accountId);
	}
	
	@Override
	public AccountDTO createAccount(AccountDTO account) {
		account = accountDAO.insert(account);
		AccountMovimentationDTO movimentation = new AccountMovimentationDTO();
		movimentation.setAccountId(account.getId());
		movimentation.setAmount(account.getBalance());
		movimentation.setMovimentationType(AccountMovimentationDTO.MOVIMENTATION_TYPE_INITIAL_VALUE);
		accountMovimentationDAO.insert(movimentation);
		return account;
	}
	
	@Override
	public synchronized void executeAccountBalanceUpdate(AccountDTO account, Double amount)
			throws NotEnoughMoneyException {
		if (amount < 0 && (account.getBalance() + amount) < 0) {
			throw new NotEnoughMoneyException();
		}
		account.setBalance(account.getBalance() + amount);

		this.accountDAO.update(account);
	}

	@Override
	public void updateOwnedStocks(AccountDTO account, Long companyStockId, int quantity) throws Exception{
		Optional<AccountCompanyStockDTO> findFirst = account.getOwnedStocks().stream()
				.filter(owned -> owned.getCompanyStockId().equals(companyStockId)).findFirst();
		if (findFirst.isPresent()) {
			AccountCompanyStockDTO owned = findFirst.get();
			owned.setQuantity(owned.getQuantity() + quantity);
			if(owned.getQuantity().longValue() == 0l) {
				account.getOwnedStocks().remove(owned); 
			}
		} else if(quantity > 0){
			AccountCompanyStockDTO stockOwned = new AccountCompanyStockDTO();
			stockOwned.setAccountId(account.getId());
			stockOwned.setCompanyStockId(companyStockId);
			stockOwned.setQuantity(new Long(quantity));
			account.getOwnedStocks().add(stockOwned);
		}else {
			throw new Exception ("Zero quantity to new Owned Stock");
		}
	}
	
	@Override
	public AccountCompanyStockDTO getOwnedStock(AccountDTO account, Long companyStockId) throws NotFoundException {
		Optional<AccountCompanyStockDTO> findFirst = account.getOwnedStocks().stream()
			.filter(owned -> owned.getCompanyStockId().equals(companyStockId)).findFirst();
		
		if(findFirst.isPresent()) {
			return findFirst.get();
		}else {
			throw new NotFoundException("nao encontrei acao propria para o identificador "+ companyStockId +" informado");
		}
	}
	
	private Map<String, Object> validateRequestAccountActions(String account, String amount, String currency, Long quotationDolarOperation) throws Exception{
		if(StringUtils.isBlank(account) || StringUtils.isBlank(amount) || StringUtils.isBlank(currency)) {
			throw new Exception("Preencher campos obrigatorios (account, amount e currency)");
		}
		
		if(!CURRENCY_REAL.equalsIgnoreCase(currency) && !CURRENCY_DOLAR.equalsIgnoreCase(currency)) {
			throw new Exception("Preencher o campo currency com 'REAL' ou 'DOLAR'");
		}
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			Long accountId = Long.parseLong(account);
			AccountDTO accountFound = accountDAO.findById(accountId);
			result.put("account", accountFound);
			
		} catch (NumberFormatException e) {
			throw new Exception("Valor incorreto para o campo account");
		}
		
		boolean isReal = CURRENCY_REAL.equalsIgnoreCase(currency);
		Double dolarQuotation = null;
		
		if(isReal) {
			try {
				dolarQuotation = Math.floor(quotationClient.getDolarQuotation(quotationDolarOperation).doubleValue() * 100) / 100;
			}catch(Exception e) {
				log.error(e.getMessage(), e);
				throw new Exception("Erro ao buscar a cotacao do Dolar, operacao nao pode ser continuada", e);
			}
		}
		
		try {
			Double amountDouble = Double.parseDouble(amount);
			if(isReal) {
				amountDouble = dolarQuotation * amountDouble;
			}
			result.put("amount", amountDouble);
		} catch (NumberFormatException e) {
			throw new Exception("Valor incorreto para o campo amount");
		}
		
		return result;
		
	}

	@Override
	public boolean validateFieldsToUpdate(AccountDTO item) throws Exception {
		throw new Exception ("Voce nao pode fazer alteracao por servico");
	}
	
	@Override
	public boolean validateFieldsToInsert(AccountDTO item) throws Exception {
		this.validateAccount(item, ACCOUNT_OWNER, "Campo dono da conta ('accountOwner') requerido");
		this.validateAccount(item, EMAIL_NOTIFICATION, "Campo email de notificacao ('emailNotification') requerido");
		this.validateAccount(item, BALANCE, "Campo saldo da conta ('balance') requerido");

		return super.validateFieldsToInsert(item);
	}
	

	private void validateAccount(AccountDTO account, int field, String msg) throws Exception {
		switch (field) {
		case ID: {
			if (account.getId() == null || account.getId().longValue() == 0) {
				throw new Exception(msg);
			}
			break;
		}
		case EMAIL_NOTIFICATION: {
			if (StringUtils.isBlank(account.getEmailNotification())) {
				throw new Exception(msg);
			}
			break;
		}
		case ACCOUNT_OWNER: {
			if(StringUtils.isBlank(account.getAccountOwner())) {
				throw new Exception(msg);
			}
			break;
		}
		case BALANCE: {
			if(account.getBalance() == null) {
				throw new Exception(msg);
			}
			break;
		}
		default: {
			throw new Exception("Campo nao identificado!");
		}
		}
	}
	
	@Override
	public void deposit(String accountId, String amount, String currency) throws Exception{
		Map<String, Object> validateRequestAccountActions = validateRequestAccountActions(accountId, amount, currency, QuotationClient.QUOTATION_DOLAR_BUY);
		
		AccountDTO acc = (AccountDTO) validateRequestAccountActions.get("account");
		Double amountDouble = (Double) validateRequestAccountActions.get("amount");
		
		this.executeAccountBalanceUpdate(acc, amountDouble);

		insertAccountMovimentation(acc.getId(), amountDouble, AccountMovimentationDTO.MOVIMENTATION_TYPE_DEPOSIT);
	}
	
	@Override
	public void withdraw(String accountId, String amount, String currency) throws Exception {
		Map<String, Object> validateRequestAccountActions = validateRequestAccountActions(accountId, amount, currency, QuotationClient.QUOTATION_DOLAR_SELL);
		
		AccountDTO acc = (AccountDTO) validateRequestAccountActions.get("account");
		Double amountDouble = (Double) validateRequestAccountActions.get("amount");
		
		if(amountDouble > 0) {
			amountDouble *= -1;
		}
		
		this.executeAccountBalanceUpdate(acc, amountDouble);
		
		insertAccountMovimentation(acc.getId(), amountDouble, AccountMovimentationDTO.MOVIMENTATION_TYPE_WITHDRAW);
	}
	
	private void insertAccountMovimentation(Long accountId, Double amount, String movimentationType) {
		AccountMovimentationDTO mov = new AccountMovimentationDTO();
		mov.setAccountId(accountId);
		mov.setAmount(amount);
		mov.setMovimentationType(movimentationType);
		accountMovimentationDAO.insert(mov);
	}
	
	@Override
	public List<AccountDTO> findAllActive(){
		return accountDAO.findAllActive();
	}

}
