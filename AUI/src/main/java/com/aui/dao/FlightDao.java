package com.aui.dao;

import java.util.List;

import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.pojo.SearchFlight;

public interface FlightDao extends GenericDao<TBLFlight>{

	List<TBLFlight> searchFlights(SearchFlight searchFlight);
	
	void populateFlights(TBLFlight flight);

	void populateFlightLogo(TBLFlightLogo tblFlightLogo);
	
}
