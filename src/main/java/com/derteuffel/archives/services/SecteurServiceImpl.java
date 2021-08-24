package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Direction;
import com.derteuffel.archives.entities.Secteur;
import com.derteuffel.archives.repositories.DirectionRepository;
import com.derteuffel.archives.repositories.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecteurServiceImpl implements SecteurService{

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private SecteurRepository secteurRepository;


    @Override
    public Secteur save(Secteur secteur, Long id) {

        Direction direction = directionRepository.getOne(id);
        secteur.setDirection(direction);
        return secteurRepository.save(secteur);
    }

    @Override
    public Secteur update(Secteur secteur, Long id) {
        Secteur existedSecteur = secteurRepository.getOne(id);
        existedSecteur.setLocation(secteur.getLocation());
        existedSecteur.setName(secteur.getName());
        existedSecteur.setVille(secteur.getVille());
        return secteurRepository.save(existedSecteur);
    }

    @Override
    public Secteur getOne(Long id) {
        return secteurRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
            secteurRepository.deleteById(id);
    }

    @Override
    public List<Secteur> findAll() {
        return secteurRepository.findAll();
    }

    @Override
    public List<Secteur> findAllByDirection(Long id) {
        return secteurRepository.findAllByDirection_IdOrderByIdDesc(id);
    }

    @Override
    public List<Secteur> findAllByVille(String ville) {
        return secteurRepository.findAllByVilleOrderByNameAsc(ville);
    }
}
