package com.aui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="USER_SECURITY_QUESANS")
public class TBLUserQuesAns extends TBLRoot implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="QUESTION_ID")
	private String questionId; 
	
	@Column(name="ANSWER")
	private String answer;
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
