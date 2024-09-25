package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Administrateur;

public interface AdministrateurRepository  extends JpaRepository<Administrateur, Long> {
  
	Administrateur findByEmailAndPwd(String email, String pwd);
	Optional<Administrateur> findById(Long id_admin);
}
