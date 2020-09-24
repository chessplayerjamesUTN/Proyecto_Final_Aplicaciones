package ctc.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ctc.model.managers.ManagerPelicula;
import ctc.model.entities.Pelicula;

@Named
@Stateless
public class BeanPelicula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerPelicula mPelicula;

	private List<Pelicula> listaPeliculas;
	private Pelicula pelicula;
	private boolean panelColapsado;
	private Pelicula peliculaSeleccionado;

	@PostConstruct
	public void inicializar() {
		listaPeliculas = mPelicula.findAllPelicula();
		pelicula = new Pelicula();
		panelColapsado = true;
	}

	public void actionListenerColapsarPanel() {
		panelColapsado = !panelColapsado;
	}

	public void actionListenerInsertarPelicula() {
		try {
			mPelicula.insertarPelicula(pelicula);
			listaPeliculas = mPelicula.findAllPelicula();
			pelicula = new Pelicula();
			JSFUtil.crearMensajeInfo("Datos de pelicula insertados.");

		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionListenerEliminarPelicula(int peliculaId) {
		mPelicula.elminarPelicula(peliculaId);
		listaPeliculas = mPelicula.findAllPelicula();
		JSFUtil.crearMensajeInfo("Pelicula eliminada");
	}

	public void actionListenerSeleccionarPelicula(Pelicula pelicula) {
		peliculaSeleccionado = pelicula;
	}

	public void actionListenerActualizarPelicula() {
		try {
			mPelicula.actualizarPelicula(peliculaSeleccionado);
			listaPeliculas = mPelicula.findAllPelicula();
			JSFUtil.crearMensajeInfo("Datos Actualizados");
		} catch (Exception e) {
			// TODO: handle exception
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Pelicula> getListaPeliculas() {
		return listaPeliculas;
	}

	public void setListaPeliculas(List<Pelicula> listaPeliculas) {
		this.listaPeliculas = listaPeliculas;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public boolean isPanelColapsado() {
		return panelColapsado;
	}

	public void setPanelColapsado(boolean panelColapsado) {
		this.panelColapsado = panelColapsado;
	}

	public Pelicula getPeliculaSeleccionado() {
		return peliculaSeleccionado;
	}

	public void setPeliculaSeleccionado(Pelicula peliculaSeleccionado) {
		this.peliculaSeleccionado = peliculaSeleccionado;
	}

}
