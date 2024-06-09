package cz.cvut.fit.stehlvo2.bandportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Band implements EntityWithId<Long> {
	@Id
	@GeneratedValue
	private Long bandId;

	private String bandName;
	private String bandWebsite;

	@ManyToMany(mappedBy = "bands") @JsonIgnore
	private Collection<Event> events = new ArrayList<>();

	public Long getBandId() {
		return bandId;
	}

	public void setBandId(Long bandId) {
		this.bandId = bandId;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public String getBandWebsite() {
		return bandWebsite;
	}

	public void setBandWebsite(String bandWebsite) {
		this.bandWebsite = bandWebsite;
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
		Band band = (Band) o;
		return Objects.equals(bandId, band.bandId);
	}

	@Override
	public String toString() {
		return "Band{" +
			"bandId=" + bandId +
			", bandName='" + bandName + '\'' +
			", bandWebsite='" + bandWebsite + '\'' +
			'}';
	}
}
