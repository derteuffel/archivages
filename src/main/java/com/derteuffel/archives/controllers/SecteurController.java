package com.derteuffel.archives.controllers;

import com.derteuffel.archives.entities.*;
import com.derteuffel.archives.enums.ERole;
import com.derteuffel.archives.helpers.CountryDetails;
import com.derteuffel.archives.helpers.UserDto;
import com.derteuffel.archives.repositories.ArchiveRepository;
import com.derteuffel.archives.repositories.CompteRepository;
import com.derteuffel.archives.repositories.RoleRepository;
import com.derteuffel.archives.repositories.UserRepository;
import com.derteuffel.archives.services.CompteService;
import com.derteuffel.archives.services.DirectionService;
import com.derteuffel.archives.services.Multipart;
import com.derteuffel.archives.services.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/secteur")
public class SecteurController {

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${back-end-base}")
    private String backeEndBase;

    CountryDetails countryDetails = new CountryDetails();

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private Multipart multipart;


    @PostMapping("/save/{id}")
    public String save(Secteur secteur, @PathVariable Long id) {
        secteurService.save(secteur, id);

        return "redirect:/secteur/lists/"+id;
    }

    @GetMapping("/update/{id}")
    public String addUpdate(Model model, @PathVariable Long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Secteur secteur = secteurService.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("secteur", secteur);
        model.addAttribute("villes",countryDetails.getVilles());
        return "secteur/update";
    }

    @PostMapping("/update/{id}")
    public String update(Secteur secteur, @PathVariable Long id) {
        secteurService.update(secteur, id);
        return "redirect:/secteur/detail/"+id;
    }

    @GetMapping("/detail/{id}")
    public String getOne(@PathVariable Long id, HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Secteur secteur = secteurService.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("secteur", secteur);
        return "secteur/detail";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Direction direction = secteurService.getOne(id).getDirection();
        secteurService.delete(id);
        return "redirect:/secteur/lists/"+direction.getId();
    }

    @GetMapping("/lists")
    public String findAll(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("lists",secteurService.findAll());
        return "secteur/lists";
    }

    @GetMapping("/lists/{id}")
    public String findAllByDirection(@PathVariable Long id, HttpServletRequest request, Model model) {

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Direction direction = directionService.getOne(id);
        model.addAttribute("compte", compte);
        model.addAttribute("direction",direction);
        model.addAttribute("lists",secteurService.findAllByDirection(id));
        model.addAttribute("secteur", new Secteur());
        model.addAttribute("villes",countryDetails.getVilles());
        return "secteur/lists";
    }

    @GetMapping("/ville/lists/{ville}")
    public String findAllByVille(@PathVariable String ville, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        model.addAttribute("compte", compte);
        model.addAttribute("lists",secteurService.findAllByVille(ville));
        model.addAttribute("secteur",new Secteur());
        model.addAttribute("villes",countryDetails.getVilles());
        return "secteur/lists";
    }

    @GetMapping("/archive/lists/{id}")
    public String archiveSearchByAge(Model model, HttpServletRequest request,@PathVariable Long id){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Role role = roleRepository.findByName(ERole.ROLE_USER.toString());
        List<Archive> lists = new ArrayList<>();

        if (compte.getRoles().contains(role)){
            lists.addAll(archiveRepository.findAllByCompte_Id(compte.getId()));
        }else {
            lists.addAll(archiveRepository.findAllBySecteur_IdOrderByIdDesc(id));
        }

        Secteur secteur = secteurService.getOne(id);
        model.addAttribute("lists",lists);
        model.addAttribute("compte",compte);
        model.addAttribute("secteur",secteur);
        model.addAttribute("archive",new Archive());
        model.addAttribute("departements",countryDetails.getDivisions());
        return "secteur/archives/archives";
    }


    @PostMapping("/archive/save/{id}")
    public String archiveSave(Archive archive, RedirectAttributes redirectAttributes, HttpServletRequest request,
                              @RequestParam("files") MultipartFile[] files, @PathVariable Long id){

        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Secteur secteur = secteurService.getOne(id);
        if (files.length != 0){
            for (MultipartFile file : files){
                multipart.store(file);
                if (archive.getPieces().size() == 0){
                    archive.setPieces(new ArrayList<String>(Collections.singleton(backeEndBase+"/downloadFile/" + file.getOriginalFilename())));
                }else {
                    archive.getPieces().add(backeEndBase+"/downloadFile/"+file.getOriginalFilename());
                }
            }
        }

        archive.setCode("#"+archive.getNatureDocument().substring(0,2).toUpperCase()+"/"+archive.getService().substring(0,3).toUpperCase()+"/"+archiveRepository.findAll().size());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        archive.setDateEnregistrement(sdf.format(date));
        archive.setCompte(compte);
        archive.setSecteur(secteur);
        archiveRepository.save(archive);
        redirectAttributes.addFlashAttribute("success","Operation reussie");
        return "redirect:/secteur/archive/lists/"+id;
    }
    @GetMapping("/archive/detail/{id}")
    public String archiveDetail(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Archive archive = archiveRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",archive);
        return "secteur/archives/detail";
    }

    @GetMapping("/archive/update/{id}")
    public String archiveUpdate(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Archive archive = archiveRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("archive",archive);
        model.addAttribute("departements",countryDetails.getDivisions());
        return "secteur/archives/update";
    }

    @GetMapping("/archive/delete/{id}")
    public String archiveDelete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Archive archive = archiveRepository.getOne(id);
        Secteur secteur = archive.getSecteur();
        archiveRepository.delete(archive);
        redirectAttributes.addFlashAttribute("success","Suppression reussie");
        return "redirect:/secteur/archive/lists/"+secteur.getId();
    }



    @GetMapping("/user/lists/{id}")
    public String findAllUser(HttpServletRequest request, Model model, @PathVariable Long id){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        Secteur secteur = secteurService.getOne(id);
        List<User> users = new ArrayList<>();
                List<Compte> comptes = compteService.findAllBySecteur(secteur.getId());
                for(Compte item: comptes){
                    users.add(item.getUser());
                }
        model.addAttribute("lists", users);
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("secteur",secteur);
        model.addAttribute("compte",compte);
        return "secteur/user/lists";
    }
    @GetMapping("/user/detail/{id}")
    public String userDetail(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        User user = userRepository.getOne(id);
        Compte userAccount = compteRepository.findByUser_Id(user.getId());
        System.out.println(userAccount.getSecteur().getLocation());
        model.addAttribute("compte",compte);
        model.addAttribute("user",user);
        model.addAttribute("userAccount",userAccount);
        return "secteur/user/detail";
    }

    @GetMapping("/user/change/role/{id}")
    public String changeRole(@PathVariable Long id,String role,RedirectAttributes redirectAttributes){
        Compte compte = compteRepository.getOne(id);
        Role role1 = roleRepository.findByName(role.toString());
        compte.getRoles().clear();
        if (role1 != null) {
            compte.getRoles().add(role1);
        }else {
            Role role2 = new Role();
            role2.setName(role.toString());
            roleRepository.save(role2);
            compte.getRoles().add(role2);
        }
        compteRepository.save(compte);
        redirectAttributes.addFlashAttribute("success","Vous avez changer le role de ce compte avec succes");
        return "redirect:/secteur/user/detail/"+compte.getUser().getId();

    }

    @PostMapping("/user/save/{id}")
    public String saveUser(UserDto userDto, RedirectAttributes redirectAttributes, @PathVariable Long id){
        Compte compte = compteService.findByUsernameOrEmail(userDto.getEmail(),userDto.getEmail());
        Secteur secteur = secteurService.getOne(id);
        if (compte!= null){
            redirectAttributes.addFlashAttribute("error","Un utilisateur existe deja avec cet adresse email, veuillez essayer avec une autre adresse email");
            return "redirect:/secteur/user/lists/"+secteur.getId();
        }else {
            userDto.setId(secteur.getId());
            compteService.save(userDto);
            redirectAttributes.addFlashAttribute("success", "Enregistrement effectuer avec succes");
            return "redirect:/secteur/user/lists/"+secteur.getId();
        }
    }

    @GetMapping("/user/update/{id}")
    public String userForm(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        User user = userRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("user",user);
        return "secteur/user/update";
    }

    @PostMapping("/user/update")
    public String updateUser(User user,RedirectAttributes redirectAttributes){
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Modification reussi");
        return "redirect:/secteur/user/lists";
    }

    @GetMapping("/user/delete/{id}")
    public String userDelete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        User user = userRepository.getOne(id);
        userRepository.delete(user);
        redirectAttributes.addFlashAttribute("success","Suppression reussie");
        return "redirect:/secteur/user/lists";
    }
}
