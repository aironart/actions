package br.com.airon.actions.actionsapi.services;

import java.util.List;

import br.com.airon.actions.actionsdomains.CompanyStockDTO;

public interface CompanyStockService extends CrudService<CompanyStockDTO, Long>{

	/**
	 * Return all Active Company Stock
	 * @return
	 */
	public List<CompanyStockDTO> findAllActive();

}
