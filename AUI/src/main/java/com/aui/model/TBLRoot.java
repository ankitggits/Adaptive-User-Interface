/**
 * 
 */
package com.aui.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author sinankit
 *
 */
@MappedSuperclass
public class TBLRoot {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true)
	private String  id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDON",updatable=false,nullable=false)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATEDON",nullable=false)
	private Date updatedOn;
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public String getId() {
		return id;
	}
	
	@PrePersist
	public void predateModification(){
		this.createdOn=new Date();
		this.updatedOn=new Date();
	}
	
	@PostUpdate
	public void  postDateModification(){
		this.updatedOn=new Date();
	}
	
}
