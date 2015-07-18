package com.aui.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.model.TBLAuthentication;

@Transactional(propagation = Propagation.MANDATORY, readOnly=true)
public class AuthenticationDaoImpl extends GenericDaoImpl<TBLAuthentication> implements AuthenticationDao {

	@Override
	public TBLAuthentication getAuthenticationByUserName(String userName) {
		Query query = getSession().createQuery("from TBLAuthentication ta where ta.username = :username");
		return (TBLAuthentication) query.setParameter("username", userName).uniqueResult();
	}
	
	@Override
	public boolean isUsernameExists(String username) {
		int count = ((Long)getSession().createQuery("select count(*) from TBLAuthentication ta where ta.username = :username").setParameter("username", username).uniqueResult()).intValue();
		return (count>0)?true:false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserSecurityQuesAns(String authenticationId) {
		Query query = getSession().createQuery("from authentication_user_security_quesans ta where ta. = :userid");
		return query.setParameter("userid", authenticationId).list();
	}

	public void saveOrUpdateAuthentication(TBLAuthentication tblAuthentication){
		
		/*if(null==tblAuthentication.getId() || "".equals(tblAuthentication.getId())){
			getSession().save(tblAuthentication);
		}else{
			getSession().merge(tblAuthentication);
		}*/
		
		getSession().saveOrUpdate(tblAuthentication);
	}

}
