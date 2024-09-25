package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Administrateur;
import com.example.demo.entity.Commune;
import com.example.demo.repository.CommuneReposotiry;

@Service
public class CommuneService {

    @Autowired
    private CommuneReposotiry communeRepository;

    public List<Commune> getAllCommunes() {
        return communeRepository.findAll();
    }
    public Commune getCommuneByNom(String nom) {
        return communeRepository.findByNom(nom);
    }
    public Commune getCommuneById(Long id) {
        Optional<Commune> optionalDemande = communeRepository.findById(id);
        return optionalDemande.orElseThrow(() -> new RuntimeException("Citoyen not found"));
    }
}