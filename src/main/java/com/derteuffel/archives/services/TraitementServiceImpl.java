package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Archive;
import com.derteuffel.archives.entities.Compte;
import com.derteuffel.archives.entities.Traitement;
import com.derteuffel.archives.entities.User;
import com.derteuffel.archives.enums.EStatus;
import com.derteuffel.archives.repositories.ArchiveRepository;
import com.derteuffel.archives.repositories.TraitemenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraitementServiceImpl implements TraitementService{

    @Autowired
    private TraitemenRepository traitemenRepository;

    @Autowired
    private ArchiveRepository archiveRepository;


    @Override
    public Traitement save(Traitement traitement) {
        Traitement savedTraitement = traitemenRepository.save(traitement);
        System.out.println("Dans le service: "+traitement.getId());
        return savedTraitement;
    }

    @Override
    public Traitement update(Traitement traitement, Long id) {
        Traitement existedTraitement = traitemenRepository.getOne(id);
        existedTraitement.setTask(traitement.getTask());
        existedTraitement.setTitle(traitement.getTitle());
        return traitemenRepository.save(existedTraitement);
    }

    @Override
    public Traitement changeUser(Long id, Compte compte) {
        Traitement traitement = traitemenRepository.getOne(id);
        traitement.setCompte(compte);
        return traitemenRepository.save(traitement);
    }

    @Override
    public void delete(Long id) {
        traitemenRepository.deleteById(id);
    }

    @Override
    public Traitement findOne(Long id) {
        return traitemenRepository.getOne(id);
    }

    @Override
    public List<Traitement> findAll() {
        return traitemenRepository.findAll();
    }

    @Override
    public List<Traitement> findAllByArchive(Long id) {
        return traitemenRepository.findAllByArchive_Id(id);
    }

    @Override
    public List<Traitement> findAllbyCompte(Long id) {
        return traitemenRepository.findAllByCompte_Id(id);
    }


}
