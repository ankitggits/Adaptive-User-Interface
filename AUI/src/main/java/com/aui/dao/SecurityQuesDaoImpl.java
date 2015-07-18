package com.aui.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.model.TBLSecurityQuestion;

@Transactional(propagation=Propagation.MANDATORY)
public class SecurityQuesDaoImpl extends GenericDaoImpl<TBLSecurityQuestion> implements SecurityQuesDao{

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.MANDATORY,readOnly=true)
	public List<TBLSecurityQuestion> retriveSecurityQues() {
		Query query= getSession().createQuery("from TBLSecurityQuestion");
		return (List<TBLSecurityQuestion>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.MANDATORY,readOnly=true)
	public List<TBLSecurityQuestion> retriveSecurityQues(String group) {
		Query query= getSession().createQuery("from TBLSecurityQuestion tsq where tsq.group = :xyz");
		return (List<TBLSecurityQuestion>) query.setParameter("xyz",group).list();
	}

	@Override
	public void populateSecurityQues(TBLSecurityQuestion securityQues) {
		getSession().save(securityQues);
	}

	@Override
	@Transactional(propagation=Propagation.MANDATORY,readOnly=true)
	public TBLSecurityQuestion retriveSecurityQuesById(String questionId) {
		/*Query query = getSession().createQuery("from TBLSecurityQuestion tsq where tsq.id= :id").setParameter("id", questionId);
		return (TBLSecurityQuestion)query.uniqueResult();*/
		
		return (TBLSecurityQuestion)getSession().get(TBLSecurityQuestion.class, questionId);
	}

}
