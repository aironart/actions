package br.com.airon.actions.actionsapiclient.clients;

import java.util.List;

import javax.ws.rs.core.GenericType;

import br.com.airon.actions.actionsdomains.AccountDTO;

public class AccountClientImpl extends BaseCrudClient<AccountDTO, Long>{

	public AccountClientImpl(String hostName, int port) {
		super(hostName, port , "accounts", AccountDTO.class);
	}

	@Override
	public GenericType<List<AccountDTO>> getListGenericType() {
		return new GenericType<List<AccountDTO>>() {};
	}
	
	@Override
	public GenericType<AccountDTO> getGenericType() {
		return new GenericType<AccountDTO>() {};
	}

}
