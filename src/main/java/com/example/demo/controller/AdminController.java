package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Administrateur;
import com.example.demo.entity.Commune;
import com.example.demo.entity.Demande;
import com.example.demo.entity.Fonctionnaire;
import com.example.demo.repository.AdministrateurRepository;
import com.example.demo.repository.CommuneReposotiry;
import com.example.demo.repository.citoyenRepository;
import com.example.demo.repository.demandeRepository;
import com.example.demo.repository.fonctionaireRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.CommuneService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	 @Autowired
	    private CommuneService communeService; 
	 @Autowired
	    private AdminService adminService;
    
	 @Autowired
	    private CommuneReposotiry communerepositry;
 
    @Autowired
    private AdministrateurRepository admin;
    @GetMapping("/loginAdmin")
    public String showLoginForm(Model model) {
        return "loginAdmin";
    }

    @RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
    public String login1(HttpServletRequest request, Model model,HttpSession session) {
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        
        Administrateur userEntity = admin.findByEmailAndPwd(email, pwd);
        session.setAttribute("id_admin", userEntity.getId());
        session.setAttribute("id_commune", userEntity.getCommune().getId());
        if (userEntity != null) {
            return "redirect:/Admin";
        } else{
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/loginAdmin";
        }
    }
    
    @Autowired
    private fonctionaireRepository fonctionnaireRepository;
 
    @Autowired
    private citoyenRepository citoyen ;
 
    // Affichage  des fonctionnaire 
    @GetMapping("/Admin")
    public String listFonctionnaires(Model model, HttpSession session) {
    	Long idCommune = (Long) session.getAttribute("id_commune");
    	 List<Fonctionnaire> fonctionnaires = fonctionnaireRepository.findByCommuneId(idCommune);
        model.addAttribute("fonctionnaires", fonctionnaires);
        model.addAttribute("fonctionnaire", new Fonctionnaire());  // Add this line
        return "Admin";
    }

    // Additioner
    @GetMapping("/add")
    public String addFonctionnaireForm(Model model) {
        model.addAttribute("fonctionnaire", new Fonctionnaire());
        return "Admin";
    }

    @PostMapping("/add")
    public String addFonctionnaire(@ModelAttribute Fonctionnaire fonctionnaire, HttpSession session) {
    	Long idCommune = (Long) session.getAttribute("id_commune");
    	Long id_admin = (Long) session.getAttribute("id_admin");
    	System.out.println(id_admin);
    	Commune commune =communeService.getCommuneById(idCommune);
    	
    	Administrateur ad=adminService.getAdminById(id_admin);
    	fonctionnaire.setAdministrateur(ad);
    	fonctionnaire.setCommune(commune);
        fonctionnaireRepository.save(fonctionnaire);
        return "redirect:/Admin";  // Redirige vers la liste des fonctionnaires
    }
    
    
    @GetMapping("/deleteFonctionnaire")
    public String deleteFonctionnaire(@RequestParam("id_foc") Long idFonctionnaire) {
        // Supprimez le fonctionnaire directement en utilisant le repository
        fonctionnaireRepository.deleteById(idFonctionnaire);

        // Redirigez l'utilisateur vers la page des fonctionnaires
        return "redirect:/Admin";
    }
    
    
    @GetMapping("/UpdateF")
    public String UpdateFonctionnaireForm(@RequestParam("id_foc") Long idFonctionnaire, Model model) {
        Optional<Fonctionnaire> existingFonctionnaireOptional = fonctionnaireRepository.findById(idFonctionnaire);

        if (existingFonctionnaireOptional.isPresent()) {
            Fonctionnaire existingFonctionnaire = existingFonctionnaireOptional.get();
            model.addAttribute("fonctionnaire", existingFonctionnaire);
        } else {
           
        }
        return "UpdateF";
    }
    
    @PostMapping("/edit")
    public String updateFonctionnaire(@ModelAttribute("fonctionnaire") Fonctionnaire updatedFonctionnaire) {
    	Long id = updatedFonctionnaire.getId_foc();
    	System.out.println("ID: " + id);
    	Optional<Fonctionnaire> existingFonctionnaireOptional = fonctionnaireRepository.findById(id);

        if (existingFonctionnaireOptional.isPresent()) {
            Fonctionnaire existingFonctionnaire = existingFonctionnaireOptional.get();
            existingFonctionnaire.setNom(updatedFonctionnaire.getNom());
            existingFonctionnaire.setPrenom(updatedFonctionnaire.getPrenom());
            existingFonctionnaire.setEmail(updatedFonctionnaire.getEmail());
            existingFonctionnaire.setPassword(updatedFonctionnaire.getPassword());
            System.out.println("Saving updated fonctionnaire");
            fonctionnaireRepository.save(existingFonctionnaire);
        } else {
          
        }

        return "redirect:/Admin";
}
    
 

}
