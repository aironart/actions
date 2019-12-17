package br.com.airon.actions.actionsapi.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsapi.services.BaseCrudService;
import br.com.airon.actions.actionsapi.services.CompanyStockService;
import br.com.airon.actions.actionsapi.util.StringUtils;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Service("companyStockService")
public class CompanyStockServiceImpl extends BaseCrudService<CompanyStockDTO, Long> implements CompanyStockService {

	private CompanyStockDAO dao;
	
	@Autowired
	public CompanyStockServiceImpl(CompanyStockDAO dao) {
		super(dao);
		this.dao = dao;
	}

	private static final int ID = 1;
	private static final int COMPANY_NAME = 2;
	private static final int STOCK_QUANTITY = 3;
	private static final int STOCK_VALUE = 4;
	
	@Override
	public List<CompanyStockDTO> findAllActive(){
		return this.dao.findAllActive();
	}
	
	@Override
	public boolean validateFieldsToUpdate(CompanyStockDTO item) throws Exception{
		this.validateFields(item, ID, "Campo identificador ('id') nao informado");
		this.validateFields(item, COMPANY_NAME, "Campo nome da empresa ('companyName') requerido");
		this.validateFields(item, STOCK_QUANTITY, "Campo quantidade de acoes ('stockQuantity') requerido");
		this.validateFields(item, STOCK_VALUE, "Campo valor da acao ('stockValue') requerido");
		return true;
	}
	
	@Override
	public boolean validateFieldsToInsert(CompanyStockDTO item) throws Exception{
		this.validateFields(item, COMPANY_NAME, "Campo nome da empresa ('companyName') requerido");
		this.validateFields(item, STOCK_QUANTITY, "Campo email de notificacao ('stockQuantity') requerido");
		this.validateFields(item, STOCK_VALUE, "Campo saldo da conta ('stockValue') requerido");
		return true;
	}

	private void validateFields(CompanyStockDTO item, int field, String msg) throws Exception {
		switch (field) {
		case ID: {
			if (item.getId() == null || item.getId().longValue() == 0) {
				throw new Exception(msg);
			}
			break;
		}
		case COMPANY_NAME: {
			if (StringUtils.isBlank(item.getCompanyName())) {
				throw new Exception(msg);
			}
			break;
		}
		case STOCK_QUANTITY: {
			if(item.getStockQuantity() == null) {
				throw new Exception(msg);
			}
			break;
		}
		case STOCK_VALUE: {
			if(item.getStockValue() == null) {
				throw new Exception(msg);
			}
			break;
		}
		default: {
			throw new Exception("Campo nao identificado!");
		}
		}
	}
	
	
	
}
