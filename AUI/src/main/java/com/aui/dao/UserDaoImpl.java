package com.aui.dao;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.model.TBLUser;

@Transactional(propagation = Propagation.MANDATORY)
public class UserDaoImpl extends GenericDaoImpl<TBLUser>implements UserDao{

	@Override
	public void doRegister(TBLUser tblUser) {
		getSession().save(tblUser);
	}
	
	@Override
	@Transactional(propagation = Propagation.MANDATORY,readOnly = true)
	public TBLUser getUserByUserName(String userName){
		Query query = getSession().createQuery("from TBLUser tu where tu.tblAuthentication.username =:userName");
		return (TBLUser)query.setParameter("userName", userName).uniqueResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = false)
	public void truncateTable(String tableToTruncate){
		String hql = String.format("delete from %s",tableToTruncate);
	    getSession().createQuery(hql).executeUpdate();
	}
	
}
