package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Commune;

import jakarta.transaction.Transactional;


public interface citoyenRepository extends JpaRepository<Citoyen, Long> {
	@Query("SELECT COUNT(c) FROM Citoyen c JOIN Fonctionnaire f ON c.commune = f.commune WHERE f.id_foc = :id")
	long nombre_citoye(@Param("id") Long nom);

	Citoyen findByEmailAndPwd(String Email, String cin);
	 Optional<Citoyen> findById(Long id);
	 List<Citoyen> findByCommuneId(Long communeId);
	   @Modifying
	    @Transactional
	    @Query("UPDATE Citoyen c SET c.adresse = :adresse, c.etat_civil = :etat WHERE c.id_cit = :id")
	    void update_info(@Param("id") Long id, @Param("etat") String etat, @Param("adresse") String adresse);
	   @Modifying
	    @Transactional
	    @Query("UPDATE Demande d SET d.etatCitoyen = :etat_cit WHERE d.citoyen.id_cit = :id_ci and d.id_dmd = :dm")
	    void update_etat_citoyen(@Param("id_ci") Long id, @Param("etat_cit") String etat, @Param("dm") Long dm);

	   @Query("SELECT c.email FROM Citoyen c, Demande d WHERE d.citoyen = c AND d.id_dmd = :dm")
	   String email_cit(@Param("dm") Long dm);

}
