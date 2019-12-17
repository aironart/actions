package br.com.airon.actions.actionsapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airon.actions.actionsapi.dao.AccountDAO;
import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsapi.dao.StockMonitoringDAO;
import br.com.airon.actions.actionsapi.services.BaseCrudService;
import br.com.airon.actions.actionsapi.services.StockMonitoringService;
import br.com.airon.actions.actionsdomains.StockMonitoringDTO;

@Service("stockMonitoringService")
public class StockMonitoringServiceImpl extends BaseCrudService<StockMonitoringDTO, Long>
		implements StockMonitoringService {

	@Autowired
	public StockMonitoringServiceImpl(StockMonitoringDAO dao) {
		super(dao);
	}

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private CompanyStockDAO companyStockDAO;

	private static final int FIELD_ID = 1;
	private static final int FIELD_ACCOUNT_ID = 2;
	private static final int FIELD_COMPANY_STOCK_ID = 3;
	private static final int FIELD_BUY_VALUE = 4;
	private static final int FIELD_SELL_VALUE = 5;
	private static final int FIELD_BALANCE_PERCENT_MAX = 6;

	@Override
	public boolean validateFieldsToInsert(StockMonitoringDTO item) throws Exception {
		validateField(item, FIELD_BALANCE_PERCENT_MAX,
				"Campo maximo porcentual do saldo ('balancePercentMax') deve ser preenchido no intervalo de 1 a 100");
		validateField(item, FIELD_SELL_VALUE, "Campo Valor de venda ('sellValue') deve ser preenchido e maior que 0");
		validateField(item, FIELD_BUY_VALUE, "Campo Valor de compra ('buyValue') deve ser preenchido e maior que 0");
		validateField(item, FIELD_COMPANY_STOCK_ID,
				"Campo Identificador de Ação da Empresa ('companyStockId') deve ser preenchido e válido");
		validateField(item, FIELD_ACCOUNT_ID,
				"Campo Identificador de Conta ('accountId') deve ser preenchido e válido");
		return super.validateFieldsToInsert(item);
	}

	@Override
	public boolean validateFieldsToUpdate(StockMonitoringDTO item) throws Exception {
		validateField(item, FIELD_BALANCE_PERCENT_MAX,
				"Campo maximo porcentual do saldo ('balancePercentMax') deve ser preenchido no intervalo de 1 a 100");
		validateField(item, FIELD_SELL_VALUE, "Campo Valor de venda ('sellValue') deve ser preenchido e maior que 0");
		validateField(item, FIELD_BUY_VALUE, "Campo Valor de compra ('buyValue') deve ser preenchido e maior que 0");
		validateField(item, FIELD_COMPANY_STOCK_ID,
				"Campo Identificador de Ação da Empresa ('companyStockId') deve ser preenchido e válido");
		validateField(item, FIELD_ACCOUNT_ID,
				"Campo Identificador de Conta ('accountId') deve ser preenchido e válido");
		validateField(item, FIELD_ID, "Campo Identificador de Monitoramento ('id') deve ser preenchido e válido");
		return super.validateFieldsToUpdate(item);
	}

	private void validateField(StockMonitoringDTO item, int field, String msg) throws Exception {
		switch (field) {
		case FIELD_ID: {
			if (item.getId() == null || item.getId().longValue() == 0) {
				throw new Exception(msg);
			}
			this.dao.findById(item.getId());
			break;
		}
		case FIELD_ACCOUNT_ID: {
			if (item.getAccountId() == null || item.getAccountId().longValue() == 0) {
				throw new Exception(msg);
			}

			accountDAO.findById(item.getAccountId());
			break;
		}
		case FIELD_COMPANY_STOCK_ID: {
			if (item.getCompanyStockId() == null || item.getCompanyStockId().longValue() == 0) {
				throw new Exception(msg);
			}

			companyStockDAO.findById(item.getCompanyStockId());
			break;
		}
		case FIELD_BUY_VALUE: {
			if (item.getBuyValue() == null || item.getBuyValue().longValue() <= 0) {
				throw new Exception(msg);
			}
			break;
		}
		case FIELD_SELL_VALUE: {
			if (item.getSellValue() == null || item.getSellValue().longValue() <= 0) {
				throw new Exception(msg);
			}
			break;
		}
		case FIELD_BALANCE_PERCENT_MAX: {
			if (item.getBalancePercentMax() <= 0 || item.getBalancePercentMax() > 100) {
				throw new Exception(msg);
			}
			break;
		}
		default: {
			throw new Exception("Campo nao identificado!");
		}
		}
	}
}
