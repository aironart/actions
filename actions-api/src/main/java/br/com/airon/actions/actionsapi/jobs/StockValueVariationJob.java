package br.com.airon.actions.actionsapi.jobs;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsapi.mocks.CompanyStockMock;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Component("stockValueVariationJob")
public class StockValueVariationJob {

	private static final Logger log = LoggerFactory.getLogger(StockValueVariationJob.class);
	
	@Autowired
	private CompanyStockDAO companyStockDAO;
	
	/*
	 * Do not remove this injections, it is here to guarantee the PostConstruct's order
	 * Mock should initialize before job
	 * 
	 */
	@Autowired
	@SuppressWarnings("unused")
	private CompanyStockMock mock;
	
	@Scheduled(cron = "0/5 * * * * *")
	public void doJob() {
		List<CompanyStockDTO> findAll = companyStockDAO.findAll();
		
		Random rd = new Random();
		for (CompanyStockDTO companyStockDTO : findAll) {
			
			double minValue = companyStockDTO.getStockValue() * 0.9;
			minValue = Math.floor(minValue * 100) / 100;
			double maxValue = companyStockDTO.getStockValue() * 1.1;
			maxValue = Math.floor(maxValue * 100) / 100;
			double finalValue = rd.doubles(minValue, maxValue).findAny().getAsDouble();
			finalValue = Math.floor(finalValue * 100) / 100;
			log.info(companyStockDTO.getCompanyName() + ": value -> " + companyStockDTO.getStockValue());
			log.info(companyStockDTO.getCompanyName() + ": initValue -> " + minValue);
			log.info(companyStockDTO.getCompanyName() + ": finalValue -> " + maxValue);
			companyStockDTO.setStockValue(finalValue);
			log.info(companyStockDTO.getCompanyName() + ": changedValue -> " + companyStockDTO.getStockValue());
			
		}
	}
	
	public static void main(String[] args) {
		new StockValueVariationJob().doJob();
	}
	
}
