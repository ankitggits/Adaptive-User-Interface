package com.aui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="TRANSACTION_HISTORY")
public class TBLTransactionHistory extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="TRANSACTION_ID")
	private String transactionId;
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@Cascade(value=CascadeType.ALL)
	private TBLTicket tblTicket;
	@Column(name="STATUS")
	private String status;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public TBLTicket getTBLTicket() {
		return tblTicket;
	}

	public void setTBLTicket(TBLTicket tblTicket) {
		this.tblTicket = tblTicket;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
