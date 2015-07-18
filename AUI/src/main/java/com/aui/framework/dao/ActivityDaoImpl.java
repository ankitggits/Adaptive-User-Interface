package com.aui.framework.dao;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.GenericDaoImpl;
import com.aui.framework.model.TBLActivity;

@Transactional(propagation = Propagation.REQUIRED)
public class ActivityDaoImpl extends GenericDaoImpl<TBLActivity> implements ActivityDao{

	@Override
	public void logActivity(TBLActivity tblActivity) {
		getSession().saveOrUpdate(tblActivity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly = true)
	public TBLActivity retrieveActivityByUserName(String userName) {
		Query query = getSession().createQuery("from TBLActivity ta where ta.userName =:userName");
		return (TBLActivity)query.setParameter("userName", userName).uniqueResult();
	}

}
