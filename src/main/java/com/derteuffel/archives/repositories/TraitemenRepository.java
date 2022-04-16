package com.derteuffel.archives.repositories;

import org.springframework.stereotype.Repository;

import com.derteuffel.archives.entities.Traitement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface TraitemenRepository extends JpaRepository<Traitement, Long>{
	
	List<Traitement> findAllByArchive_Id(Long id);
	List<Traitement> findAllByCompte_Id(Long id);

}
