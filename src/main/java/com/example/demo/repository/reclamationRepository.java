package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Demande;
import com.example.demo.entity.Reclamation;
@Repository
public interface reclamationRepository extends JpaRepository<Reclamation, Long> {


    @Query("SELECT COUNT(r) FROM Reclamation r, Citoyen c, Fonctionnaire f " +
            "WHERE r.citoyen.id_cit = c.id_cit AND c.commune = f.commune AND f.id_foc = :id")
    long nombre_reclam(@Param("id") Long nom);
    
    @Query("SELECT COUNT(r), r.date_Soumission FROM Reclamation r, Citoyen c, Fonctionnaire f " +
            "WHERE r.citoyen.id_cit = c.id_cit AND c.commune = f.commune AND f.id_foc = :id " +
            "GROUP BY r.date_Soumission")
    List<Object[]> reclamation_date(@Param("id") Long nom);


    @Query("SELECT r FROM Reclamation r WHERE r.citoyen.commune.id = :communeId")
    List<Reclamation> findReclamationsByCommuneIdCustomQuery(@Param("communeId") Long communeId);
    
}
