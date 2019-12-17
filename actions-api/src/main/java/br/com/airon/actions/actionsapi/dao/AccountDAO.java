package br.com.airon.actions.actionsapi.dao;

import java.util.List;

import br.com.airon.actions.actionsdomains.AccountDTO;

public interface AccountDAO extends CrudDAO<AccountDTO, Long>{

	public List<AccountDTO> findAllActive();

}
