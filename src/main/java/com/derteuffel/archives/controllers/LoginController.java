package com.derteuffel.archives.controllers;

import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Role;
import com.derteuffel.archives.entities.User;
import com.derteuffel.archives.enums.ERole;
import com.derteuffel.archives.helpers.UserDto;
import com.derteuffel.archives.repositories.CompteRepository;
import com.derteuffel.archives.repositories.RoleRepository;
import com.derteuffel.archives.repositories.UserRepository;
import com.derteuffel.archives.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/archive")
public class LoginController {


    @Autowired
    private CompteService compteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Value("${file.upload-dir}")
    private  String fileStorage;


    @ModelAttribute("compte")
    public UserDto compteRegistrationDto(){
        return new UserDto();
    }

    @GetMapping("/registration")
    public String registrationForm(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration( UserDto userDto, RedirectAttributes redirectAttributes){
        Compte compte = compteService.findByUsernameOrEmail(userDto.getUsername(),userDto.getEmail());

        Role role = roleRepository.findByName(ERole.ROLE_ROOT.toString());


        if (compte != null){
            redirectAttributes.addFlashAttribute("error","There are existing account with the provided username or email, try to connect to your account");
            return "redirect:/archive/login";
        }else {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setFirstname(userDto.getFirstname());
            user.setFonction("Administrateur du site");
            user.setLastname(userDto.getLastname());
            user.setMatricule("000001");
            user.setSexe(userDto.getSexe());
            user.setTelephone(userDto.getTelephone());
            userRepository.save(user);
            Compte compte1 = new Compte();
            compte1.setEmail(userDto.getEmail());
            compte1.setPassword(passwordEncoder.encode(userDto.getPassword()));
            compte1.setUsername(userDto.getUsername());
            compte1.setUser(user);
            if (role!= null) {
                compte1.setRoles(Arrays.asList(role));
            }else {
                Role role1 = new Role();
                role1.setName(ERole.ROLE_ROOT.toString());
                roleRepository.save(role1);
                compte1.setRoles(Arrays.asList(role1));
            }

            compteRepository.save(compte1);

            redirectAttributes.addFlashAttribute("success","You registered successfully");
            return "redirect:/archive/login";

        }
    }
}
