package com.derteuffel.archives.services;

import com.derteuffel.archives.entities.Direction;
import com.derteuffel.archives.repositories.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionServiceImpl implements DirectionService{

    @Autowired
    private DirectionRepository directionRepository;


    @Override
    public Direction save(Direction direction) {
        return directionRepository.save(direction);
    }

    @Override
    public Direction update(Direction direction, Long id) {
        Direction existedDirection = directionRepository.getOne(id);
        existedDirection.setLocation(direction.getLocation());
        existedDirection.setName(direction.getName());
        existedDirection.setRegion(direction.getRegion());
        existedDirection.setVille(direction.getVille());
        return directionRepository.save(existedDirection);
    }

    @Override
    public Direction getOne(Long id) {
        return directionRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        directionRepository.deleteById(id);
    }

    @Override
    public List<Direction> findAll() {
        return directionRepository.findAll();
    }

    @Override
    public List<Direction> findAllByRegion(String region) {
        return directionRepository.findAllByVilleOrderByNameAsc(region);
    }

    @Override
    public List<Direction> findAllByVille(String ville) {
        return directionRepository.findAllByVilleOrderByNameAsc(ville);
    }
}
