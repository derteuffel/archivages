package com.derteuffel.archives.controllers;

import com.derteuffel.archives.entities.Archive;
import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Role;
import com.derteuffel.archives.entities.User;
import com.derteuffel.archives.helpers.UserDto;
import com.derteuffel.archives.repositories.CompteRepository;
import com.derteuffel.archives.repositories.RoleRepository;
import com.derteuffel.archives.repositories.UserRepository;
import com.derteuffel.archives.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/archive/user")
public class UserController {

    @Autowired
    private CompteService compteService;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/lists")
    public String findAll(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        List<User> users = userRepository.findAll();
        model.addAttribute("lists", users);
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("compte",compte);
        return "user/lists";
    }
    @GetMapping("/detail/{id}")
    public String userDetail(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        User user = userRepository.getOne(id);
        Compte userAccount = compteRepository.findByUser_Id(user.getId());
        model.addAttribute("compte",compte);
        model.addAttribute("user",user);
        model.addAttribute("userAccount",userAccount);
        return "user/detail";
    }

    @GetMapping("/change/role/{id}")
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
        return "redirect:/archive/user/detail/"+compte.getUser().getId();

    }

    @PostMapping("/save")
    public String saveUser(UserDto userDto, RedirectAttributes redirectAttributes){
        Compte compte = compteService.findByUsernameOrEmail(userDto.getEmail(),userDto.getEmail());
        if (compte!= null){
            redirectAttributes.addFlashAttribute("error","Un utilisateur existe deja avec cet adresse email, veuillez essayer avec une autre adresse email");
            return "redirect:/archive/user/lists";
        }else {
            compteService.save(userDto);
            redirectAttributes.addFlashAttribute("success", "Enregistrement effectuer avec succes");
            return "redirect:/archive/user/lists";
        }
    }

    @GetMapping("/update/{id}")
    public String archiveDetail(@PathVariable Long id, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
        User user = userRepository.getOne(id);
        model.addAttribute("compte",compte);
        model.addAttribute("user",user);
        return "user/update";
    }

    @PostMapping("/update")
    public String updateUser(User user,RedirectAttributes redirectAttributes){
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Modification reussi");
        return "redirect:/archive/user/lists";
    }

    @GetMapping("/delete/{id}")
    public String userDelete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        User user = userRepository.getOne(id);
        userRepository.delete(user);
        redirectAttributes.addFlashAttribute("success","Suppression reussie");
        return "redirect:/archive/user/lists";
    }


}
