package com.aui.service;

import java.util.List;

import com.aui.pojo.BookedTicket;
import com.aui.pojo.Flight;
import com.aui.pojo.FlightLogo;
import com.aui.pojo.ResponseData;
import com.aui.pojo.SearchFlight;

public interface FlightService {

	ResponseData searchFlights(SearchFlight searchflight);
	
	ResponseData saveFlights(List<Flight> flights);

	ResponseData saveFlightLogo(List<FlightLogo> logo);
	
	ResponseData bookFlight(BookedTicket bookedTicket);

	ResponseData getCities(String userName);
}
