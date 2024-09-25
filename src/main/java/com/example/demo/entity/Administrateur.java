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
public class Administrateur implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	private String nom ;
	private String prenom ;
	private String email ;
	private String pwd ;
	@OneToMany(mappedBy="administrateur")
	private Collection<Demande> demandes  ;
	
	
	@OneToMany(mappedBy="administrateur")
	private Collection<Fonctionnaire> fonctionnaires ;
	
	
	@ManyToOne
	private Commune commune ;
	
	
	
	public Collection<Demande> getDemandes() {
		return demandes;
	}
	public void setDemandes(Collection<Demande> demandes) {
		this.demandes = demandes;
	}
	public Collection<Fonctionnaire> getFonctionnaires() {
		return fonctionnaires;
	}
	public void setFonctionnaires(Collection<Fonctionnaire> fonctionnaires) {
		this.fonctionnaires = fonctionnaires;
	}
	public Commune getCommune() {
		return commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Administrateur() {
		
	}
	


}
