package ctc.model.managers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ctc.model.entities.Cliente;


@Stateless
@LocalBean
public class ManagerCliente {
	@PersistenceContext
	private EntityManager em;
	
	public Cliente findClienteById(int id)
    {
		/*
		 * String consulta = "elect c from Cliente c WHERE c.clienteId='" + id + "'";
		 * System.out.println("Wildfly sucks!"); Query q = em.createNamedQuery(consulta,
		 * Cliente.class); System.out.println("hola"); return
		 * (Cliente)q.getResultList().get(0);
		 */
		return em.find(Cliente.class, id);
    }
	
}
