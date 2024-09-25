package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Administrateur;
import com.example.demo.entity.Demande;
import com.example.demo.repository.AdministrateurRepository;
@Service
public class AdminService {
	@Autowired
	AdministrateurRepository admin;
	
	 public Administrateur getAdminById(Long id) {
	        Optional<Administrateur> optionalDemande = admin.findById(id);
	        return optionalDemande.orElseThrow(() -> new RuntimeException("Citoyen not found"));
	    }
}
