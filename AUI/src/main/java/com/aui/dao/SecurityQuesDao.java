package com.aui.dao;

import java.util.List;

import com.aui.model.TBLSecurityQuestion;

public interface SecurityQuesDao extends GenericDao<TBLSecurityQuestion> {

	List<TBLSecurityQuestion> retriveSecurityQues();
	
	TBLSecurityQuestion retriveSecurityQuesById(String questionId);
	
	List<TBLSecurityQuestion> retriveSecurityQues(String Group);
	
	void populateSecurityQues(TBLSecurityQuestion securityQues);
}
