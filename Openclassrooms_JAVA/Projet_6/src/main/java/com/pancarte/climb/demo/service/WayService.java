package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Way;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WayService {

    Way findAllByIdWay(@Param("idvoie") int idvoie);
}
