package br.com.airon.actions.actionsapi.dao;

import java.util.List;

import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;

public interface AccountMovimentationDAO extends CrudDAO<AccountMovimentationDTO, Long> {

	public List<AccountMovimentationDTO> byAccount(Long idAccount);

	public List<AccountMovimentationDTO> byCompanyStock(Long companyStockId);

}
