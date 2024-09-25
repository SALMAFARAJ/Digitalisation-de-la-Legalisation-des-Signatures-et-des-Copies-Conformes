package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Fonctionnaire;
import com.example.demo.repository.fonctionaireRepository;
@Service
public class FonctionnaireServise {
	 private final fonctionaireRepository fonc;

	    @Autowired
	    public FonctionnaireServise(fonctionaireRepository fonc) {
	        this.fonc = fonc;
	    }

	public Fonctionnaire getFonctionnaireByID(Long id1) {
		
		return fonc.findById(id1).orElse(null);
	}
}
