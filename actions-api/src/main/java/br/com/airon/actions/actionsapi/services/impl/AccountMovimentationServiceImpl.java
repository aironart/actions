package br.com.airon.actions.actionsapi.services.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.airon.actions.actionsapi.dao.AccountDAO;
import br.com.airon.actions.actionsapi.dao.AccountMovimentationDAO;
import br.com.airon.actions.actionsapi.dao.CompanyStockDAO;
import br.com.airon.actions.actionsapi.services.AccountMovimentationService;
import br.com.airon.actions.actionsdomains.AccountDTO;
import br.com.airon.actions.actionsdomains.AccountMovimentationDTO;
import br.com.airon.actions.actionsdomains.CompanyStockDTO;

@Service("accountMovimentationService")
public class AccountMovimentationServiceImpl implements AccountMovimentationService {

	@Autowired
	private AccountMovimentationDAO accountMovimentationDAO;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private CompanyStockDAO companyStockDAO;

	@Autowired
	private JavaMailSender javaMailSender;
	
	private SimpleDateFormat formatterDiaMesAnoHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public AccountMovimentationServiceImpl() {
	}

	@Override
	public List<AccountMovimentationDTO> byAccount(Long idAccount) {
		return accountMovimentationDAO.byAccount(idAccount);
	}

	private String getMovimentationTypeLabel(String type) {
		if (AccountMovimentationDTO.MOVIMENTATION_TYPE_BUY.equalsIgnoreCase(type)) {
			return "Compra";
		} else if (AccountMovimentationDTO.MOVIMENTATION_TYPE_SELL.equalsIgnoreCase(type)) {
			return "Venda";
		} else if (AccountMovimentationDTO.MOVIMENTATION_TYPE_INITIAL_VALUE.equalsIgnoreCase(type)) {
			return "Saldo Inicial";
		} else if (AccountMovimentationDTO.MOVIMENTATION_TYPE_DEPOSIT.equalsIgnoreCase(type)) {
			return "Deposito";
		} else if (AccountMovimentationDTO.MOVIMENTATION_TYPE_WITHDRAW.equalsIgnoreCase(type)) {
			return "Saque";
		}
		return "";
	}

	@Override
	public String byAccountToEmail(Long idAccount) throws Exception {
		List<AccountMovimentationDTO> listByAccount = accountMovimentationDAO.byAccount(idAccount);

		List<CompanyStockDTO> findAllCompanies = companyStockDAO.findAll();
		Map<Long, CompanyStockDTO> companies = new HashMap<>();
		for (CompanyStockDTO companyStockDTO : findAllCompanies) {
			companies.put(companyStockDTO.getId(), companyStockDTO);
		}
		MimeMessage message = javaMailSender.createMimeMessage();

		message.setHeader("Content-Type", "text/plain; charset=utf-8");
		message.setHeader("Content-Encoding", "utf-8");

		StringBuilder email = new StringBuilder();
		email.append("<html><head>");
		email.append("<style type=\"text/css\">        ");
		email.append("	table {                        ");
		email.append("		border-spacing: 0px;       ");
		email.append("		text-align: center;        ");
		email.append("	}                              ");
	    email.append("                                 ");
		email.append("	th,                            ");
		email.append("	td {                           ");
		email.append("		padding: 5px;              ");
		email.append("	}                              ");
	    email.append("                                 ");
		email.append("	tr td:last-child {             ");
		email.append("		color: darkgreen;          ");
		email.append("		font-weight: bold;         ");
		email.append("	}                              ");
	    email.append("                                 ");
		email.append("	tbody tr:nth-child(odd) {      ");
		email.append("		background-color: #eeeeee; ");
		email.append("	}                              ");
		email.append("</style>                         ");
		email.append("</head><body><table><thead><tr>");
		email.append("<th>#</th>");
		email.append("<th>Data</th>");
		email.append("<th>Empresa</th>");
		email.append("<th>Valor unitario</th>");
		email.append("<th>Quantidade</th>");
		email.append("<th>Valor</th>");
		email.append("<th>Tipo Movimentacao</th>");
		email.append("<th>Saldo</th>");
		email.append("</tr></thead>");
		email.append("<tbody>");

		Double saldo = 0d;
		for (int i = 0; i < listByAccount.size(); i++) {
			AccountMovimentationDTO accountMovimentationDTO = listByAccount.get(i);
			email.append("<tr>");
			email.append("<td>" + i + "</td>");
			email.append("<td>");
			email.append(formatterDiaMesAnoHora.format(accountMovimentationDTO.getDataMovimentacao()));
			email.append("</td>");
			email.append("<td>");
			if (accountMovimentationDTO.getCompanyStockId() != null) {
				email.append(companies.get(accountMovimentationDTO.getCompanyStockId()).getCompanyName());
			} 
			email.append("</td>");
			email.append("<td>");
			if(accountMovimentationDTO.getQuantity() > 0) {
				double valorUnitario = Math.floor(accountMovimentationDTO.getAmount() / accountMovimentationDTO.getQuantity() * 100) / 100;
				if(valorUnitario < 0) {
					valorUnitario *= -1;
				}
				email.append(valorUnitario);
			}
			email.append("</td>");
			email.append("<td>" + accountMovimentationDTO.getQuantity() + "</td>");
			email.append("<td>" + Math.floor(accountMovimentationDTO.getAmount() * 100) / 100 + "</td>");
			email.append("<td>" + getMovimentationTypeLabel(accountMovimentationDTO.getMovimentationType()) + "</td>");
			saldo += accountMovimentationDTO.getAmount();
			email.append("<td>" + Math.floor(saldo * 100 ) / 100 + "</td>");
			email.append("</tr>");
		}

		email.append("</tbody></body></html>");

		message.setText(email.toString(), "utf-8", "html");

		AccountDTO account = accountDAO.findById(idAccount);
		message.addRecipient(RecipientType.TO, new InternetAddress(account.getEmailNotification()));

		// javaMailSender.send(message); // Uncomment this line to send email

		return email.toString();
	}

	@Override
	public List<AccountMovimentationDTO> byCompanyStock(Long companyStockId) {
		return accountMovimentationDAO.byCompanyStock(companyStockId);
	}

}
