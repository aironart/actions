package br.com.airon.actions.actionsapi.dao;

import java.io.Serializable;
import java.util.List;

import br.com.airon.actions.actionsdomains.BaseDTO;

public interface CrudDAO<T  extends BaseDTO<ID>, ID extends Serializable> {

	public T findById(ID id);
	
	public List<T> findAll ();
	
	public T update(T dto);
	
	public T insert(T dto);
	
	public void delete(T dto);
	
	public void deleteById(ID id);
	
}
