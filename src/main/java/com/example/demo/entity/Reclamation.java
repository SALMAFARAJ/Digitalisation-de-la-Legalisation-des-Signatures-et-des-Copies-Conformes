package com.example.demo.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Reclamation implements Serializable  {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_rec;
	@Temporal(TemporalType.DATE)
	private Date date_Soumission;
    private String type  ;
    private String contenu ;
    
    public String getContenu() {
		return contenu;
	}
	public Citoyen getCitoyen() {
		return citoyen;
	}
	public void setCitoyen(Citoyen citoyen) {
		this.citoyen = citoyen;
	}
	public void setDate_Soumission(Date date_Soumission) {
		this.date_Soumission = date_Soumission;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	@ManyToOne
    private Citoyen citoyen ;

	public Long getId_rec() {
		return id_rec;
	}
	public void setId_rec(Long id_rec) {
		this.id_rec = id_rec;
	}
	public Date getDate_Soumission() {
		return date_Soumission;
	}
	public void setDateSoumission(Date date) {
	    this.date_Soumission =  date;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Reclamation(){
		
	}
	
}