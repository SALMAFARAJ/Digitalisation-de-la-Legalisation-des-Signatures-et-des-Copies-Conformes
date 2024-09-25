package com.example.demo.entity;



import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Citoyen implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_cit  ;
	private String nom ;
	private String prenom ;
	private String email ;
	private String code;
	

	public String getEtat_civil() {
		return etat_civil;
	}
	public void setEtat_civil(String etat_civil) {
		this.etat_civil = etat_civil;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	private String Img;
	public String getImg() {
		return Img;
	}
	public void setImg(String img) {
		Img = img;
	}
	private String cin ;
	private String etat_civil;
	private String adresse;
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	private String pwd;
	@OneToMany(mappedBy="citoyen")
	private Collection<Reclamation> reclamations  ;
	
	@OneToMany(mappedBy="citoyen")
	private Collection<Demande> demandes ;
	
	@ManyToOne
	private Commune commune;
	
	
	public Collection<Reclamation> getReclamations() {
		return reclamations;
	}
	public void setReclamations(Collection<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}
	public Collection<Demande> getDemandes() {
		return demandes;
	}
	public void setDemandes(Collection<Demande> demandes) {
		this.demandes = demandes;
	}
	public Commune getCommune() {
		return commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	
	
	
	public void setId_cit(Long id_cit) {
		this.id_cit = id_cit;
	}
	public Long getId_cit() {
		return id_cit;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCin() {
		return cin ;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public Citoyen() {
		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
