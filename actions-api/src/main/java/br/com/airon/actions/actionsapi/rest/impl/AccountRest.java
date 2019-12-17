package br.com.airon.actions.actionsapi.rest.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.airon.actions.actionsapi.rest.BaseCrudRest;
import br.com.airon.actions.actionsapi.services.AccountService;
import br.com.airon.actions.actionsdomains.AccountDTO;

@Path("/accounts")
public class AccountRest extends BaseCrudRest<AccountDTO, Long>{

	@Autowired
	public AccountRest(AccountService service) {
		super(service);
	}

	@Autowired
	private AccountService accountService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/withdraw")
	public String withdraw(@QueryParam("account") String accountId, @QueryParam("amount") String amount, @QueryParam("currency") String currency) {
		try {
			accountService.withdraw(accountId, amount, currency);
		}catch(Exception e) {
			if(e instanceof NotFoundException) {
				throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build() );
			}else {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build() );
			}
			
		}
		return "OK";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deposit")
	public String deposit(@QueryParam("account") String accountId, @QueryParam("amount") String amount, @QueryParam("currency") String currency) {
		try {
			accountService.deposit(accountId, amount, currency);
		}catch(Exception e) {
			if(e instanceof NotFoundException) {
				throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build() );
			}else {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build() );
			}
			
		}
		return "OK";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllActive")
	public List<AccountDTO> findAllActive(){
		return accountService.findAllActive();
	}
	
}
