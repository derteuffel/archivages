package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Secteur;

import java.util.List;

public interface SecteurService {

    Secteur save(Secteur secteur, Long id);
    Secteur update(Secteur secteur, Long id);
    Secteur getOne(Long id);
    void delete(Long id);
    List<Secteur> findAll();
    List<Secteur> findAllByDirection(Long id);
    List<Secteur> findAllByVille(String ville);
}
