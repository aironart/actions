package br.com.airon.actions.actionsdomains;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO extends BaseDTO<Long> {

	private Long id;
	private String emailNotification;
	private String accountOwner;
	private Double balance;
	private List<AccountCompanyStockDTO> ownedStocks = new ArrayList<AccountCompanyStockDTO>();
	private Boolean active = Boolean.TRUE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<AccountCompanyStockDTO> getOwnedStocks() {
		return ownedStocks;
	}

	public void setOwnedStocks(List<AccountCompanyStockDTO> ownedStocks) {
		this.ownedStocks = ownedStocks;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
