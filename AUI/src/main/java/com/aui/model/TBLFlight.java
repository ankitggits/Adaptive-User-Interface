package com.aui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FLIGHT")
public class TBLFlight extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="NAME")
	private String name;
	@Column(name="FLIGHT_CODE")
	private String flightCode;
	@Column(name="SOURCE")
	private String source;
	@Column(name="DESTINATION")
	private String destination;
	@Column(name="DEPARTURE")
	private String departure;
	@Column(name="ARRIVAL")
	private String arrival;
	@Column(name="DURATION")
	private String duration;
	@Column(name="ECONOMY_FARE")
	private String economyfare;
	@Column(name="BUSINESS_FARE")
	private String businessfare;
	@Column(name="SEAT_LEFT")
	private String seatLeft;

	public String getEconomyfare() {
		return economyfare;
	}

	public void setEconomyfare(String economyfare) {
		this.economyfare = economyfare;
	}

	public String getBusinessfare() {
		return businessfare;
	}

	public void setBusinessfare(String businessfare) {
		this.businessfare = businessfare;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	
}
