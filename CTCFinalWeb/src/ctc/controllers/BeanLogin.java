package ctc.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
    
    private static String bytesToHex(byte[] hash)
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
    	tipousuario = "c";
    	System.out.println("hola");
    	System.out.println("got here");
    	System.out.println(cedula);
    	//System.out.println(cedula.length());
    	cliente = mLogin.findClienteByCedula(cedula);
    	System.out.println(cliente.getNombres());
    	//System.out.println(cliente.getSalt());
    	this.salt = cliente.getSalt();
    		try
    		{
    			System.out.println("got here");
    			MessageDigest digest = MessageDigest.getInstance("SHA-256");
    			//String salt = "937e8d5fbb48bd4949536cd65b8d35c426b80d2f830c5c308e2cdec422ae2244";
    			String superhashedpassword = password + salt + Pepper.PEPPERVALUE;
    			byte[] encodedhash = digest.digest(superhashedpassword.getBytes(StandardCharsets.UTF_8));
    			String hash = bytesToHex(encodedhash);
    			//String pass = "6a3280d0390e3727717a45069668e7654dfd508216a300d2c15727538de5b1e6";

    			
    			exitoso = hash.equals(cliente.getPassword());
    			//System.out.println(pass);
    			System.out.println(hash);
    			mLogin.guardarLogeo(cedula, 'c', exitoso);
    			if (exitoso)
    			{
    				System.out.println("Success");
    				System.out.println(hash);
    				FacesContext.getCurrentInstance().getExternalContext().redirect("/CTCFinalWeb/faces/menuCliente.xhtml");

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
