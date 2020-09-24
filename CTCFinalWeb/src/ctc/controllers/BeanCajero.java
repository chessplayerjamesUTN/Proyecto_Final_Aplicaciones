package ctc.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ctc.model.entities.Cajero;
import ctc.model.entities.Cliente;
import ctc.model.entities.Funcion;
import ctc.model.entities.Reservacion;
import ctc.model.managers.ManagerCajero;
import ctc.model.managers.ManagerCliente;

@Named
@SessionScoped
public class BeanCajero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@EJB
	private ManagerCajero managerCajero;

    private List<Reservacion> listaReservacion;
    private Reservacion reservacion;
	private Reservacion reservacionSeleccionado;
    private Cajero cajero; 
	
	@PostConstruct
    public void inicializar(){
	 
		listaReservacion=  managerCajero.findAllReservaciones();
		
		reservacion= new Reservacion();
		cajero=new Cajero();
	}
	
	
	 public void actionListenerSeleccionarReservacion(Reservacion reservacion) {
			reservacionSeleccionado = reservacion;
		}
	
	
	 public void actionListenerActualizarReservacion(int id) {
		System.out.println("holis!");
		 try {
				System.out.println("hola llegue al bean");
				
				Reservacion r = new Reservacion();
				Cajero c = new Cajero();
				System.out.println("hola llegue a la penultima linea");
                                                             
				r = managerCajero.findReservacionByCliente(id);
				c= managerCajero.findCajerobyId(1);
				
				managerCajero.actualizarReservacion(r);
				System.out.println("Este es el cajero actual");

				listaReservacion = managerCajero.findAllReservaciones();
				JSFUtil.crearMensajeInfo("Datos Actualizados");
				
			} catch (Exception e) {
				// TODO: handle exception
				JSFUtil.crearMensajeError(e.getMessage());
				e.printStackTrace();
			}
		}
	
	
	public List<Reservacion> getListaReservacion() {
		return listaReservacion;
	}
	public void setListaReservacion(List<Reservacion> listaReservacion) {
		this.listaReservacion = listaReservacion;
	}
	public Reservacion getReservacion() {
		return reservacion;
	}
	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}
	
	
	
    

}