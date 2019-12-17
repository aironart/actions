package br.com.airon.actions.actionsapi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Repository;

import br.com.airon.actions.actionsapi.dao.AccountDAO;
import br.com.airon.actions.actionsdomains.AccountDTO;

/**
 * Classe que comunicaria com o Banco de dados <br>
 * Devido a necessidade de fazer rodar facil o aplicativo, estou fazendo a
 * persistencia em memoria
 * 
 * @author airon
 *
 */
@Repository("accountDAO")
public class AccountDAOImpl implements AccountDAO {

	private static List<AccountDTO> accounts = new ArrayList<>();

	@Override
	public AccountDTO findById(Long id) {
		Optional<AccountDTO> findFirst = accounts.stream().filter(acc -> acc.getId().equals(id)).findFirst();
		if (!findFirst.isPresent()) {
			throw new NotFoundException("Nao encontrei conta para o identificador " + id);
		}
		AccountDTO accountFound = findFirst.get();
		return accountFound;
	}

	@Override
	public List<AccountDTO> findAll() {
		// Do not show account balance on findAll
		List<AccountDTO> resultList = accounts.stream().map(account -> {
			AccountDTO newAccount = new AccountDTO();
			newAccount.setAccountOwner(account.getAccountOwner());
			newAccount.setEmailNotification(account.getEmailNotification());
			newAccount.setId(account.getId());
			return newAccount;
		}).collect(Collectors.toList());
		return resultList;
	}
	
	@Override
	public List<AccountDTO> findAllActive() {
		// Do not show account balance on findAll
		List<AccountDTO> resultList = this.findAll().stream().filter(acc -> acc.getActive()).collect(Collectors.toList());
		return resultList;
	}

	@Override
	public AccountDTO update(AccountDTO dto) {
		// Object's reference has come, do not need to do anything 
		return dto;
	}

	@Override
	public AccountDTO insert(AccountDTO newAccount) {
		accounts.add(newAccount);
		int index = accounts.indexOf(newAccount);
		newAccount.setId(new Long(index+1));
		return newAccount;
	}
	
	@Override
	public void delete(AccountDTO dto) {
		dto.setActive(false);
	}

	@Override
	public void deleteById(Long id) {
		this.delete(this.findById(id));
	}

}
