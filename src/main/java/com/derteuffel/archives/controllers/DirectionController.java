package com.derteuffel.archives.controllers;

import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Direction;
import com.derteuffel.archives.entities.Secteur;
import com.derteuffel.archives.helpers.CountryDetails;
import com.derteuffel.archives.services.CompteService;
import com.derteuffel.archives.services.DirectionService;
import com.derteuffel.archives.services.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private CompteService compteService;

    CountryDetails countryDetails = new CountryDetails();


    @PostMapping("/save")
    public String save(Direction direction) {
        directionService.save(direction);

        return "redirect:/direction/lists";
    }

    @GetMapping("/update/{id}")
    public String addUpdate(Model model, @PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Direction direction = directionService.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("direction", direction);
        model.addAttribute("villes",countryDetails.getVilles());
        model.addAttribute("regions", countryDetails.getRegions());
        return "direction/update";
    }

    @PostMapping("/update/{id}")
    public String update(Direction direction, @PathVariable Long id) {
        directionService.update(direction, id);
        return "redirect:/direction/detail/"+id;
    }

    @GetMapping("/detail/{id}")
    public String getOne(@PathVariable Long id, HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Direction direction = directionService.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("direction", direction);
        return "direction/detail";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        directionService.delete(id);
        return "redirect:/direction/lists";
    }

    @GetMapping("/lists")
    public String findAll(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("lists",directionService.findAll());
        model.addAttribute("direction",new Direction());
        model.addAttribute("villes",countryDetails.getVilles());
        model.addAttribute("regions", countryDetails.getRegions());
        return "direction/lists";
    }



    @GetMapping("/ville/lists/{ville}")
    public String findAllByVille(@PathVariable String ville, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("lists",directionService.findAllByVille(ville));
        model.addAttribute("direction",new Direction());
        model.addAttribute("villes",countryDetails.getVilles());
        model.addAttribute("regions", countryDetails.getRegions());
        return "direction/lists";
    }
}
