package com.aui.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.BookedTicket;
import com.aui.pojo.Flight;
import com.aui.pojo.FlightLogo;
import com.aui.pojo.ResponseData;
import com.aui.pojo.SearchFlight;
import com.aui.service.AuthenticationService;
import com.aui.service.FlightService;

@Controller
@RequestMapping(value="/flight")
public class FlightController {

	@Autowired
	FlightService flightService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/populate", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData saveFlights(@RequestBody List<Flight> flights){
		return flightService.saveFlights(flights);
	}
	
	@RequestMapping(value="/populate/logo", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData saveFlightLogo(@RequestBody List<FlightLogo> logo){
		return flightService.saveFlightLogo(logo);
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData searchFlights(@RequestBody SearchFlight searchflight){
		return flightService.searchFlights(searchflight);
	}
	
	@RequestMapping(value="/bookFlight", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData bookFlight(@RequestBody BookedTicket bookedTicket){
		String userName=authenticationService.getAuthenticatedUserName();
		bookedTicket.setUserName(userName);
		return flightService.bookFlight(bookedTicket);
	}
	
	@RequestMapping(value="/getCities", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getCities(){
		String userName=authenticationService.getAuthenticatedUserName();
		return flightService.getCities(userName);
	}
	
	
	@RequestMapping(value="/getFrequentCities", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getFrequentCities(){
		String userName=authenticationService.getAuthenticatedUserName();
		return flightService.getFrequentCities(userName);
	}
}
