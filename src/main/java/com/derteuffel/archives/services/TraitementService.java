package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Traitement;
import com.derteuffel.archives.entities.User;
import com.derteuffel.archives.enums.EStatus;

import java.util.List;

public interface TraitementService {

    Traitement save(Traitement traitement);
    Traitement update(Traitement traitement, Long id);

    Traitement changeUser(Long id, Compte compte);

    void delete(Long id);
    Traitement findOne(Long id);
    List<Traitement> findAll();
    List<Traitement> findAllByArchive(Long id);
    List<Traitement> findAllbyCompte(Long id);
}
