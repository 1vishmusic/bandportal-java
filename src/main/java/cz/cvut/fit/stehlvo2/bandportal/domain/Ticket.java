package cz.cvut.fit.stehlvo2.bandportal.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Ticket implements EntityWithId<Long> {
	@Id
	private Long eventId;

	private String ticketPrice = "";
	private boolean atPlace = false;
	private String ticketWebsite = "";

	@OneToOne
	@JoinColumn(name = "event_id")
	private Event event;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public boolean isAtPlace() {
		return atPlace;
	}

	public void setAtPlace(boolean atPlace) {
		this.atPlace = atPlace;
	}

	public String getTicketWebsite() {
		return ticketWebsite;
	}

	public void setTicketWebsite(String ticketWebsite) {
		this.ticketWebsite = ticketWebsite;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return Objects.equals(eventId, ticket.eventId);
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"eventId=" + eventId +
				", ticketPrice='" + ticketPrice + '\'' +
				", atPlace=" + atPlace +
				", website='" + ticketWebsite + '\'' +
				", event=" + event +
				'}';
	}
}
