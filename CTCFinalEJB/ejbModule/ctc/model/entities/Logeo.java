package ctc.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the logeo database table.
 * 
 */
@Entity
@Table(name="logeo")
@NamedQuery(name="Logeo.findAll", query="SELECT l FROM Logeo l")
public class Logeo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Boolean exitoso;

	@Column(nullable=false)
	private Timestamp fechahora;

	@Column(name="id_usuario", nullable=false, length=10)
	private String idUsuario;

	@Column(name="tipo_usuario", nullable=false, length=1)
	private String tipoUsuario;

	public Logeo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getExitoso() {
		return this.exitoso;
	}

	public void setExitoso(Boolean exitoso) {
		this.exitoso = exitoso;
	}

	public Timestamp getFechahora() {
		return this.fechahora;
	}

	public void setFechahora(Timestamp fechahora) {
		this.fechahora = fechahora;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}