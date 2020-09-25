package ctc.controllers;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ctc.model.entities.Cliente;
import ctc.model.entities.Funcion;
import ctc.model.entities.Pelicula;
import ctc.model.entities.Reservacion;
import ctc.model.entities.Ticket;
import ctc.model.managers.ManagerCliente;
import ctc.model.managers.ManagerFuncion;
import ctc.model.managers.ManagerPelicula;
import ctc.model.managers.ManagerReservacion;

@Named
@SessionScoped
public class BeanReservacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	@EJB
	private ManagerReservacion managerReservacion;
	private ManagerFuncion managerFuncion;
    private List<Funcion> listaFunciones;
    private List<Reservacion> listaReservacion;
    private List<Reservacion> listaReservacionesCliente;
    private List<Ticket> listaTickets;
   
    @Inject
    private BeanLogin bl;
    
    private Funcion funcion;
    private Pelicula pelicula;
    private Reservacion reservacion;
    private Cliente cliente;
	private Reservacion reservacionSeleccionado;

    
    private int numTickets;
    private int maxAsientos;
    
    @PostConstruct
    public void inicializar(){
	 
    	listaFunciones = managerReservacion.findAllFunciones();
		listaReservacion=  managerReservacion.findAllReservaciones();
		funcion = new Funcion();
		 //  JSFUtil.crearMensajeInfo("Insertando Datos");
		listaTickets = new ArrayList();
		//pelicula = new Pelicula();
		numTickets = 1;
		reservacion= new Reservacion();
		cliente = bl.getCliente();
		System.out.println(cliente.getClienteId());
		listaReservacionesCliente = managerReservacion.findReservacionesByCedula(cliente);
		System.out.println(listaReservacionesCliente.size());
		System.out.println(listaReservacionesCliente.get(0).getTotalTickets());
	}
    
    
    public void actionListenerInsertarFuncion(){
  	  
  		   JSFUtil.crearMensajeInfo("Insertando Datos");

  	  
  	}
    public void actionListenerSeleccionarReservacion(Reservacion reservacion) {
		reservacionSeleccionado = reservacion;
	}
    
  
    
    public List<Reservacion> getListaReservacion() {
		return listaReservacion;
	}


	public void setListaReservacion(List<Reservacion> listaReservacion) {
		this.listaReservacion = listaReservacion;
	}


	public List<Reservacion> getListaReservacionesCliente() {
		return listaReservacionesCliente;
	}


	public void setListaReservacionesCliente(List<Reservacion> listaReservacionesCliente) {
		this.listaReservacionesCliente = listaReservacionesCliente;
	}


	public List<Ticket> getListaTickets() {
		return listaTickets;
	}


	public void setListaTickets(List<Ticket> listaTickets) {
		this.listaTickets = listaTickets;
	}


	public Reservacion getReservacion() {
		return reservacion;
	}


	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public int getNumTickets() {
		return numTickets;
	}


	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}


	public void actionListenerAgregar() {
		reservacion = new Reservacion();
		cliente = bl.getCliente();
		reservacion.setCliente(cliente);
		reservacion.setTotalCosto(Integer.toString((numTickets * 3)));
		reservacion.setTotalTickets((numTickets));
		reservacion.setCancelado(false);
		managerReservacion.insertReservacion(reservacion, funcion, numTickets);
		managerReservacion.insertTickets(numTickets, funcion, reservacion);
		listaTickets = managerReservacion.findTicketsByReservacion(reservacion);
		listaFunciones = managerReservacion.findAllFunciones();
		listaReservacionesCliente = managerReservacion.findReservacionesByCedula(cliente);
    }
	
	public void actionListenerCargarFuncion(Funcion f)
	{
		//this.funcion = new Funcion();
	    this.funcion=f;
	    this.maxAsientos = Math.min(managerReservacion.MaxAsientos(funcion), 10); 
	}
    
    

	public int getMaxAsientos() {
		return maxAsientos;
	}


	public void setMaxAsientos(int maxAsientos) {
		this.maxAsientos = maxAsientos;
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


	public Reservacion getReservacionSeleccionado() {
		return reservacionSeleccionado;
	}


	public void setReservacionSeleccionado(Reservacion reservacionSeleccionado) {
		this.reservacionSeleccionado = reservacionSeleccionado;
	}

	
    
   

	
    
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

