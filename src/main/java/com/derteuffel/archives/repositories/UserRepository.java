package com.derteuffel.archives.repositories;

import com.derteuffel.archives.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByCompte_Id(Long id);
}
