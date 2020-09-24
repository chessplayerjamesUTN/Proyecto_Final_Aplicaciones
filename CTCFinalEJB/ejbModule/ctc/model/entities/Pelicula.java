package ctc.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pelicula database table.
 * 
 */
@Entity
@Table(name="pelicula")
@NamedQuery(name="Pelicula.findAll", query="SELECT p FROM Pelicula p")
public class Pelicula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pelicula_id", unique=true, nullable=false)
	private Integer peliculaId;

	@Temporal(TemporalType.DATE)
	private Date anio;

	@Column(length=30)
	private String director;

	@Column(name="pelicula_nombre", length=50)
	private String peliculaNombre;

	@Column(length=2147483647)
	private String sinopsis;

	//bi-directional many-to-one association to Funcion
	@OneToMany(mappedBy="pelicula")
	private List<Funcion> funcions;

	public Pelicula() {
	}

	public Integer getPeliculaId() {
		return this.peliculaId;
	}

	public void setPeliculaId(Integer peliculaId) {
		this.peliculaId = peliculaId;
	}

	public Date getAnio() {
		return this.anio;
	}

	public void setAnio(Date anio) {
		this.anio = anio;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getPeliculaNombre() {
		return this.peliculaNombre;
	}

	public void setPeliculaNombre(String peliculaNombre) {
		this.peliculaNombre = peliculaNombre;
	}

	public String getSinopsis() {
		return this.sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public List<Funcion> getFuncions() {
		return this.funcions;
	}

	public void setFuncions(List<Funcion> funcions) {
		this.funcions = funcions;
	}

	public Funcion addFuncion(Funcion funcion) {
		getFuncions().add(funcion);
		funcion.setPelicula(this);

		return funcion;
	}

	public Funcion removeFuncion(Funcion funcion) {
		getFuncions().remove(funcion);
		funcion.setPelicula(null);

		return funcion;
	}

}