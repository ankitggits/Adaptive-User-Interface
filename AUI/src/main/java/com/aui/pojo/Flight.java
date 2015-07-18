package com.aui.pojo;


public class Flight{

	private String name;
	private String flightCode;
	private String source;
	private String destination;
	private String departure;
	private String arrival;
	private String duration;
	private String economyfare;
	private String businessfare;
	private FlightLogo flightLogo;

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

	public FlightLogo getFlightLogo() {
		return flightLogo;
	}

	public void setFlightLogo(FlightLogo flightLogo) {
		this.flightLogo = flightLogo;
	}
	
}
