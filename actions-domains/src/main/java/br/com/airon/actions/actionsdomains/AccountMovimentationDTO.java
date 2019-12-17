package br.com.airon.actions.actionsdomains;

import java.util.Date;

public class AccountMovimentationDTO extends BaseDTO<Long> {

	public static final String MOVIMENTATION_TYPE_BUY = "B";
	public static final String MOVIMENTATION_TYPE_SELL = "S";
	public static final String MOVIMENTATION_TYPE_INITIAL_VALUE = "I";
	public static final String MOVIMENTATION_TYPE_DEPOSIT = "D";
	public static final String MOVIMENTATION_TYPE_WITHDRAW = "W";

	private Long id;
	private Long accountId;
	private Long companyStockId;

	private String movimentationType;
	private Double amount;

	private int quantity;
	
	private Date dataMovimentacao = new Date();

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

	public String getMovimentationType() {
		return movimentationType;
	}

	public void setMovimentationType(String movimentationType) {
		this.movimentationType = movimentationType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

}
