package ctc.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the ticket database table.
 * 
 */
@Entity
@Table(name="ticket")
@NamedQuery(name="Ticket.findAll", query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ticket_id", unique=true, nullable=false)
	private Integer ticketId;

	@Column(nullable=false)
	private int costo;

	//bi-directional many-to-one association to Funcion
	@ManyToOne
	@JoinColumn(name="funcion_id", nullable=false)
	private Funcion funcion;

	//bi-directional many-to-one association to Reservacion
	@ManyToOne
	@JoinColumn(name="reservacion_id", nullable=false)
	private Reservacion reservacion;

	public Ticket() {
	}

	public Integer getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public int getCosto() {
		return this.costo;
	}

	public void setCosto(int costo) {
		this.costo = (costo);
	}

	public Funcion getFuncion() {
		return this.funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Reservacion getReservacion() {
		return this.reservacion;
	}

	public void setReservacion(Reservacion reservacion) {
		this.reservacion = reservacion;
	}

}