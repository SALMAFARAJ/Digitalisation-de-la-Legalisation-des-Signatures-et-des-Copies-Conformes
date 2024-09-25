package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Document;

import jakarta.transaction.Transactional;

public interface documentRepository extends JpaRepository<Document, Long>{
	Optional<Document> findById(Long id_doc);
	 
	Document findByContenu(String contenu);
	@Modifying
	@Transactional
	@Query("UPDATE Document d SET d.fonctionnaire.id_foc = :idf " +
	       "WHERE d.id_doc IN (SELECT s.document.id_doc FROM Demande s WHERE s.document.id_doc = :id_dem)")
	void update_info_fonct(@Param("idf") Long idf, @Param("id_dem") Long id_dem);

	@Modifying
	@Transactional
	@Query("UPDATE Document c SET c.fonctionnaire.id_foc = :idf WHERE c.id_doc IN (SELECT d.document.id_doc FROM Demande d WHERE d.id_dmd = :id_dem)")
	void update(@Param("idf") long idf, @Param("id_dem") long idD);

}
