package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Commune;
import com.example.demo.entity.Demande;

import jakarta.transaction.Transactional;

public interface demandeRepository extends JpaRepository<Demande, Long> {
	List<Demande> findByCitoyen_CommuneAndStatut(Commune commune, String statut);
    Optional<Demande> findById(Long id_dmd);
    List<Demande> findByEtatCitoyenAndCitoyen(String etatCitoyen,Citoyen cit);
    @Modifying
    @Transactional
    @Query("UPDATE Demande d SET d.document.fonctionnaire.id_foc = :id_fonc WHERE d.id_dmd = :id_demande")
    void update_info_fonct(@Param("id_fonc") Long id_fonc, @Param("id_demande") Long id_demande);

   }

