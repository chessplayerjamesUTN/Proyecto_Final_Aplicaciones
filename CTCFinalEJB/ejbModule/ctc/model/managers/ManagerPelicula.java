package ctc.model.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ctc.model.entities.Pelicula;

/**
 * Session Bean implementation class ManagerPelicula
 */
@Stateless
@LocalBean
public class ManagerPelicula {
	@PersistenceContext
	private EntityManager em;

	public ManagerPelicula() {
		// TODO Auto-generated constructor stub
	}

	public List<Pelicula> findAllPelicula() {
		String consulta = "select o from Pelicula o order by o.peliculaNombre";
		Query q = em.createQuery(consulta, Pelicula.class);
		return q.getResultList();
	}

	public void insertarPelicula(Pelicula pelicula) throws Exception {
		/*
		 * if (findPeliculaByNombre(pelicula.getPeliculaNombre()) != null) throw new
		 * Exception("Ya existe la Pelicula indicada. "); if
		 * (pelicula.getPeliculaNombre() == null ||
		 * pelicula.getPeliculaNombre().length() == 0) { throw new
		 * Exception("Debe especificar un Nombre"); }
		 */
		// visible="false"
		em.persist(pelicula);

	}

	public Pelicula findPeliculaByNombre(String nombre) {
		return em.find(Pelicula.class, nombre);
	}

	public Pelicula findPeliculaById(int id) {
		return em.find(Pelicula.class, id);
	}

	public void elminarPelicula(int peliculaId) {

		Pelicula estudiante = findPeliculaById(peliculaId);
		if (estudiante != null) {
			em.remove(estudiante);
		}

	}

	public void actualizarPelicula(Pelicula pelicula) throws Exception {
		Pelicula p = findPeliculaByNombre(pelicula.getPeliculaNombre());
		if (p == null) {
			throw new Exception("No existe pelicula con este Id");
		}
		p.setPeliculaNombre(pelicula.getPeliculaNombre());
		p.setDirector(pelicula.getDirector());
		p.setAnio(pelicula.getAnio());
		p.setSinopsis(pelicula.getSinopsis());

		em.merge(p);
	}

	

	
	

}
