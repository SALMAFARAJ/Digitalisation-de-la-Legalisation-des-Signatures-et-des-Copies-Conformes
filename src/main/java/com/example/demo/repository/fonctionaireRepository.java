package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Administrateur;
import com.example.demo.entity.Fonctionnaire;

public interface fonctionaireRepository extends JpaRepository<Fonctionnaire, Long> {
    void deleteById(Long id);
    Fonctionnaire findByEmailAndPassword(String email, String pwd);
    List<Fonctionnaire> findByCommuneId(Long communeId);
}
