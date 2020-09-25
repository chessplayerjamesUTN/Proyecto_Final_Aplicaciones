package ctc.model.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ctc.model.entities.Cajero;
import ctc.model.entities.Cliente;
import ctc.model.entities.Funcion;
import ctc.model.entities.Pelicula;
import ctc.model.entities.Reservacion;

@Stateless
@LocalBean

public class ManagerCajero {
	
@PersistenceContext
	private EntityManager em;

	public ManagerCajero() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<Reservacion> findAllReservaciones() {
		System.out.print("llegueaqui nomas");

		String consulta = "select o from Reservacion o order by o.reservacionId";
		System.out.print("llegueaqui");
		Query q = em.createQuery(consulta, Reservacion.class);
		return q.getResultList();
	
	}
	
	
	public Reservacion findReservacionByCliente(int reservacionId) {
	System.out.println("llego al manager ");
		return em.find(Reservacion.class, reservacionId);
	
	}
	
	public Cajero findCajerobyId(int id) {
		System.out.println("llego al id de cajero ");
			return em.find(Cajero.class, id);
		
		}
		
	public void actualizarReservacion(Reservacion reservacion, Cajero c) throws Exception {
		Reservacion r = findReservacionByCliente(reservacion.getReservacionId());
		if (r == null) {
			throw new Exception("No existe pelicula con este Id");
		}
		r.setCancelado(true);
		r.setCajeroId(c);	
		em.merge(r);
	}
	
	public boolean saltExistsCliente(String hash)
	{
		String consulta = "select count(*) from Cajero c where c.salt='" + hash + "'";
		Query q = em.createQuery(consulta);
		return (Integer.parseInt(q.getSingleResult().toString()) == 1);
	}
	
	public void CreateCajero(Cajero c)
	{
		em.persist(c);
		return;
	}
	
}
