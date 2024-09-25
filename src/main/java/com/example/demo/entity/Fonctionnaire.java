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
public class Fonctionnaire implements Serializable  {
	
	
	@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_foc  ;
	private String nom ;
	private String prenom ;
	private String email ;
	private String password ;
	
	@ManyToOne
	private Administrateur administrateur ;
	
	
	@OneToMany(mappedBy="fonctionnaire")
	private Collection<Document> documents ;
	
	@ManyToOne
	private Commune commune ;
	
	public Administrateur getAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	public Collection<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(Collection<Document> documents) {
		this.documents = documents;
	}
	public Commune getCommune() {
		return commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	public Long getId_foc() {
		return id_foc;
	}
	public void setId_foc(Long id_foc) {
		this.id_foc = id_foc;
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
		email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Fonctionnaire() {

}

}
