package com.derteuffel.archives.controllers;

import com.derteuffel.archives.entities.Archive;
import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Role;
import com.derteuffel.archives.helpers.CountryDetails;
import com.derteuffel.archives.repositories.ArchiveRepository;
import com.derteuffel.archives.repositories.CompteRepository;
import com.derteuffel.archives.repositories.RoleRepository;
import com.derteuffel.archives.repositories.UserRepository;
import com.derteuffel.archives.services.CompteService;
import com.derteuffel.archives.services.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/archive")
public class ArchiveController {
    @Autowired
    private CompteService compteService;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Multipart multipart;

    CountryDetails countryDetails = new CountryDetails();

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        List<Archive> lists = archiveRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Role role = roleRepository.findByName("ROLE_USER");
        model.addAttribute("lists",lists);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",new Archive());
        if (compte.getRoles().contains(role)){
            return "redirect:/archive/lists";
        }
        return "index";
    }

    @GetMapping("/lists")
    public String archives(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        List<Archive> lists = archiveRepository.findAllByCompte_Id(compte.getId());
        model.addAttribute("lists",lists);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",new Archive());
        model.addAttribute("departements",countryDetails.getDivisions());
        return "index";
    }

    @GetMapping("/lists/nature/{natureDocument}")
    public String archiveSearchByNature(Model model, HttpServletRequest request,@PathVariable String natureDocument){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        List<Archive> lists = archiveRepository.findAllByNatureDocument(natureDocument,Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("lists",lists);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",new Archive());
        model.addAttribute("departements",countryDetails.getDivisions());
        return "index";
    }

    @GetMapping("/lists/age/{age}")
    public String archiveSearchByAge(Model model, HttpServletRequest request,@PathVariable String age){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        List<Archive> lists = archiveRepository.findAllByAge(age,Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("lists",lists);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",new Archive());
        model.addAttribute("departements",countryDetails.getDivisions());
        return "index";
    }



    @PostMapping("/save")
    public String archiveSave(Archive archive, RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("files") MultipartFile[] files){

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        if (files.length != 0){
            for (MultipartFile file : files){
                multipart.store(file);
                if (archive.getPieces().size() == 0){
                    archive.setPieces(new ArrayList<String>(Collections.singleton("/downloadFile/" + file.getOriginalFilename())));
                }else {
                    archive.getPieces().add("/downloadFile/"+file.getOriginalFilename());
                }
            }
        }

        archive.setCode("#"+archive.getNatureDocument().substring(0,2).toUpperCase()+"/"+archive.getService().substring(0,3).toUpperCase()+"/"+archiveRepository.findAll().size());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        archive.setDateEnregistrement(sdf.format(date));
        archive.setCompte(compte);
        archiveRepository.save(archive);
        redirectAttributes.addFlashAttribute("success","Operation reussie");
        return "redirect:/archive/home";
    }

    @GetMapping("/detail/{id}")
    public String archiveDetail(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Archive archive = archiveRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",archive);
        return "archive/detail";
    }

    @GetMapping("/update/{id}")
    public String archiveUpdate(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Archive archive = archiveRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",archive);
        model.addAttribute("departements",countryDetails.getDivisions());
        return "archive/update";
    }

    @GetMapping("/delete/{id}")
    public String archiveDelete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Archive archive = archiveRepository.getOne(id);
        archiveRepository.delete(archive);
        redirectAttributes.addFlashAttribute("success","Suppression reussie");
        return "redirect:/archive/home";
    }
}
