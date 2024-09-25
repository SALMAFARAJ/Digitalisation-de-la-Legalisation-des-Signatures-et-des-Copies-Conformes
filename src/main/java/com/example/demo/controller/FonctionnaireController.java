package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Fonctionnaire;
import com.example.demo.entity.Reclamation;
import com.example.demo.repository.citoyenRepository;
import com.example.demo.repository.fonctionaireRepository;
import com.example.demo.repository.reclamationRepository;
import com.example.demo.service.DemandeService;
import com.example.demo.service.FonctionnaireServise;
import com.example.demo.service.ReclamationService;
import com.example.demo.service.citoyenService;
import com.example.demo.service.documentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
public class FonctionnaireController {
	 private final FonctionnaireServise fonctionnaireService;

	    @Autowired
	    public FonctionnaireController(FonctionnaireServise fonctionnaireService) {
	        this.fonctionnaireService = fonctionnaireService;
	    }

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
    @Autowired
    private documentService doc ;

    // Login_de_fonctionnaire
    @GetMapping("/loginFct")
    public String login(Model model) {
        return "loginFct";
    }

    // traitement_de_Login_Fonctionnaire
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Citoyen ct, Model model, HttpSession session) {
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        Fonctionnaire userEntity = fonc.findByEmailAndPassword(email, pwd);

        if (userEntity != null) {
            session.setAttribute("id_foc", userEntity.getId_foc());
            session.setAttribute("id_commune", userEntity.getCommune().getId());
            session.setAttribute("nom", userEntity.getNom());
            return "redirect:/Fonct_Cit";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/loginAdmin";
        }
    }


        @Autowired
        private reclamationRepository recl;
     

        @GetMapping("/recl_fonc")
        public String recTable(Model model, HttpSession session) {
            Long id1 = (Long) session.getAttribute("id_foc");
            Fonctionnaire fct = fonctionnaireService.getFonctionnaireByID(id1);
            Long communeId = fct.getCommune().getId();
            List<Reclamation> reclamations = recl.findReclamationsByCommuneIdCustomQuery(communeId);
            model.addAttribute("reclamations", reclamations);
            model.addAttribute("reclamation", new Reclamation());

            return "recl_fonc";
        }

     //suppression_reclamation
        @GetMapping("/deleteRec/{id}")
        public String deleteRec(@PathVariable Long id, Model model) {
            
            recl.deleteById(id);
            return "redirect:/recl_fonc";
        }
        

    
 // affichage de la liste des citoyen
    @GetMapping("/Fonct_Cit")
    public String tableF(Model model, HttpSession session) {
        Long commune_id_com = (Long) session.getAttribute("id_commune");
        
        
        List<Citoyen> citoyens = city.findByCommuneId(commune_id_com);

        model.addAttribute("citoyensList", citoyens);
        model.addAttribute("newCitoyen", new Citoyen());

        return "Fonct_Cit";
    }
    
    
    // code historique des demandes effectues
    @RequestMapping(value = "/Historique_fonct", method = {RequestMethod.GET, RequestMethod.POST})
    public String histdem(Model m ,HttpSession session) {
    	Long id1 = (Long) session.getAttribute("id_foc");
    	List<Demande> list=demService.demande1("changer information" ,id1,"confirme" , "traite");
    	m.addAttribute("listeH", list);
    	m.addAttribute("id_f" ,id1);
    	
    	List<Demande> list2=demService.demande1("signer document" ,id1,"confirme" , "traite");
    	m.addAttribute("listeH2", list2);
    	m.addAttribute("id_f" ,id1);
    	
    	
    	return "Historique_fonct";
    }
    
    // Code affichage des demandes non traites
    @GetMapping("/table_dem")
    public String listdem(Model m ,HttpSession session) {
    	Long id1 = (Long) session.getAttribute("id_foc");
        
		List<Demande> list=demService.demande1("changer information" ,id1,"confirme" , "En cours");
		m.addAttribute("liste", list);
		session.setAttribute("id_foc", id1);
		System.out.println(id1);
		List<Demande> list1=demService.demande1("signer document" ,id1,"confirme" , "En cours");
		m.addAttribute("liste1", list1);
		return "table_dem";
	
	}
    
    // Code des Statistique
    @GetMapping("/dashboard")
	public String nb_citoyen(Model m,HttpSession session) {
		Long id = (Long) session.getAttribute("id_foc");
		Model addAttribute = m.addAttribute("nombre_citoyen",citservice.find_citoyen(id) );

		Model addAttribute1 = m.addAttribute("nombre_demande",demService.find_demande(id) );
        Model addAttribute2=m.addAttribute("nombre_reclamation",reclservice.nombre_reclamation(id));
        List <Object[]> list=demService.demande_par_date(id);
        List <Object[]> list1=reclservice.reclamation_date(id);

        for (int i=0 ;i <list.size();i++) {
        	
        	System.out.print(list.get(i));
        	    	
        }
        System.out.print("iiiiiiiiiiiiiiii"+list);
        Model addAttribute3=m.addAttribute("demande_date",list);
        Model addAttribute4=m.addAttribute("reclam_date",list1);
        Model addAttribute5=m.addAttribute("demande_traite",reclservice.nombre_reclamation(id));

		return "index.html";			
	}
    
    
    // wissal
    @GetMapping("/Modifi_foc")
	public String home1(@RequestParam("id") Long id, Model m,@RequestParam("id_dem") Long id_dem) {
		System.out.print(id);
		Citoyen c=citservice.getCitoyenById(id) ;
		Demande d=demService.getDemandeById(id_dem);
		m.addAttribute("citoyen",c);
		m.addAttribute("demande",d);

		return "/Modifi_foc";
		}
    
    
    
    @GetMapping("/modif_info")
    public String modif_info(@RequestParam("id") Long id, Model m, @RequestParam("demande") Long demande) {
        Citoyen c = citservice.getCitoyenById(id);
        m.addAttribute("citoyen", c);
        m.addAttribute("demande", demande);

        return "modif_info";
    }
    
    
    @PostMapping("/update_info")
    public String update_info(
            @RequestParam("id") Long id,
            Model m,
            @RequestParam("adresse") String adresse,
            @RequestParam("etat") String etat,
            @RequestParam("id_demande") Long id_demande,
            HttpSession s
    ) {
        email e;
        citservice.update_inf(adresse, etat, id);
        Citoyen c1 = citservice.getCitoyenById(id);
        citservice.update_etat_cit(id, id_demande);
        m.addAttribute("citoyen", c1);
        String email1 = citservice.emailCit(id_demande);

        System.out.print(email1);
        email.sendMail(email1);
        Long id1 = (Long) s.getAttribute("id_fonct");
        doc.update_inf_fonct(id1, id_demande);
        return "modif_info";
    }

    @GetMapping("/dem_sg")
    public String homeSg(@RequestParam("id") Long id, Model m,@RequestParam("id_dem") Long id_dem ) {
    	System.out.print(id);
    	Citoyen c=citservice.getCitoyenById(id) ;
    	Demande d=demService.getDemandeById(id_dem);
    	m.addAttribute("citoyen",c);
    	m.addAttribute("demande",d);
    	
    	
    	return "DemSignature.html";
    	}



    @PostMapping("/envoi")
    public String UpdateDocument(Model m,@RequestParam("pdfFile") String cont , Long id ,HttpSession session,@RequestParam("id_dem") Long id_dem ,@RequestParam("id_cit") Long id_cit) {
    	Long idF = (Long) session.getAttribute("id_foc");
    	System.out.print("foooooooooooooooooooooooooooc"+idF);
    	System.out.print("foooooooooooooooooooooooooooc"+cont);
    	Demande d=demService.getDemandeById(id_dem);
    	Citoyen c=citservice.getCitoyenById(id_cit) ;
    	String email=citservice.emailCit(id_dem);
    	
    	email e;
    	com.example.demo.controller.email.sendMail(email);
    	m.addAttribute("demande",d);
    	m.addAttribute("citoyen",c);
    	demService.update_citoy(id_cit, id_dem);
    	doc.update_doc(idF,id_dem);	
    	return "DemSignature.html";
    }

}
