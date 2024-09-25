package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.http.ResponseEntity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Commune;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Document;
import com.example.demo.repository.CommuneReposotiry;
import com.example.demo.repository.demandeRepository ;
import com.example.demo.repository.documentRepository;
import com.example.demo.service.DemandeService;
import com.example.demo.service.documentService;
import jakarta.servlet.http.HttpSession;
@Controller
public class demandeController {
	 @Autowired
	    private com.example.demo.service.citoyenService citoyenService;
	   @Autowired
	    private CommuneReposotiry comm;
	 @Autowired
	    private DemandeService demandeService;
	 @Autowired
	    private documentService documentService;
	 @Autowired
	    private demandeRepository demandeRepository;
	    @Autowired
	    private documentRepository documentRepository;

	    @GetMapping("/AdminDmd")
	    public String afficherDetailsDemande(@RequestParam("id_dmd") Long idDmd,
	                                         @RequestParam("id_dmd") Long idDoc, Model model) {
	        // Récupérer la demande et le document à partir de leurs ID
	        Demande demandeOptional = demandeService.getDemandeById(idDmd);
	        Long id_cit = demandeOptional.getCitoyen().getId_cit();
	        Document documentOptional = documentService.getDocById(idDoc);
	        Citoyen citoyen = citoyenService.getCitoyenById(id_cit);
	        model.addAttribute("citoyen", citoyen);
	        if (documentOptional != null) {
	            // Mettre les objets dans le modèle pour les afficher dans la page Thymeleaf
	            model.addAttribute("demande", demandeOptional);
	            model.addAttribute("document", documentOptional);
	            return "AdminDmd";
	        } else {
	            return "erreur";
	        }
	    }
	    @GetMapping("/AdminModif")
	    public String afficherDetailsDemandeModif(@RequestParam("id_dmd") Long  idDmd, @RequestParam("id_dmd") Long idDoc, Model model) {
	        // Récupérer la demande et le document à partir de leurs ID
	    	Demande demandeOptional = demandeService.getDemandeById(idDmd);
	    	Long id_cit = demandeOptional.getCitoyen().getId_cit();
	        Document documentOptional = documentService.getDocById(idDoc);
	        Citoyen citoyen = citoyenService.getCitoyenById(id_cit);
	        model.addAttribute("citoyen", citoyen);

	        if (documentOptional!=null) {
	            // Mettre les objets dans le modèle pour les afficher dans la page Thymeleaf
	            model.addAttribute("demande", demandeOptional);
	            model.addAttribute("document", documentOptional);
	            return "AdminDmd"; // Remplacez "autrePage" par le nom de votre deuxième page Thymeleaf
	        } else {
	            // Gérer le cas où la demande ou le document n'est pas trouvé
	            return "erreur"; // Remplacez "erreur" par la page d'erreur appropriée
	        }
	    
	}
	    
	    @GetMapping("/demandeAd")
	    public String displayDemands(Model model, HttpSession session) {
	        Long idCommune = (Long) session.getAttribute("id_commune");
	        if (idCommune != null) {
	            Commune commune = comm.findById(idCommune).orElse(null);
	            if (commune != null) {
	            	List<Demande> demands = demandeRepository.findByCitoyen_CommuneAndStatut(commune,"signer document");
	                model.addAttribute("demandes", demands);
	            } else {
	                model.addAttribute("error", "Commune not found");
	            }
	        } else {
	            model.addAttribute("error", "Commune ID not found in session");
	        }

	        return "demandeAd";
	    }
	    

	    @GetMapping("/demandeMdf")
	    public String displayDemandsMdf(Model model, HttpSession session) {
	        Long idCommune = (Long) session.getAttribute("id_commune");
	        if (idCommune != null) {
	            Commune commune = comm.findById(idCommune).orElse(null);
	            if (commune != null) {
	                List<Demande> demands = demandeRepository.findByCitoyen_CommuneAndStatut(commune,"changer information");
	                model.addAttribute("demandes", demands);
	            } else {
	                model.addAttribute("error", "Commune not found");
	            }
	        } else {
	            model.addAttribute("error", "Commune ID not found in session");
	        }

	        return "demandeMdf";
	    }
	    
	    
	    
	    @PostMapping("/viewPdf")
	    public ResponseEntity<byte[]> viewPdf( @RequestParam("documentContenu") String documentName) {
	        // Obtenez le chemin complet du fichier sur le bureau ou où qu'il soit
	        String filePath = "C:/Users/SALMA/Desktop/" + documentName;

	        // Lisez le contenu du fichier en tant que tableau d'octets
	        byte[] fileContent;
			try {
				fileContent = Files.readAllBytes(Paths.get(filePath));
			

	        // Définissez les en-têtes de réponse
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData(documentName, documentName);
	        headers.setCacheControl(CacheControl.noCache().mustRevalidate());

	        // Renvoyez le fichier en tant que réponse
	           return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}return null;
	    }
			
	    
	    @PostMapping("/updateDmd")
	    public String updateDemande(@RequestParam("etatAdmin") String etatDemAdmin,
	            @RequestParam("id_dmd") Long id, Model model) {
	        Demande dd = demandeService.getDemandeById(id);
	        dd.setEtatAdmin(etatDemAdmin);
	        demandeRepository.save(dd);
	        Citoyen c=dd.getCitoyen();
	        if ("Rejeter".equals(etatDemAdmin)) {
	        	System.out.println("Sendingemail to salmafaraj06@gmail.com");
	        	  email1 email = new email1();
	              email.sendMail(c.getEmail());
	              System.out.println("Email sent successfully.");
	          }
	        return "redirect:/demandeAd";
	    }

	    @PostMapping("/updateModif")
	    public String updateDemandeModif(@RequestParam("etatAdmin") String etatDemAdmin,
	            @RequestParam("id_dmd") Long id, Model model) {
	        Demande dd = demandeService.getDemandeById(id);
	        dd.setEtatAdmin(etatDemAdmin);
	        
	        
	        demandeRepository.save(dd);
	        if ("Rejeter".equals(etatDemAdmin)) {
	        	email e;
	        	  email email = new email();
	              email.sendMail("salwafaraj02@gmail.com");
	              System.out.println("Email sent successfully.");
	          }

	        return "redirect:/demandeAd";
	    }
	  
	    
	    
	    
	    
	    
	    
}
