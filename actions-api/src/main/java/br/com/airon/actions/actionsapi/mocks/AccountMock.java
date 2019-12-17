package br.com.airon.actions.actionsapi.mocks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.services.AccountService;
import br.com.airon.actions.actionsdomains.AccountDTO;

@Component("accountMock")
public class AccountMock {

	@Autowired
	private AccountService service;
	
	@PostConstruct
	private void mock() {
		AccountDTO dto = new AccountDTO();
		
		dto.setAccountOwner("Spider Man");
		dto.setBalance(5000d);
		dto.setEmailNotification("spiderman@marvel.com");
		service.createAccount(dto);
		
		dto = new AccountDTO();
		
		dto.setAccountOwner("Peppa pig");
		dto.setBalance(1500d);
		dto.setEmailNotification("peppapig@nick.com");
		service.createAccount(dto);
		
		dto = new AccountDTO();
		
		dto.setAccountOwner("Galinha Pintadinha");
		dto.setBalance(7800d);
		dto.setEmailNotification("galinha@pinta.dinha");
		service.createAccount(dto);
		
		dto = new AccountDTO();
		
		dto.setAccountOwner("Bob Zoom");
		dto.setBalance(8900d);
		dto.setEmailNotification("bob@zoom.co");
		service.createAccount(dto);
		
	}

}
