package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Demande;
import com.example.demo.repository.demandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
@Service
public class DemandeService {
    
    private demande de;

    @Autowired
    public DemandeService(demande demandeRepository) {
        this.de = demandeRepository;
    }
    public void insert_dateT(Date date, Long id_dmd) {
        de.update_dem_insert_dateT(date, id_dmd);
    }
   
    
    
    public void update_citoy(Long cit, Long id_dmd) {
    	de.update_etat_cit(cit, "traite", id_dmd);
    }
    
    
    
    @Autowired
    private demandeRepository demande;

    public Demande getDemandeById(Long id) {
        Optional<Demande> optionalDemande = demande.findById(id);
        return optionalDemande.orElseThrow(() -> new RuntimeException("Citoyen not found"));
    }

    public long find_demande(Long id) {
        return de.nombre_demande(id);
    }

    public List<Object[]> demande_par_date(Long id) {
        return de.demande_date(id);
    }

    public long demande_traait(Long id) {
        return de.demande_traite(id, "traite");
    }
    
    public List<Demande> demande1(String statut, long id_f, String etat, String etat_cit) {
        List<Demande> list = de.demande1(statut, id_f, etat, etat_cit);

        return list;
    }
    public void update_fonctio_dem(Long id_fon, Long Id_dem) {
        de.update_info_fonct(id_fon, Id_dem);
    }
}
