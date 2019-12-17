package br.com.airon.actions.actionsapi.exceptions;

public class NotEnoughMoneyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughMoneyException() {
		super("Sem saldo suficiente para realizar este saque");
	}

}
