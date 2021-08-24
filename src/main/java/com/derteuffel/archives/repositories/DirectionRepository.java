package com.derteuffel.archives.repositories;

import com.derteuffel.archives.entities.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectionRepository extends JpaRepository<Direction,Long>{

    List<Direction> findAllByVilleOrderByNameAsc(String ville);
    List<Direction> findAllByRegionOrderByNameAsc(String region);
}
