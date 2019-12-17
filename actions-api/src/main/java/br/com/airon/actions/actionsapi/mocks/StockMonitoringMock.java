package br.com.airon.actions.actionsapi.mocks;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.dao.StockMonitoringDAO;
import br.com.airon.actions.actionsdomains.StockMonitoringDTO;

@Component("stockMonitoringMock")
public class StockMonitoringMock {

	private StockMonitoringDAO stockMonitoringDAO;

	@PostConstruct
	public void mock() {
		StockMonitoringDTO dto = new StockMonitoringDTO();
		dto.setAccountId(1l);
		dto.setCompanyStockId(1l);
		dto.setBuyValue(245d);
		dto.setSellValue(265d);
		dto.setBalancePercentMax(50);

		stockMonitoringDAO.insert(dto);

		dto = new StockMonitoringDTO();
		dto.setAccountId(2l);
		dto.setCompanyStockId(1l);
		dto.setBuyValue(235d);
		dto.setSellValue(305d);
		dto.setBalancePercentMax(50);

		stockMonitoringDAO.insert(dto);

	}
}
