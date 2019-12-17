package br.com.airon.actions.actionsapi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Repository;

import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Repository("companyStockDAO")
public class CompanyStockDAOImpl implements CompanyStockDAO {

	private static final List<CompanyStockDTO> list = new ArrayList<>();
	
	@Override
	public CompanyStockDTO findById(Long id) {
		Optional<CompanyStockDTO> findFirst = list.stream().filter(cs -> cs.getId().equals(id)).findFirst();
		if (!findFirst.isPresent()) {
			throw new NotFoundException("Nao encontrei Acao de Empresa para o identificador " + id);
		}
		CompanyStockDTO item = findFirst.get();
		return item;
	}

	@Override
	public List<CompanyStockDTO> findAll() {
		return list;
	}

	@Override
	public CompanyStockDTO update(CompanyStockDTO dto) {
		CompanyStockDTO item = this.findById(dto.getId());
		item.setCompanyName(dto.getCompanyName());
		item.setStockQuantity(dto.getStockQuantity());
		item.setStockValue(dto.getStockValue());
		return item;
	}

	@Override
	public CompanyStockDTO insert(CompanyStockDTO dto) {
		list.add(dto);
		int index = list.indexOf(dto);
		dto.setId(new Long(index+1));
		return dto;
	}

	@Override
	public void delete(CompanyStockDTO dto) {
		dto.setActive(false);
	}

	@Override
	public void deleteById(Long id) {
		this.delete(this.findById(id));
	}
	
	@Override
	public List<CompanyStockDTO> findAllActive(){
		List<CompanyStockDTO> allActive = this.findAll().stream().filter(cs -> cs.getActive()).collect(Collectors.toList());
		return allActive;
	}

}
