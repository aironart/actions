package br.com.airon.actions.actionsapi.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.airon.actions.actionsapi.dao.CrudDAO;
import br.com.airon.actions.actionsdomains.BaseDTO;

@Transactional
public abstract class BaseCrudService<T extends BaseDTO<ID>, ID extends Serializable> {

	protected CrudDAO<T, ID> dao;

	public BaseCrudService(CrudDAO<T, ID> dao) {
		this.dao = dao;
	}

	/**
	 * Override this method to make your own implementation for insert validation,
	 * otherwise always will return true
	 * 
	 * @param item
	 * @param field
	 * @param msg
	 * @throws Exception
	 */
	public boolean validateFieldsToInsert(T item) throws Exception {
		return true;
	}

	/**
	 * Override this method to make your own implementation for update validation,
	 * otherwise always will return true
	 * 
	 * @param item
	 * @param field
	 * @param msg
	 * @throws Exception
	 */
	public boolean validateFieldsToUpdate(T item) throws Exception {
		return true;
	}

	/**
	 * Find by id
	 * 
	 * @param id
	 * @return
	 */
	public T findById(ID id) {
		T findById = this.dao.findById(id);
		return findById;
	}

	/**
	 * Delete by id
	 * 
	 * @param id
	 */
	public void deleteById(ID id) {
		this.dao.deleteById(id);
	}

	/**
	 * Find all
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.dao.findAll();
	}

	/**
	 * Insert the item
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public T insert(T item) throws Exception {
		if (validateFieldsToInsert(item)) {
			item = dao.insert(item);
		}
		return item;
	}

	/**
	 * Update the item 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public T update(T item) throws Exception {
		if (validateFieldsToUpdate(item)) {
			item = dao.update(item);
		}
		return item;
	}
	
	/**
	 * Delete the item
	 * @param dto
	 */
	public void delete(T dto) {
		this.dao.delete(dto);
	}

}
