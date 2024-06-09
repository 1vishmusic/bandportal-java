package cz.cvut.fit.stehlvo2.bandportal.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Event implements EntityWithId<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	private String eventName;
	private LocalDateTime eventStart;
	private LocalDateTime eventEnd;
	private String eventWebsite;

	@ManyToMany
	private Collection<Band> bands = new ArrayList<>();

	@ManyToOne @JoinColumn(name = "place_id")
	private Place place;

	@OneToOne(mappedBy = "event", cascade = CascadeType.REMOVE)
	private Ticket ticket;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDateTime getEventStart() {
		return eventStart;
	}

	public void setEventStart(LocalDateTime eventStart) {
		this.eventStart = eventStart;
	}

	public LocalDateTime getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(LocalDateTime eventEnd) {
		this.eventEnd = eventEnd;
	}

	public String getEventWebsite() {
		return eventWebsite;
	}

	public void setEventWebsite(String eventWebsite) {
		this.eventWebsite = eventWebsite;
	}

	public Collection<Band> getBands() {
		return bands;
	}

	public void setBands(Collection<Band> bands) {
		this.bands = bands;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Event event = (Event) o;
		return Objects.equals(eventId, event.eventId);
	}

	@Override
	public String toString() {
		return "Event{" +
			"eventId=" + eventId +
			", eventName='" + eventName + '\'' +
			", eventStart=" + eventStart +
			", eventEnd=" + eventEnd +
			", eventWebsite='" + eventWebsite + '\'' +
			", bands=" + bands +
			", place=" + place +
			", ticket=" + ticket +
			'}';
	}
}
