package com.aui.dao;

import java.util.List;

import com.aui.model.TBLTicket;

public interface TicketDao extends GenericDao<TBLTicket>{

	List<TBLTicket> getTicketsByUsername(String username);
	
}
