package com.derteuffel.archives.repositories;

import com.derteuffel.archives.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte,Long> {

    Compte findByUsernameOrEmail(String username, String email);
    Compte findByUser_Id(Long id);

    List<Compte> findAllBySecteur_IdOrderByIdDesc(Long id);
    List<Compte> findAllByDirection_IdOrderByIdDesc(Long id);
}
