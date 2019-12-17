package br.com.airon.actions.actionsapi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Repository;

import br.com.airon.actions.actionsapi.dao.StockMonitoringDAO;
import br.com.airon.actions.actionsdomains.StockMonitoringDTO;

@Repository("stockMonitoringDAO")
public class StockMonitoringDAOImpl implements StockMonitoringDAO {

	private static final List<StockMonitoringDTO> list = new ArrayList<>();
	
	@Override
	public StockMonitoringDTO findById(Long id) {
		Optional<StockMonitoringDTO> findFirst = list.stream().filter(cs -> cs.getId().equals(id)).findFirst();
		if (!findFirst.isPresent()) {
			throw new NotFoundException("Nao encontrei Monitoramento para o identificador " + id);
		}
		StockMonitoringDTO item = findFirst.get();
		return item;
	}

	@Override
	public List<StockMonitoringDTO> findAll() {
		return list;
	}

	@Override
	public StockMonitoringDTO update(StockMonitoringDTO dto) {
		StockMonitoringDTO item = this.findById(dto.getId());
		item.setAccountId(dto.getAccountId());
		item.setBalancePercentMax(dto.getBalancePercentMax());
		item.setBuyValue(dto.getBuyValue());
		item.setCompanyStockId(dto.getCompanyStockId());
		item.setSellValue(dto.getSellValue());
		return item;
	}

	@Override
	public StockMonitoringDTO insert(StockMonitoringDTO dto) {
		list.add(dto);
		int index = list.indexOf(dto);
		dto.setId(new Long(index+1));
		return dto;
	}

	@Override
	public void delete(StockMonitoringDTO dto) {
		deleteById(dto.getId());
	}

	@Override
	public void deleteById(Long id) {
		StockMonitoringDTO dto = this.findById(id);
		list.remove(dto);
	}

}
