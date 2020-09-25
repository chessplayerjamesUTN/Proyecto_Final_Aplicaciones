package ctc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ctc.logic.Validations;
import ctc.model.entities.Cajero;
import ctc.model.entities.Cliente;
import ctc.model.entities.Funcion;
import ctc.model.entities.Reservacion;
import ctc.model.managers.ManagerCajero;
import ctc.model.managers.ManagerCliente;
import ctc.pepper.Pepper;

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

	@Inject
	private BeanLogin bl;
	
    private List<Reservacion> listaReservacion;
    private Reservacion reservacion;
	private Reservacion reservacionSeleccionado;
    private Cajero cajero;
    private Cajero cajeronuevo;
	
	@PostConstruct
    public void inicializar(){
	 
		listaReservacion=  managerCajero.findAllReservaciones();
		cajeronuevo = new Cajero();
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
				System.out.println("hola llegue a la penultima linea");
                                                             
				r = managerCajero.findReservacionByCliente(id);
				cajero = bl.getCajero();
				
				managerCajero.actualizarReservacion(r, cajero);
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


	public Reservacion getReservacionSeleccionado() {
		return reservacionSeleccionado;
	}


	public void setReservacionSeleccionado(Reservacion reservacionSeleccionado) {
		this.reservacionSeleccionado = reservacionSeleccionado;
	}


	public Cajero getCajero() {
		return cajero;
	}


	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}


	public Cajero getCajeronuevo() {
		return cajeronuevo;
	}


	public void setCajeronuevo(Cajero cajeronuevo) {
		this.cajeronuevo = cajeronuevo;
	}
	
	
	public void actionListenerNuevoCajero() throws IOException, NoSuchAlgorithmException
	{
		if (!Validations.validadorDeCedula(cajeronuevo.getCedula()))
		{
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cedula incorrecta, digite de nuevo.",""));
			return;
		}
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	String hash = "";
    	do
    	{
	    	String saltvalue = Double.toString((Math.random() * 999999999));
	    	byte[] encodedhash = digest.digest(saltvalue.getBytes(StandardCharsets.UTF_8));
			hash = BeanLogin.bytesToHex(encodedhash);
    	} while (managerCajero.saltExistsCliente(hash));
    	cajeronuevo.setSalt(hash);
    	String superpassword = cajeronuevo.getPassword() + hash + Pepper.PEPPERVALUE;
    	byte[] encodedhash = digest.digest(superpassword.getBytes(StandardCharsets.UTF_8));
		hash = BeanLogin.bytesToHex(encodedhash);
		cajeronuevo.setPassword(hash);
		managerCajero.CreateCajero(cajeronuevo);
    	FacesContext.getCurrentInstance().getExternalContext().redirect("/CTCFinalWeb/faces/CancelarReservacion.xhtml");
	}

	public String actionCerrarSession() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
	}
    

}