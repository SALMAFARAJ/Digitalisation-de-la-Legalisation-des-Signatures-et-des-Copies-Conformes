package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//Importez les annotations nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Commune;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Document;
import com.example.demo.entity.Reclamation;
import com.example.demo.repository.citoyenRepository;
import com.example.demo.repository.demandeRepository;
import com.example.demo.repository.documentRepository;
import com.example.demo.repository.reclamationRepository;
import com.example.demo.service.CommuneService;
import com.example.demo.service.citoyenService;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DemoController {
	@Autowired
    private citoyenRepository cit;
	@Autowired
	private citoyenService citoyenService;
	 @Autowired
	 private CommuneService communeService;	
	 @Autowired
	 private  reclamationRepository recl;
	 @Autowired
	 private  demandeRepository demandeRepositry;
	 @Autowired
	 private  documentRepository doc;
	 @Autowired
	 private  citoyenService citSer;
	 //homePage1
		@GetMapping("/")
		public String home() {
		   return "home1";
		}
		//homePage
		@GetMapping("/home")
		public String hi() {
		   return "home";
		}
		
		
		@GetMapping("/reclamation")
		public String reclamation() {
		    return "reclamation";
		}

		@RequestMapping(value = "/reclamation", method = RequestMethod.POST)
		public String reclamation(HttpServletRequest request, Model model, @ModelAttribute Reclamation r, HttpSession session) {
		    Long citoyenId = (Long) session.getAttribute("citoyenId");
            
		    if (citoyenId != null) {
		        Citoyen citoyen = citoyenService.getCitoyenById(citoyenId);
		        model.addAttribute("citoyen", citoyen);

		        String name = request.getParameter("name");
		        String email = request.getParameter("email");
		        String type = request.getParameter("type");
		        String message = request.getParameter("message");
		        Date currentDateAndDate = new Date();
		        Reclamation rc = new Reclamation();
		        rc.setContenu(message);
		        rc.setType(type);
		        rc.setDateSoumission(currentDateAndDate);
                rc.setCitoyen(citoyen);
		        // Save the reclamation
		        recl.save(rc);

		        session.setAttribute("message", "Réclamation soumise avec succès...");

		        return "home";
		    } else {
		        // Redirect to the login page if the citoyenId is not present in the session
		        return "redirect:/";
		    }
		}
		
		
		
		
		
		
		//get page pour soummettre la demande(Rabab)	
		
		 @GetMapping("/soumettre")
	      public String getdem(Model m,HttpSession session) {
	      
	      Long citoyenId = (Long) session.getAttribute("citoyenId");
	        Citoyen c=citoyenService.getCitoyenById(citoyenId);
	        m.addAttribute("citoyen", c);
	        return "soumettre";
	      }
	    
	    
		// Method to generate a random code
		 private String generateRandomCode() {
		     // Define the characters that can be used in the code
		     String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		     int codeLength = 8;

		     // Generate a random code using characters
		     StringBuilder code = new StringBuilder();
		     Random random = new Random();
		     for (int i = 0; i < codeLength; i++) {
		         int index = random.nextInt(characters.length());
		         code.append(characters.charAt(index));
		     }

		     return code.toString();
		 }
			
		 @GetMapping("/Modifi")
	      public String getdemM(Model m,HttpSession session) {
	      
	      Long citoyenId = (Long) session.getAttribute("citoyenId");
	       Citoyen c=citoyenService.getCitoyenById(citoyenId);
	       m.addAttribute("citoyen", c);
	        return "Modifi";
	      }
	    
	    
	    @PostMapping("/soumis")
	    public String insert_demnade(HttpSession session,Model m, String cont, HttpServletRequest req, long id, String file) {
	    	
	        Date date = new Date(System.currentTimeMillis());
	        Demande d = new Demande();
	        Document doct = new Document(); // Utilisation de la même référence d'objet
	        String confidentialCode = generateRandomCode();
	        // Set other properties of Document
	        doct.setContenu(file);
	        doct.setFonctionnaire(null);

	        // Sauvegarder le document
	        doc.save(doct);

	        d.setDocument(doct);
	        d.setAdministrateur(null);
	        Citoyen c = citoyenService.getCitoyenById(id);
	        m.addAttribute("citoyen", c);
            c.setCode(confidentialCode);
	        d.setCitoyen(c);
	        d.setDate(date);
	        d.setRaison("Egalisation");
	        d.setEtatAdmin("En cours");
	        d.setEtatCitoyen("En cours");
	        d.setRaison(null);
	        d.setStatut("signer document");

	        // Sauvegarder la demande
	        demandeRepositry.save(d);

	        return "succes";
	       
	    }
      @GetMapping("/succes")
      public String succes(Model m,HttpSession session){
    	  Long citoyenId = (Long) session.getAttribute("citoyenId");
	        Citoyen c=citoyenService.getCitoyenById(citoyenId);
	        m.addAttribute("citoyen", c);
	        return "succes";
      }
	    
	    
	// traitement de  page de Modifiction par client
	@PostMapping("/demande")
	public String insert_demande(Model m, HttpServletRequest req, String file, String raison, HttpSession session) {
	    Demande demande = new Demande();
	    Long citoyenId = (Long) session.getAttribute("citoyenId");

	    // Utiliser orElseThrow pour obtenir le Citoyen ou jeter une exception si non présent
	    Citoyen c = citSer.getCitoyenById(citoyenId);

	    m.addAttribute("citoyen", c);

	    Document docu = new Document();
	    docu.setContenu(file);
	    docu.setFonctionnaire(null);
	    demande.setDocument(docu);

	    Date date = new Date(System.currentTimeMillis());
	    demande.setDate(date);

	    demande.setAdministrateur(null);
	    demande.setCitoyen(c);

	    demande.setEtatAdmin("En cours");
	    demande.setEtatCitoyen("En cours");
	    demande.setRaison(raison);
	    demande.setStatut("changer information");

	    doc.save(docu);
	    demandeRepositry.save(demande);

	    return "home";
	}
		
		
	// Login page de citoyen
		@GetMapping("/Login")
		public String getLoginForm(Model model) {
			 List<Commune> communes= communeService.getAllCommunes();	
			    model.addAttribute("communes", communes);
			    model.addAttribute("citoyen", new Citoyen());
		    return "Login";
		}
	// traitement de Login
		@RequestMapping(value="/Login", method=RequestMethod.POST)
	    public String login(HttpServletRequest request, Citoyen ct, Model model, HttpSession session) {
	        String email = request.getParameter("email");
	        String pwd= request.getParameter("password");

	        Citoyen userEntity = cit.findByEmailAndPwd(email, pwd);

	        if (userEntity != null) {
	            session.setAttribute("citoyenId", userEntity.getId_cit());
	            return "redirect:/home";
	        } else {
	            model.addAttribute("error", "Invalid email or password");
	            return "redirect:/reclamation";
	        }
	    }
         
	//register Traitement
    @PostMapping("/register")
    public String register(HttpServletRequest request, @RequestParam("communeName") String communeName, HttpSession session) {
    String nom = request.getParameter("nom");
    String prenom = request.getParameter("prenom");
    String email = request.getParameter("email");
    String pwd = request.getParameter("password");
    String cin = request.getParameter("cin");
    
    String adresse = request.getParameter("adresse");
    String etat_civil = request.getParameter("etat_civil");
    Commune commune = communeService.getCommuneByNom(communeName);
    Citoyen citoyen = new Citoyen();
    citoyen.setCommune(commune);
    citoyen.setNom(nom);
 
    citoyen.setAdresse(adresse);
    citoyen.setEtat_civil(etat_civil);
    citoyen.setPrenom(prenom);
    citoyen.setEmail(email);
    citoyen.setCin(cin);
    citoyen.setPwd(pwd);
    session.setAttribute("citoyenId", citoyen.getId_cit());
    cit.save(citoyen);

    return "redirect:/home";
}
    
    
   
    @GetMapping("/tables")
    public String table(Model model,HttpSession session) {
    	Long id=(Long)session.getAttribute("citoyenId");
    	Citoyen ci=citSer.getCitoyenById(id);
    	
    	
        // Les_demandes_traitées
        List<Demande> demandeList = demandeRepositry.findByEtatCitoyenAndCitoyen("traite",ci);
        model.addAttribute("demandeList", demandeList);

        // Les_demandes_en_cours_de_traitement
        List<Demande> demandesEnCoursList =demandeRepositry.findByEtatCitoyenAndCitoyen("En Cours",ci);
        model.addAttribute("demandesEnCoursList", demandesEnCoursList);

        return "tables";
    }
    // formulaire de code confidentiel
    @RequestMapping(value = "/verifierCode", method = RequestMethod.POST)
    public String login(@RequestParam("code") String code, Model model, @RequestParam("id_document") Long id_doc,HttpSession session) {
    	Long id=(Long)session.getAttribute("citoyenId");

        Citoyen userEntity = citSer.getCitoyenById(id);

        if (userEntity.getCode().equals(code)) {
            // Code correct, redirigez vers la vue qui affiche le PDF avec l'ID en tant que paramètre
            return "redirect:/viewPdf1?id=" + id + "&id_doc=" + id_doc;
        } else {
            // Code incorrect, redirigez vers une autre vue (par exemple, tableF)
            return "redirect:/tableF";
        }
    }
    
    @RequestMapping(value = "/viewPdf1", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<byte[]> viewPdf(@RequestParam("id") Long id, @RequestParam("id_doc") Long id_doc) {
    // Obtenez le citoyen par ID
    Citoyen citoyen = citSer.getCitoyenById(id);

    if (citoyen != null) {
        // Utilisez l'ID du document associé au citoyen
        Document doct = doc.findById(id_doc).orElse(null);

        if (doct != null) {
            String documentName = doct.getContenu();

            // Obtenez le chemin complet du fichier sur le bureau ou où qu'il soit
            String filePath = "C:/Users/salwa/OneDrive/Bureau/" + documentName + ".pdf";
            System.out.println("Chemin d'accès au fichier : " + filePath);

            // Lisez le contenu du fichier en tant que tableau d'octets
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

                // Définissez les en-têtes de réponse
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData(documentName, documentName);
                headers.setCacheControl(CacheControl.noCache().mustRevalidate());

                // Renvoyez le fichier en tant que réponse
                return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Si le citoyen ou le document n'est pas trouvé, renvoyez une réponse 404
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
}
}


