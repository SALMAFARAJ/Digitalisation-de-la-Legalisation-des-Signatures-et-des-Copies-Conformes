package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Reclamation;
import com.example.demo.repository.reclamationRepository;

@Service
public class ReclamationService {
	  private reclamationRepository rec;
	  public ReclamationService(reclamationRepository Repository) {
		  this.rec= Repository;
	  }
	    
    
    
    public long nombre_reclamation(Long id) {
        return rec.nombre_reclam(id);
    }

    public List<Object[]> reclamation_date(Long id) {
        return rec.reclamation_date(id);
    }



//    public long reclam(Long communeId) {
//        return rec.reclamation(communeId);
//    }

}
