package com.derteuffel.archives.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.derteuffel.archives.enums.EStatus;

@Entity
@Table(name="status")
public class Status implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date = new Date();

	@Enumerated
	private EStatus status;
	
	private Boolean validate;
	
	
	private String fileUrl;


	private String observation;
	
	@ManyToOne
	private Traitement traitement;

	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}


	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Traitement getTraitement() {
		return traitement;
	}

	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public Boolean getValidate() {
		return validate;
	}

	public void setValidate(Boolean validate) {
		this.validate = validate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
}
