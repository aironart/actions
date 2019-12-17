package br.com.airon.actions.actionsapiclient.clients;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.airon.actions.actionsdomains.BaseDTO;

public abstract class BaseCrudClient<T extends BaseDTO<ID>, ID extends Serializable> {

	protected WebTarget baseTarget;

	private Class<T> classe;

	/**
	 * This constructor set the default serviceUrl <br>
	 * it concatenates "http://" + <code>hostName</code> + ":" + <code>port</code> +
	 * "/actions-api/rest/ + <code>prefix</code>
	 * 
	 * @param hostName
	 * @param port
	 * @param prefix
	 */
	public BaseCrudClient(String hostName, int port, String prefix, Class<T> classe) {
		String serviceUrl = "http://" + hostName + ":" + port + "/actions-api/rest/" + prefix;
		Client client = ClientBuilder.newClient();
		baseTarget = client.target(serviceUrl);
		this.classe = classe;
	}

	public T update(ID id, T item) {
		WebTarget updateTarget = baseTarget.path("/" + id);
		Invocation.Builder invocationBuilder = updateTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(item, MediaType.APPLICATION_JSON));

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro atualizando");
		}
		T updateItem = (T) response.readEntity(classe);
		return updateItem;
	}

	public T findById(ID id) {
		WebTarget getTarget = baseTarget.path("/" + id);
		Invocation.Builder invocationBuilder = getTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro buscando o id");
		}

		T byId = response.readEntity(this.getGenericType());
		return byId;
	}

	public String delete(ID id) {
		WebTarget deleteTarget = baseTarget.path("/" + id);
		Invocation.Builder deleteBuilder = deleteTarget.request(MediaType.TEXT_PLAIN);
		Response response = deleteBuilder.delete();

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro deletando por id");
		}

		String deleteResult = response.readEntity(String.class);
		return deleteResult;
	}

	public List<T> findAll() {
		Invocation.Builder invocationBuilder = baseTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro listando");
		}

//		try {
//			List<AccountDTO> readEntity = response.readEntity(new GenericType<List<AccountDTO>>() {
//			});
//			System.out.println(readEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		List<T> responseList = response.readEntity(this.getListGenericType());
		return responseList;
	}

	public T create(T newItem) {
		Invocation.Builder invocationBuilder = baseTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(newItem, MediaType.APPLICATION_JSON));

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new RuntimeException("Erro inserindo");
		}

		return response.readEntity(new GenericType<T>() {
		});

	}

	public abstract GenericType<T> getGenericType() ;

	public abstract GenericType<List<T>> getListGenericType();

}
