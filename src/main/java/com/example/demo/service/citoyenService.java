package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Commune;
import com.example.demo.repository.citoyenRepository;

@Service
public class citoyenService {
	@Autowired
	private citoyenRepository cr;

    private  citoyenRepository citoyen;

    @Autowired
    public citoyenService(citoyenRepository citoyen) {
        this.citoyen = citoyen;
    }

    public Citoyen getCitoyenById(Long id) {
        Optional<Citoyen> optionalCitoyen = citoyen.findById(id);
        return optionalCitoyen.orElseThrow(() -> new RuntimeException("Citoyen not found"));
    }
    public long find_citoyen(Long id) {
    	 return citoyen.nombre_citoye(id);
 		
   	}
    public void update_etat_cit(Long id, Long id_dem) {
        cr.update_etat_citoyen(id, "taite", id_dem);
    }
    public void update_inf(String adresse, String etat, Long id) {
        cr.update_info(id, etat, adresse);
    }
  
   public void createUser(Citoyen ct) {
	   cr.save(ct);
   }
//   
   public List<Citoyen> getAllCitoyen(){
	   return citoyen.findAll();
   }
   
   
  

   public String emailCit(long idDem) {
       String em = cr.email_cit(idDem);
       return em;
   }

  
   
   
   
   
//   
//   public Citoyen getById(int id) {
//	   Optional<Citoyen>c =cr.findById(id);
//	   if(c.isPresent()) {
//		   return c.get();
//	   }
//	   return null;
//   }
//   public void deleteCitoyen(int id) {
//		cr.deleteById(id);
//	}
}
