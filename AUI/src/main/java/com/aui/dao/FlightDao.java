package com.aui.dao;

import java.util.List;

import com.aui.model.TBLTicket;
import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.pojo.SearchFlight;

public interface FlightDao extends GenericDao<TBLFlight>{

	List<TBLFlight> searchFlights(SearchFlight searchFlight);
	
	List<TBLFlight> getFlights();
	
	void populateFlights(TBLFlight flight);

	void populateFlightLogo(TBLFlightLogo tblFlightLogo);
	
	void bookFlight(TBLTicket tblBookedTicket);
	
}
