package ctc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ctc.logic.Validations;
import ctc.model.entities.Cajero;
import ctc.model.entities.Cliente;
import ctc.model.managers.ManagerLogin;
import ctc.pepper.Pepper;

@Named
@Stateless
public class BeanLogin implements Serializable {

	@EJB
	private ManagerLogin mLogin;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cedula;
	private String password;
	private String salt;
	private boolean exitoso;
	private Timestamp fechahora;
	private String tipousuario;
	private boolean verificado;
	private Cajero cajero;
	private Cliente cliente;

	/**
     * Default constructor. 
     */
    public BeanLogin() {
        // TODO Auto-generated constructor stub
    }
    
    @PostConstruct
	public void inicializar() {
		//mLogin = new ManagerLogin();
	}
    
    public static String bytesToHex(byte[] hash)
    {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++)
	    {
		    String hex = Integer.toHexString(0xff & hash[i]);
		    if(hex.length() == 1) hexString.append('0');
		    hexString.append(hex);
	    }
	    return hexString.toString();
	}
    
    public String actionClienteLogin()
    {
    	
    	int codigo = mLogin.findPersona(cedula);
    	System.out.println("got here 1");
    	if (codigo == 1)
    	{
    		cliente = mLogin.findClienteByCedula(cedula);
    		tipousuario = "c";
    	}
    	else if (codigo == 2)
    	{
    		cajero = mLogin.findCajeroByCedula(cedula);
    		tipousuario = "a";
    	}
    	else
    	{
    		cliente = new Cliente();
    		cliente.setClienteId(cedula);
    		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/CTCFinalWeb/faces/crearCliente.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "clientemenu";
    	}
    	if (codigo == 1) this.salt = cliente.getSalt();
    	else this.salt = cajero.getSalt();
    		try
    		{
    			MessageDigest digest = MessageDigest.getInstance("SHA-256");
    			String superhashedpassword = password + salt + Pepper.PEPPERVALUE;
    			byte[] encodedhash = digest.digest(superhashedpassword.getBytes(StandardCharsets.UTF_8));
    			String hash = bytesToHex(encodedhash);
    			if (codigo == 1) exitoso = hash.equals(cliente.getPassword());
    			else exitoso = hash.contentEquals(cajero.getPassword());
    			//System.out.println(pass);
    			System.out.println(hash);
    			mLogin.guardarLogeo(cedula, tipousuario.toCharArray()[0], exitoso);
    			if (exitoso)
    			{
    				System.out.println("Success");
    				System.out.println(hash);
    				String direccioncliente = "/CTCFinalWeb/faces/Reservaciones.xhtml";
    				String direccioncajero = "/CTCFinalWeb/faces/CancelarReservacion.xhtml";
    				if (codigo == 1)FacesContext.getCurrentInstance().getExternalContext().redirect(direccioncliente);
    				else FacesContext.getCurrentInstance().getExternalContext().redirect(direccioncajero);
    				return "clientemenu";
    				
    			}
    			else
    			{
    				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta.",""));
    			}
    			return "";
    		}
    		catch (Exception e)
    		{
    			
    		}
    		
    	/*
    	else if (tipousuario.equals("a"))
    	{
    		
    	}*/
    	return "";
    }
    
   
    public void actionListenerNuevoCliente() throws NoSuchAlgorithmException, IOException
    {
    	if (!Validations.validadorDeCedula(cliente.getClienteId()))
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
			hash = bytesToHex(encodedhash);
    	} while (mLogin.saltExistsCliente(hash));
    	cliente.setSalt(hash);
    	String superpassword = password + hash + Pepper.PEPPERVALUE;
    	byte[] encodedhash = digest.digest(superpassword.getBytes(StandardCharsets.UTF_8));
		hash = bytesToHex(encodedhash);
		cliente.setPassword(hash);
		mLogin.createCliente(cliente);
    	FacesContext.getCurrentInstance().getExternalContext().redirect("/CTCFinalWeb/faces/Reservaciones.xhtml");
    }
    
    


    

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isExitoso() {
		return exitoso;
	}

	public void setExitoso(boolean exitoso) {
		this.exitoso = exitoso;
	}

	public Timestamp getFechahora() {
		return fechahora;
	}

	public void setFechahora(Timestamp fechahora) {
		this.fechahora = fechahora;
	}

	public String getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(String tipousuario) {
		this.tipousuario = tipousuario;
	}

	public Cajero getCajero() {
		return cajero;
	}

	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	

}
