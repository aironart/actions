package br.com.airon.actions.actionsapi.mocks;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Component("companyStockMock")
@Priority(0)
public class CompanyStockMock {

	@Autowired
	private CompanyStockDAO companyStockDAO;

	@PostConstruct
	public void mock() {
		CompanyStockDTO dto = new CompanyStockDTO();
		dto.setCompanyName("Cartoon Network");
		dto.setStockQuantity(1000l);
		dto.setStockValue(260.35);
		
		companyStockDAO.insert(dto);
		
		dto = new CompanyStockDTO();
		dto.setCompanyName("Nickelodeon");
		dto.setStockQuantity(500l);
		dto.setStockValue(312.96);
		
		companyStockDAO.insert(dto);
		
	}
	
}
