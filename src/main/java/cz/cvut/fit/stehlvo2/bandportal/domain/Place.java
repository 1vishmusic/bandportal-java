package cz.cvut.fit.stehlvo2.bandportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Place implements EntityWithId<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long placeId;
	private String placeName;
	private String placeWebsite;

	@OneToMany(mappedBy = "place")
	@JsonIgnore
	private Collection<Event> events;

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceWebsite() {
		return placeWebsite;
	}

	public void setPlaceWebsite(String placeWebsite) {
		this.placeWebsite = placeWebsite;
	}

	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Place place = (Place) o;
		return Objects.equals(placeId, place.placeId);
	}

	@Override
	public String toString() {
		return "Place{" +
			"placeId=" + placeId +
			", placeName='" + placeName + '\'' +
			", placeWebsite='" + placeWebsite + '\'' +
			'}';
	}
}
