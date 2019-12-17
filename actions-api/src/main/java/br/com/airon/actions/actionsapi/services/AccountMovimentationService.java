package br.com.airon.actions.actionsapi.services;

import java.util.List;

import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;

public interface AccountMovimentationService {

	public List<AccountMovimentationDTO> byAccount(Long idAccount);

	public List<AccountMovimentationDTO> byCompanyStock(Long companyStockId);

	public String byAccountToEmail(Long idAccount) throws Exception;

}
