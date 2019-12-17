package br.com.airon.actions.actionsapi.rest;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.airon.actions.actionsapi.services.CrudService;
import br.com.airon.actions.actionsdomains.BaseDTO;

public abstract class BaseCrudRest<T extends BaseDTO<ID>, ID extends Serializable> {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected CrudService<T, ID> service;
	
	protected String getServiceName () {
		return this.getClass().getName().toLowerCase();
	}

	public BaseCrudRest(CrudService<T, ID> service) {
		this.service = service;
	}


	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public T update(@PathParam("id") ID id, T item) {
		try {
			item.setId(id);

			item = service.update(item);
			return item;
		} catch (Exception e) {
			if (e instanceof NotFoundException) {
				throw new WebApplicationException(
						Response.status(404).entity("Não encontrei registro com o id informado -> " + id).build());
			} else {
				log.error(e.getMessage(), e);
				throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build());
			}
		}
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public T get(@PathParam("id") ID id) {
		try {
			T itemFound = service.findById(id);
			return itemFound;
		} catch (Exception e) {
			if(e instanceof NotFoundException) {
				throw new WebApplicationException(Response.status(404).entity("Não encontrei registro com o id informado -> " + id).build() );
			}else {
				log.error(e.getMessage(),e);
				throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build() );
			}
		}
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{id}")
	public String delete(@PathParam("id") ID id) {
		try {
			service.deleteById(id);
			return "OK";
		} catch (Exception e) {
			if(e instanceof NotFoundException) {
				throw new WebApplicationException(Response.status(404).entity("Não encontrei registro com o id informado -> " + id).build() );
			}else {
				log.error(e.getMessage(),e);
				throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build() );
			}
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<T> list() {
		List<T> resultList = this.service.findAll();
		return resultList;
	}
	

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public T create(T newItem) {
		if(newItem == null) {
//			I hope never come here
			throw new WebApplicationException(Response.status(Response.Status.NO_CONTENT).entity("Conteudo vazio").build() );
		}
		
		try {
			newItem = service.insert(newItem);
		}catch( Exception e) {
			throw new WebApplicationException(Response.status(400).entity(e.getMessage()).build() );
		}
		
		
		return newItem;
	}

}
