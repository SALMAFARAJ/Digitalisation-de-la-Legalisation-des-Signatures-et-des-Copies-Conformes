package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
public class Document implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_doc ;
	private String contenu ;
	
	@ManyToOne
	private Fonctionnaire fonctionnaire;
	@OneToOne(mappedBy = "document")
	private Demande demande ;
	
	public Fonctionnaire getFonctionnaire() {
		return fonctionnaire;
	}
	public void setFonctionnaire(Fonctionnaire fonctionnaire) {
		this.fonctionnaire = fonctionnaire;
	}
	public Long getId_doc() {
		return id_doc;
	}
	public void setId_doc(Long id_doc) {
		this.id_doc = id_doc;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	public Document() {
	

}

}
