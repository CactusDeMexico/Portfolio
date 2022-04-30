package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Way;
import com.pancarte.climb.demo.repository.WayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;



@Service("wayService")
public class WayServiceImpl implements WayService {

    private final WayRepository wayRepository;

    @Autowired
    public WayServiceImpl(@Qualifier("wayRepository") WayRepository wayRepository) {
        this.wayRepository = wayRepository;
    }

    @Override
    public Way findAllByIdWay(@Param("idvoie") int idvoie)
    {
        return wayRepository.findAllByIdWay(idvoie);
    }
}
