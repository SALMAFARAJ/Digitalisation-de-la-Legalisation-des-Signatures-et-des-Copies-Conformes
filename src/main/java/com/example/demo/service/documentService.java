
package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Document;
import com.example.demo.repository.demandeRepository;
import com.example.demo.repository.documentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class documentService {

    private final documentRepository doc;

    @Autowired
    public documentService(documentRepository doc) {
        this.doc = doc;
    }

    public Document getDocById(Long id) {
        Optional<Document> optionalDocument = doc.findById(id);
        return optionalDocument.orElseThrow(() -> new RuntimeException("Citoyen not found"));
    }
    
    public Document getDocumentByContenu(String contenu) {
    	return doc.findByContenu(contenu);
    }
    

    public void update_inf_fonct(Long idf, Long id_dem) {
        doc.update_info_fonct(idf, id_dem);
    }

    public void update_doc(long id1, long id2) {
        doc.update(id1, id2);
    }

}
