/**
 * 
 */
package com.aui.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="AUTHENTICATION",uniqueConstraints={@UniqueConstraint(columnNames={"USERNAME"})})
public class TBLAuthentication extends TBLRoot implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="USERNAME")
	private String username;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="STATUS")
	private String status;
	
	//Added By Priya on 19/04/15
	@OneToMany(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@Cascade(value=CascadeType.ALL)
	private List<TBLUserQuesAns> tblUserQuesAns;
	
	public List<TBLUserQuesAns> getTblUserQuesAns() {
		return tblUserQuesAns;
	}

	public void setTblUserQuesAns(List<TBLUserQuesAns> tblUserQuesAns) {
		this.tblUserQuesAns = tblUserQuesAns;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
