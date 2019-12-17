package br.com.airon.actions.actionsdomains;

public class AccountCompanyStockDTO extends BaseDTO<Long> {

	private Long id;
	private Long accountId;
	private Long companyStockId;
	private Long quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCompanyStockId() {
		return companyStockId;
	}

	public void setCompanyStockId(Long companyStockId) {
		this.companyStockId = companyStockId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
