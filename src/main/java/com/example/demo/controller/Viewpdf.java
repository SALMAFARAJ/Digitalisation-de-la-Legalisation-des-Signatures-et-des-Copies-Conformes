package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Citoyen;
import com.example.demo.repository.CommuneReposotiry;
import com.example.demo.repository.citoyenRepository;
import com.example.demo.repository.demandeRepository;
import com.example.demo.repository.documentRepository;
import com.example.demo.repository.fonctionaireRepository;
import com.example.demo.service.DemandeService;
import com.example.demo.service.ReclamationService;
import com.example.demo.service.citoyenService;
import com.example.demo.service.documentService;

import jakarta.servlet.http.HttpSession;



@Controller
public class Viewpdf {
	@Autowired
    private com.example.demo.service.citoyenService citoyenService;
   @Autowired
    private CommuneReposotiry comm;

 @Autowired
    private documentService documentService;
 @Autowired
    private demandeRepository demandeRepository;

    @Autowired
    private fonctionaireRepository fonc;
    @Autowired
    private citoyenRepository city;
    @Autowired
    private citoyenService citservice;
    @Autowired
    private DemandeService demService;
    @Autowired
    private ReclamationService reclservice ;
    
	
    @PostMapping("/show_pdf")
    public ResponseEntity<byte[]> viewPdf(@RequestParam("documentContenu") String documentName) {
        // Obtenez le chemin complet du fichier sur le bureau ou où qu'il soit
        String filePath = "C:/Users/SALMA/Desktop/" + documentName;

        // Lisez le contenu du fichier en tant que tableau d'octets
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(filePath));

            // Définissez les en-têtes de réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setCacheControl(CacheControl.noCache().mustRevalidate());
            headers.set("Content-Disposition", "inline; filename=" + documentName);

            // Renvoyez le fichier en tant que réponse
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
