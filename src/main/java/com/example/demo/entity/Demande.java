package com.example.demo.entity;

import java.io.Serializable;

import java.util.*;
import java.util.Collection;
import org.hibernate.annotations.Cascade;
import jakarta.persistence.*;


@Entity
public class Demande implements Serializable {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_dmd")
	private Long id_dmd;
	private String raison ;
	private String statut ;
	private Date date ;
	@Column(name = "etat_citoyen")
	private String etatCitoyen;
	public String getEtatAdmin() {
		return etatAdmin;
	}
	@Temporal(TemporalType.DATE)
	private Date datetraite;
	public Date getDatetraite() {
		return datetraite;
	}
	public void setDatetraite(Date datetraite) {
		this.datetraite = datetraite;
	}
	public void setEtatAdmin(String etatAdmin) {
		this.etatAdmin = etatAdmin;
	}
	@Column(name = "etat_admin")
	private String etatAdmin;
	@OneToOne
	private Document document ;
	@ManyToOne
	private Administrateur administrateur ;
	@ManyToOne
	private Citoyen citoyen ;
	

	
	public String getEtatCitoyen() {
		return etatCitoyen;
	}
	public void setEtatCitoyen(String etatCitoyen) {
		this.etatCitoyen = etatCitoyen;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public Administrateur getAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	public Citoyen getCitoyen() {
		return citoyen;
	}
	public void setCitoyen(Citoyen citoyen) {
		this.citoyen = citoyen;
	}

	
	
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date2) {
		this.date = date2;
	}
	public Long getId_dmd() {
		return id_dmd;
	}
	public void setId_dmd(Long id_dmd) {
		this.id_dmd = id_dmd;
	}
	public String getRaison() {
		return raison;
	}
	public void setRaison(String raison) {
		this.raison = raison;
	}
	
	public Demande() {

}
	

}
