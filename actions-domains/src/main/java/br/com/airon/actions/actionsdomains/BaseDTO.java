package br.com.airon.actions.actionsdomains;

import java.io.Serializable;

public abstract class BaseDTO<ID extends Serializable> {

	public abstract ID getId();
	
	public abstract void setId(ID id);
	
}
