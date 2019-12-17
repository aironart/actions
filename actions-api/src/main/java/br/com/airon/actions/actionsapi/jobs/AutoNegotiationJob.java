package br.com.airon.actions.actionsapi.jobs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.dao.AccountDAO;
import br.com.airon.actions.actionsapi.dao.AccountMovimentationDAO;
import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsapi.dao.StockMonitoringDAO;
import br.com.airon.actions.actionsapi.exceptions.NotEnoughMoneyException;
import br.com.airon.actions.actionsapi.services.AccountService;
import br.com.airon.actions.actionsdomains.AccountDTO;
import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;
import br.com.airon.actions.actionsdomains.StockMonitoringDTO;

@Component("autoNegotiationJob")
public class AutoNegotiationJob {

	private static final Logger log = LoggerFactory.getLogger(AutoNegotiationJob.class);
	
	@Autowired
	private StockMonitoringDAO stockMonitoringDAO;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private CompanyStockDAO companyStockDAO;
	
	@Autowired
	private AccountMovimentationDAO accountMovimentationDAO;
	
	@Scheduled(cron = "3/5 * * * * ? ")
	public void doJob() {
		List<StockMonitoringDTO> listMonitoring = stockMonitoringDAO.findAll();
		
		AccountDTO currentAccount = null;
		for (StockMonitoringDTO stockMonitoringDTO : listMonitoring) {
			CompanyStockDTO companyStock = companyStockDAO.findById(stockMonitoringDTO.getCompanyStockId());
			if(currentAccount == null || !currentAccount.getId().equals(stockMonitoringDTO.getAccountId())) {
				currentAccount = accountDAO.findById(stockMonitoringDTO.getAccountId());
			}
			if(currentAccount == null || !currentAccount.getActive()) {
				continue;
			}
			if(companyStock == null || !companyStock.getActive()) {
				continue;
			}
			AccountMovimentationDTO movimentation = new AccountMovimentationDTO();
			movimentation.setAccountId(currentAccount.getId());
			movimentation.setCompanyStockId(companyStock.getId());
			Double stockValue = companyStock.getStockValue();
			if(companyStock.getStockValue() <= stockMonitoringDTO.getBuyValue()) {
//				buy a stock
				Double accountBalancePermitted = currentAccount.getBalance() * (new Double(stockMonitoringDTO.getBalancePercentMax())/100);
				int stockQuantity = (int) Math.floor(accountBalancePermitted / stockValue);
				if(stockQuantity <= 0) {
					continue;
				}
				if(companyStock.getStockQuantity() < stockQuantity) {
					stockQuantity = companyStock.getStockQuantity().intValue();
				}
				
				if(stockQuantity == 0) {
					continue;
				}
				
				Double amount = (stockQuantity * stockValue) * -1;
				try {
					updateAccountBalance(currentAccount, amount);
					updateOwnedStock(currentAccount, companyStock, stockQuantity);
					companyStock.setStockQuantity(companyStock.getStockQuantity() - stockQuantity);
				} catch (Exception e) {
					continue;
				}
				movimentation.setAmount(amount);
				movimentation.setQuantity(stockQuantity);
				movimentation.setMovimentationType(AccountMovimentationDTO.MOVIMENTATION_TYPE_BUY);
				accountMovimentationDAO.insert(movimentation);
			}else if(companyStock.getStockValue() >= stockMonitoringDTO.getSellValue()) {
//				sell a stock
				int quantityToSell = 0;
				try {
					quantityToSell = accountService.getOwnedStock(currentAccount, companyStock.getId()).getQuantity().intValue();
				} catch (Exception e) {
					continue;
				}
				if(quantityToSell == 0) {
					continue;
				}
				
				Double amount = quantityToSell * stockValue;
				
				try {
					updateAccountBalance(currentAccount, amount);
					updateOwnedStock(currentAccount, companyStock, (quantityToSell * -1));

					companyStock.setStockQuantity(companyStock.getStockQuantity() + quantityToSell);
				} catch (Exception e) {
					continue;
				}
				movimentation.setAmount(amount);
				movimentation.setQuantity(quantityToSell);
				movimentation.setMovimentationType(AccountMovimentationDTO.MOVIMENTATION_TYPE_SELL);
				accountMovimentationDAO.insert(movimentation);
			}
		}
	}

	private void updateOwnedStock(AccountDTO currentAccount, CompanyStockDTO companyStock, int stockQuantity) throws Exception {
		try {
			accountService.updateOwnedStocks(currentAccount, companyStock.getId(), stockQuantity);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}

	private void updateAccountBalance(AccountDTO currentAccount, Double amount) throws Exception{
		try {
			accountService.executeAccountBalanceUpdate(currentAccount, amount);
		} catch(NotEnoughMoneyException e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
}
