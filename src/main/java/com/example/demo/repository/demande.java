package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Fonctionnaire;

import jakarta.transaction.Transactional;

@Repository
public interface demande extends JpaRepository<Demande, Long> {
    
    @Query("SELECT COUNT(d) FROM Demande d, Citoyen c, Fonctionnaire f " +
            "WHERE d.citoyen.id_cit = c.id_cit AND c.commune = f.commune AND f.id_foc = :id")
    long nombre_demande(@Param("id") Long nom);

    @Query("SELECT COUNT(d), d.date FROM Demande d, Citoyen c, Fonctionnaire f " +
            "WHERE d.citoyen.id_cit = c.id_cit AND c.commune = f.commune AND f.id_foc = :id " +
            "GROUP BY d.date")
    List<Object[]> demande_date(@Param("id") Long nom);

    @Query("SELECT COUNT(d) FROM Demande d, Citoyen c, Fonctionnaire f " +
            "WHERE d.citoyen.id_cit = c.id_cit AND c.commune = f.commune AND f.id_foc = :id " +
            "AND d.etatCitoyen = :etat")
    long demande_traite(@Param("id") Long nom, @Param("etat") String etat);
    
    @Modifying
    @Transactional
    @Query("UPDATE Demande c SET c.datetraite = :date_T WHERE c.id_dmd = :id_demande")
    void update_dem_insert_dateT(@Param("date_T") Date dateT, @Param("id_demande") Long id_demande);

    
    
    @Modifying
    @Transactional
    @Query("UPDATE Demande c SET c.etatCitoyen = :etat_cit WHERE c.citoyen.id_cit = :id_ci and c.id_dmd = :dm")
    void update_etat_cit(@Param("id_ci") Long id, @Param("etat_cit") String etat, @Param("dm") Long dm);

    
    
    @Modifying
    @Transactional
    @Query("SELECT d FROM Demande d, Citoyen c, Fonctionnaire f WHERE d.citoyen.id_cit = c.id_cit AND c.commune.id = f.commune.id AND f.id_foc = :id AND d.statut = :statut AND d.etatAdmin = :etat AND d.etatCitoyen = :etatCitoyen")
    List<Demande> demande1(@Param("statut") String statut, @Param("id") long id_f, @Param("etat") String etat, @Param("etatCitoyen") String etat_cit);

    @Modifying
    @Transactional
    @Query("UPDATE Demande d SET d.document.fonctionnaire.id_foc = :id_fonc WHERE d.id_dmd = :id_demande")
    void update_info_fonct(@Param("id_fonc") Long id_fonc, @Param("id_demande") Long id_demande);

}
