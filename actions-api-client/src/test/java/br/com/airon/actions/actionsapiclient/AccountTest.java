package br.com.airon.actions.actionsapiclient;

import java.util.List;

import br.com.airon.actions.actionsapiclient.clients.AccountClientImpl;
import br.com.airon.actions.actionsdomains.AccountDTO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AccountTest extends TestCase {
	private String hostName = "localhost";
	private int port = 8080;

	private AccountClientImpl accountClient;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AccountTest(String testName) {
		super(testName);
		accountClient = new AccountClientImpl(hostName, port);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AccountTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testFindAll() {
		try {
			List<AccountDTO> findAll = accountClient.findAll();
			System.out.println(findAll);
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			assertTrue(false);
		}
	}
	
	public void testFindById() {
		try {
			AccountDTO findById = accountClient.findById(1l);
			System.out.println(findById);
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			assertTrue(false);
		}
		
	}
}
