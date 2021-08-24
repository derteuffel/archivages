package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Direction;

import java.util.List;

public interface DirectionService {

    Direction save(Direction direction);
    Direction update(Direction direction, Long id);
    Direction getOne(Long id);
    void delete(Long id);
    List<Direction> findAll();
    List<Direction> findAllByRegion(String region);
    List<Direction> findAllByVille(String ville);
}
