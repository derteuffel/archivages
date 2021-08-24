package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.User;
import com.derteuffel.archives.helpers.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CompteService extends UserDetailsService {

    Compte findByUsernameOrEmail(String username, String email);
    Compte save(UserDto userDto);

    List<Compte> findAllBySecteur(Long id);
    List<Compte> findAllByDIrection(Long id);
}
