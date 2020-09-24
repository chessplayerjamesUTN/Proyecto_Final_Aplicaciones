package ctc.model.managers;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ctc.model.entities.Cajero;
import ctc.model.entities.Cliente;
import ctc.model.entities.Logeo;

@Stateless
@LocalBean
public class ManagerLogin {
	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public ManagerLogin() {
		// TODO Auto-generated constructor stub
	}
	
	public Cajero findCajeroByCedula(String cedula)
	{
		return em.createQuery("SELECT c FROM Cajero c"
				+ " WHERE c.cedula='" + cedula + "'", Cajero.class).getResultList().get(0);
	}
	
	public Cliente findClienteByCedula(String cedula)
	{
		return em.createQuery("SELECT c FROM Cliente c"
				+ " WHERE c.clienteId='" + cedula + "'", Cliente.class).getResultList().get(0);
	}
	
	public List<Logeo> findLogeosFallidos(String id_usuario, char tipo_usuario)
	{
		return em.createQuery("SELECT * FROM logeo L"
				+ "WHERE L.id_usuario='" + id_usuario +
				"' AND L.tipo_usuario='" + tipo_usuario +
				"' AND L.id > (SELECT Lo.id FROM Logeo Lo"
				+ "WHERE Lo.id_usuario='" + id_usuario + "'"
						+ "AND Lo.tipo_usuario='" + tipo_usuario +
						"' DESC LIMIT 1", Logeo.class).getResultList();
		
	}
	
	public void guardarLogeo(String id_usuario, char tipo_usuario, boolean exitoso)
	{
		System.out.println("hola mundo que onda");
		Logeo l = new Logeo();
		l.setExitoso(exitoso);
		l.setFechahora(new Timestamp(System.currentTimeMillis()));
		l.setIdUsuario(id_usuario);
		l.setTipoUsuario(Character.toString(tipo_usuario));
		em.persist(l);
	}
	
	
	

}
