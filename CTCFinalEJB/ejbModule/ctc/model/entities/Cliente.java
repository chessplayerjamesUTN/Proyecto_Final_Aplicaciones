package ctc.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cliente_id", unique=true, nullable=false, length=50)
	private String clienteId;

	@Column(nullable=false, length=50)
	private String apellidos;

	@Column(nullable=false, length=50)
	private String nombres;

	@Column(nullable=false, length=64)
	private String password;

	@Column(nullable=false, length=64)
	private String salt;

	//bi-directional many-to-one association to Reservacion
	@OneToMany(mappedBy="cliente")
	private List<Reservacion> reservacions;

	public Cliente() {
	}

	public String getClienteId() {
		return this.clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Reservacion> getReservacions() {
		return this.reservacions;
	}

	public void setReservacions(List<Reservacion> reservacions) {
		this.reservacions = reservacions;
	}

	public Reservacion addReservacion(Reservacion reservacion) {
		getReservacions().add(reservacion);
		reservacion.setCliente(this);

		return reservacion;
	}

	public Reservacion removeReservacion(Reservacion reservacion) {
		getReservacions().remove(reservacion);
		reservacion.setCliente(null);

		return reservacion;
	}

}