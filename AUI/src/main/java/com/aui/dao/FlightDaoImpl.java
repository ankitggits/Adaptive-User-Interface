package com.aui.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.pojo.SearchFlight;

@Transactional(propagation = Propagation.MANDATORY)
public class FlightDaoImpl extends GenericDaoImpl<TBLFlight> implements FlightDao{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.MANDATORY,readOnly = true)
	public List<TBLFlight> searchFlights(SearchFlight searchFlight) {
		Query query = getSession().createQuery("from TBLFlight flt where flt.source = :source AND flt.destination = :destination");
		return (List<TBLFlight>)query.setParameter("source", searchFlight.getSource()).setParameter("destination", searchFlight.getDestination()).list();
	}

	@Override
	public void populateFlights(TBLFlight flight) {
			getSession().save(flight);
	}
	
	@Override
	public void populateFlightLogo(TBLFlightLogo flightLogo) {
			getSession().save(flightLogo);
	}

}
