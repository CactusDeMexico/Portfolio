package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Spot;
import com.pancarte.climb.demo.repository.SpotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spotService")
public class SpotServiceImpl  implements SpotService{
    @Qualifier("spotRepository")
    @Autowired
    private SpotRepository spotRepository;
    public List<Spot> findByName(@Param("nom") String nom){
        return  spotRepository.findByName(nom);
    }
    @Override
    public List<Spot> findAllSpot() {
        return spotRepository.findAllSpot();
    }
    @Override
    public List<Spot> findByIdtopo(@Param("idtopo") int idtopo) {

        return spotRepository.findByIdtopo(idtopo);
    }
}
