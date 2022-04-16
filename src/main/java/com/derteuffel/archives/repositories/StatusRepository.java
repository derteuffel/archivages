package com.derteuffel.archives.repositories;

import com.derteuffel.archives.entities.Status;
import com.derteuffel.archives.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository  extends JpaRepository<Status, Long> {

    List<Status> findAllByTraitement_Id(Long id);
    List<Status> findAllByStatus(EStatus eStatus);
    List<Status> findAllByValidate(Boolean status);
}
