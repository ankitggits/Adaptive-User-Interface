package com.aui.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.model.TBLTicket;

@Transactional(propagation = Propagation.MANDATORY)
public class TicketDaoImpl extends GenericDaoImpl<TBLTicket> implements TicketDao{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.MANDATORY,readOnly=false)
	public List<TBLTicket> getTicketsByUsername(String userName) {
		return (List<TBLTicket>) getSession().createQuery("from TBLTicket t where t.userName=:userName").setParameter("userName", userName).list();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY,readOnly=false)
	public long getNoOfBookings(String userName) {
		return (Long) getSession().createQuery("select count(*) from TBLTicket t where t.userName=:userName").setParameter("userName", userName).uniqueResult();
	}

}
