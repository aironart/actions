package br.com.airon.actions.actionsdomains;

public class StockMonitoringDTO extends BaseDTO<Long> {

	private Long id;
	private Long accountId;
	private Long companyStockId;
	private Double buyValue;
	private Double sellValue;
	private int balancePercentMax;

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

	public Double getBuyValue() {
		return buyValue;
	}

	public void setBuyValue(Double buyValue) {
		this.buyValue = buyValue;
	}

	public Double getSellValue() {
		return sellValue;
	}

	public void setSellValue(Double sellValue) {
		this.sellValue = sellValue;
	}

	public int getBalancePercentMax() {
		return balancePercentMax;
	}

	public void setBalancePercentMax(int balancePercentMax) {
		this.balancePercentMax = balancePercentMax;
	}

}
