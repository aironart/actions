package br.com.airon.actions.actionsapi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Repository;

import br.com.airon.actions.actionsapi.dao.AccountMovimentationDAO;
import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;

@Repository("accountMovimentationDAO")
public class AccountMovimentationDAOImpl implements AccountMovimentationDAO {

	private static final List<AccountMovimentationDTO> list = new ArrayList<AccountMovimentationDTO>();

	@Override
	public AccountMovimentationDTO findById(Long id) {
		Optional<AccountMovimentationDTO> findFirst = list.stream().filter(acc -> acc.getId().equals(id)).findFirst();
		if (!findFirst.isPresent()) {
			throw new NotFoundException("Nao encontrei movimentacao de conta para o identificador " + id);
		}
		AccountMovimentationDTO itemFound = findFirst.get();
		return itemFound;
	}

	@Override
	public List<AccountMovimentationDTO> findAll() {
		return list;
	}

	@Override
	public AccountMovimentationDTO update(AccountMovimentationDTO dto) {
//		There is no why to update a movimentation
		return dto;
	}

	@Override
	public AccountMovimentationDTO insert(AccountMovimentationDTO dto) {
		list.add(dto);
		int index = list.indexOf(dto);
		dto.setId(new Long(index+1));
		return dto;
	}

	@Override
	public void delete(AccountMovimentationDTO dto) {
		deleteById(dto.getId());
	}

	@Override
	public void deleteById(Long id) {
		AccountMovimentationDTO dto = this.findById(id);
		list.remove(dto);
	}

	@Override
	public List<AccountMovimentationDTO> byAccount(Long idAccount){
		List<AccountMovimentationDTO> resultList = list.stream()
			.filter(item -> item.getAccountId().equals(idAccount))
			.collect(Collectors.toList());
		return resultList;
	}
	
	@Override
	public List<AccountMovimentationDTO> byCompanyStock(Long companyStockId){
		List<AccountMovimentationDTO> resultList = list.stream()
				.filter(item -> item.getCompanyStockId().equals(companyStockId))
				.collect(Collectors.toList());
		return resultList;
	}

}
