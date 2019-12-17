package br.com.airon.actions.actionsapi.rest.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.airon.actions.actionsapi.services.AccountMovimentationService;
import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;

@Path("/accountmovimentation")
public class AccountMovimentationRest {
	
	private static final Logger log = LoggerFactory.getLogger(AccountMovimentationRest.class);

	@Autowired
	private AccountMovimentationService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/byAccountId/{accountId}")
	public List<AccountMovimentationDTO> findByAccount(@PathParam("accountId") Long accountId){
		List<AccountMovimentationDTO> resultList = this.service.byAccount(accountId);
		return resultList;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/toEmailByAccountId/{accountId}")
	public String findByAccountToEmail(@PathParam("accountId") Long accountId){
		try {
		String emailHtml = this.service.byAccountToEmail(accountId);
		return emailHtml;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build());
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/byCompanyStockId/{companyStockId}")
	public List<AccountMovimentationDTO> findByCompanyStock(@PathParam("companyStockId") Long companyStockId){
		List<AccountMovimentationDTO> resultList = this.service.byCompanyStock(companyStockId);
		return resultList;
	}

}
