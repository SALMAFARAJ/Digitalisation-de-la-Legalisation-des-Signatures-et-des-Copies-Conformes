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
public class Commune implements Serializable{
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	private String nom ;
	private String code_postal ;
	private double latitude,longitude ;
	
	@OneToMany(mappedBy="commune")
	private Collection<Administrateur> administrateurs ;
	
	
	@OneToMany(mappedBy="commune")
	private Collection<Fonctionnaire> fonctionnaire ;
	
	@OneToMany(mappedBy="commune")
	private Collection<Citoyen> citoyen;
	

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
	public String getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Commune() {
		
	}
	

}
