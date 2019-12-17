package br.com.airon.actions.actionsapi.rest.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.airon.actions.actionsapi.rest.BaseCrudRest;
import br.com.airon.actions.actionsapi.services.CompanyStockService;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Path("/companystock")
public class CompanyStockRest extends BaseCrudRest<CompanyStockDTO, Long>{
	
	private CompanyStockService service;
	
	@Autowired
	public CompanyStockRest(CompanyStockService service) {
		super(service);
		this.service = service;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllActive")
	public List<CompanyStockDTO> findAllActive(){
		return service.findAllActive();
	}

}
