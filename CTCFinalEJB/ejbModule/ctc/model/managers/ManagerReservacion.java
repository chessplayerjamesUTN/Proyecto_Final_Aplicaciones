package ctc.model.managers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ctc.model.entities.Cliente;
import ctc.model.entities.Funcion;
import ctc.model.entities.Reservacion;
import ctc.model.entities.Ticket;

@Stateless
@LocalBean
public class ManagerReservacion {
	@PersistenceContext
	private EntityManager em;

	public ManagerReservacion() {
		// TODO Auto-generated constructor stub
	}

	public List<Reservacion> findAllReservaciones() {

		String consulta = "select o from Reservacion o order by o.reservacionId";
		System.out.print("llegueaqui");
		Query q = em.createQuery(consulta, Reservacion.class);
		return q.getResultList();
	
	}
	public List<Funcion> findAllFunciones() {
		String consulta = "select f from Funcion f order by f.funcionId";
		Query q = em.createQuery(consulta, Funcion.class);
		List<Funcion> funciones = q.getResultList();
		for (int i = 0; i < funciones.size(); i++)
		{
			Funcion funcion = funciones.get(i);
			Date d1 = funcion.getFecha();
			Date d2 = new Date();
			Calendar c1 = new GregorianCalendar();
			c1.setTime(d1);
			Calendar c2 = new GregorianCalendar();
			c2.setTime(d2);
			if (c1.get(Calendar.DAY_OF_YEAR) < c2.get(Calendar.DAY_OF_YEAR)) funciones.remove(i--);
			else if (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
			{
				int h1 = funcion.getHora();
				int h2 = c2.get(Calendar.HOUR_OF_DAY);
				if (h1 <= h2) funciones.remove(i--);
			}
			else if (MaxAsientos(funcion) <= 0) funciones.remove(i--);
		}
		return funciones;
	}
	
	public void insertReservacion(Reservacion r, Funcion f, int asientos)
	{
		r.setCajeroId(null);
		em.persist(r);
		return;
//		System.out.println("hola2");
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		insertTickets(asientos, f, r);
	}
	
	public Cliente findClienteByCedula(String cedula)
	{
		return em.createQuery("SELECT c FROM Cliente c"
				+ " WHERE c.clienteId='" + cedula + "'", Cliente.class).getResultList().get(0);
	}
	
	public int MaxAsientos(Funcion f)
	{
		String consulta = "select count(*) from Ticket t where t.funcion.funcionId=" + f.getFuncionId() + "";
		Query q = em.createQuery(consulta);
		return 20 - Integer.parseInt(q.getSingleResult().toString());
	}
	
	public Reservacion insertTickets(int asientos, Funcion f, Reservacion r)
	{
		String consulta = "select max(r.reservacionId) from Reservacion r";
		Query q = em.createQuery(consulta);
		int id = Integer.parseInt(q.getSingleResult().toString());
		r.setReservacionId(id);
		for (int i = 0; i < asientos; i++)
		{
			Ticket t = new Ticket();
			t.setCosto(3);
			t.setFuncion(f);
			t.setReservacion(r);
			em.persist(t);
		}
		return r;
	}
	
	public List<Ticket> findTicketsByReservacion(Reservacion r)
	{
//		return em.createQuery("SELECT t FROM Ticket t"
//				+ " WHERE t.clienteId='" + cedula + "'", Cliente.class).getResultList().get(0);
		String consulta = "select t from Ticket t INNER JOIN Reservacion r ON t.reservacion.reservacionId=r.reservacionId WHERE r.reservacionId=" + r.getReservacionId();
		Query q = em.createQuery(consulta, Ticket.class);
		return q.getResultList();
	}
}
