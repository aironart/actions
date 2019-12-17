package br.com.airon.actions.actionsapi.services;

import java.io.Serializable;
import java.util.List;

import br.com.airon.actions.actionsdomains.BaseDTO;

public interface CrudService<T extends BaseDTO<ID>, ID extends Serializable> {

	public T findById(ID id);

	public void deleteById(ID id);

	public List<T> findAll();

	public T insert(T item) throws Exception;

	public T update(T item) throws Exception;

}
