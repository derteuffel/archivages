package com.derteuffel.archives.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="traitement")
public class Traitement implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String task;
	
	@ManyToOne
	private Compte compte;
	
	@ManyToOne
	private Archive archive;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date date = new Date();

	@OneToMany(mappedBy = "traitement")
	private List<Status> statusList;


	public Traitement() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Archive getArchive() {
		return archive;
	}


	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
}
