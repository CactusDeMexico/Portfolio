package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Spot;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotService {
    public List<Spot> findAllSpot();
    public List<Spot> findByIdtopo(@Param("idtopo") int idtopo);
    public List<Spot> findByName(@Param("nom") String nom);

}
