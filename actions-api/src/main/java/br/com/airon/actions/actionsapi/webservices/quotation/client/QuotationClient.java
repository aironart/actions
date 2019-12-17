package br.com.airon.actions.actionsapi.webservices.quotation.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import br.com.airon.actions.actionsapi.webservices.quotation.FachadaWSSGS.FachadaWSSGSProxy;
import br.com.airon.actions.actionsapi.webservices.quotation.common.WSSerieVO;

@Component("quotationClient")
public class QuotationClient {
	
	public static final Long QUOTATION_DOLAR_SELL = 1L;
	public static final Long QUOTATION_DOLAR_BUY = 10813L;

	public static void main(String[] args) throws Exception{
		BigDecimal valor = new QuotationClient().getDolarQuotation(QUOTATION_DOLAR_SELL);
		
		System.out.println(valor);
	}

	public BigDecimal getDolarQuotation(long serie) throws Exception{
		FachadaWSSGSProxy proxy = new FachadaWSSGSProxy();
		WSSerieVO lastValueVo = proxy.getUltimoValorVO(serie);
		BigDecimal returnValue = lastValueVo.getUltimoValor().getValor();
		return returnValue;
	}
	
}
