package ctc.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the reservacion database table.
 * 
 */
@Entity
@Table(name="reservacion")
@NamedQuery(name="Reservacion.findAll", query="SELECT r FROM Reservacion r")
public class Reservacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservacion_id", unique=true, nullable=false)
	private Integer reservacionId;

	@Column(nullable=false)
	private Boolean cancelado;

	@Column(name="total_costo", nullable=false, precision=5, scale=2)
	private BigDecimal totalCosto;

	@Column(name="total_tickets", nullable=false)
	private Integer totalTickets;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="cajero_id")
	private Cajero cajeroId;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="reservacion", cascade = CascadeType.ALL)
	private List<Ticket> tickets;

	public Reservacion() {
		
	}

	public Integer getReservacionId() {
		return this.reservacionId;
	}

	public void setReservacionId(Integer reservacionId) {
		this.reservacionId = reservacionId;
	}

	
	
	public Cajero getCajeroId() {
		return cajeroId;
	}

	public void setCajeroId(Cajero cajeroId) {
		this.cajeroId = cajeroId;
	}

	public void setTotalCosto(BigDecimal totalCosto) {
		this.totalCosto = totalCosto;
	}

	public Boolean getCancelado() {
		return this.cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public BigDecimal getTotalCosto() {
		return this.totalCosto;
	}

	public void setTotalCosto(String totalCosto) {
		this.totalCosto = new BigDecimal(totalCosto);
	}

	public Integer getTotalTickets() {
		return this.totalTickets;
	}

	public void setTotalTickets(Integer totalTickets) {
		this.totalTickets = totalTickets;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setReservacion(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setReservacion(null);

		return ticket;
	}

}