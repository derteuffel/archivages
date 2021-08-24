package com.derteuffel.archives.repositories;

import com.derteuffel.archives.entities.Archive;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive,Long> {
    List<Archive> findAllByCompte_Id(Long id);
    List<Archive> findAllByNatureDocument(String nature, Sort sort);
    List<Archive> findAllByAge(String age, Sort sort);
    List<Archive> findAllBySecteur_IdOrderByIdDesc(Long id);
}
