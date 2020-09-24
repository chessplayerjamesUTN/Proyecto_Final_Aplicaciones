package ctc.controllers;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ctc.model.entities.Funcion;
import ctc.model.entities.Pelicula;
import ctc.model.managers.ManagerFuncion;
import ctc.model.managers.ManagerPelicula;

@Named
@Stateless
public class BeanFuncion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	@EJB

	private ManagerFuncion managerFuncion;
	private ManagerPelicula managerPelicula;
	private List<Funcion> listaFunciones;
	private List<Pelicula> listaPeliculas;
	private String fechahora;
	private int peliculaId;
	private int sala;

	private Funcion funcion;
	private Pelicula pelicula;

	@PostConstruct
	public void inicializar() {
		System.out.print("llegue hasta aqui");
		listaFunciones = managerFuncion.findAllFunciones();
		listaPeliculas = managerFuncion.findAllPelicula();
		funcion = new Funcion();
		// JSFUtil.crearMensajeInfo("Insertando Datos");

		pelicula = new Pelicula();
	}

	
	public List<Funcion> getListaFunciones() {

		return listaFunciones;

	}

	public void setListaFunciones(List<Funcion> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public List<Pelicula> getListaPeliculas() {
		return listaPeliculas;
	}

	public void setListaPeliculas(List<Pelicula> listaPeliculas) {
		this.listaPeliculas = listaPeliculas;
	}

}
