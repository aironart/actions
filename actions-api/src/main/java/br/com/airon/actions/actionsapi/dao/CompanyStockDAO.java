package br.com.airon.actions.actionsapi.dao;

import java.util.List;

import br.com.airon.actions.actionsdomains.CompanyStockDTO;

public interface CompanyStockDAO extends CrudDAO<CompanyStockDTO, Long> {

	public List<CompanyStockDTO> findAllActive();

}
