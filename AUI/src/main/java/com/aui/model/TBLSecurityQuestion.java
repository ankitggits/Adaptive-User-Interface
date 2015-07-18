package com.aui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="SECURITY_QUESTION")
public class TBLSecurityQuestion extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="GROUP_NAME")	
	private String group;
	
	@Column(name="QUESTION")	
	private String question;
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}

}
