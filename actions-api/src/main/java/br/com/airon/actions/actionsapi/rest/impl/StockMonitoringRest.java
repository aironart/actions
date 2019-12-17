package br.com.airon.actions.actionsapi.rest.impl;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.airon.actions.actionsapi.rest.BaseCrudRest;
import br.com.airon.actions.actionsapi.services.StockMonitoringService;
import br.com.airon.actions.actionsdomains.StockMonitoringDTO;

@Path("/stockmonitoring")
public class StockMonitoringRest extends BaseCrudRest<StockMonitoringDTO, Long>{

	@Autowired
	public StockMonitoringRest(StockMonitoringService service) {
		super(service);
	}
	
}
