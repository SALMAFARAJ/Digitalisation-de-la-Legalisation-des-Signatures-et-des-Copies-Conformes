package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Commune;


public interface CommuneReposotiry extends JpaRepository<Commune, Long> {
  
	Commune findByNom(String nom);

}
