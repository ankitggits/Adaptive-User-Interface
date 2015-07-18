package com.aui.dao;

import java.util.List;
import com.aui.model.TBLAuthentication;

public interface AuthenticationDao extends GenericDao<TBLAuthentication> {
	
	TBLAuthentication getAuthenticationByUserName(String userName);
	
	List<String> getUserSecurityQuesAns(String authenticationId);
	
	boolean isUsernameExists(String username);
	
	void saveOrUpdateAuthentication(TBLAuthentication tblAuthentication);
	
}
