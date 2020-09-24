package ctc.model.managers;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ctc.model.entities.Funcion;
import ctc.model.entities.Pelicula;


@Stateless
@LocalBean

public class ManagerFuncion {

	
		@PersistenceContext
		private EntityManager em;

		public ManagerFuncion() {
		}
		 
		

		public List<Funcion> findAllFunciones() {
			String consulta = "select f from Funcion f order by f.funcionId";
			Query q = em.createQuery(consulta, Funcion.class);
			return q.getResultList();
		}
		public List<Pelicula> findAllPelicula() {
			String consulta = "select o from Pelicula o order by o.peliculaNombre";
			Query q = em.createQuery(consulta, Pelicula.class);
			return q.getResultList();
		}

		
		

		public Funcion findFuncionbyFecha(String fecha) {
			return em.find(Funcion.class, fecha);
		}
		
	
		
		
		public void agregarFuncion(Pelicula pelicula){
			em.persist(pelicula);
	    }
		
		public void agregarFuncion2(){
		    System.out.print("Esta funcionando");
		    }
			
		
		
		 public Pelicula findPeliculabyId(int peliculaId)
		    {
		    	return em.find(Pelicula.class, peliculaId);
		    }
		    
	//	public Funcion agregarDetalle(String fechahora, int peliculaId, int sala)
	 //   {
	   // 	Pelicula p = findPeliculabyId(peliculaId);
	   // 	Funcion funcion = new Funcion();
	    //	funcion.setFechahora(fechahora);;
	    //	funcion.setPelicula(p);
	    //	funcion.setSala(sala);
	    //	return funcion;
	  //  }
		
		
		
		
}