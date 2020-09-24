package ctc.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the funcion database table.
 * 
 */
@Entity
@Table(name="funcion")
@NamedQuery(name="Funcion.findAll", query="SELECT f FROM Funcion f")
public class Funcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="funcion_id", unique=true, nullable=false)
	private Integer funcionId;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Integer hora;

	@Column(nullable=false)
	private Integer sala;

	//bi-directional many-to-one association to Pelicula
	@ManyToOne
	@JoinColumn(name="pelicula_id", nullable=false)
	private Pelicula pelicula;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="funcion")
	private List<Ticket> tickets;

	public Funcion() {
	}

	public Integer getFuncionId() {
		return this.funcionId;
	}

	public void setFuncionId(Integer funcionId) {
		this.funcionId = funcionId;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getHora() {
		return this.hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public Integer getSala() {
		return this.sala;
	}

	public void setSala(Integer sala) {
		this.sala = sala;
	}

	public Pelicula getPelicula() {
		return this.pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setFuncion(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setFuncion(null);

		return ticket;
	}

}