package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Publication;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationService {
    public List<Publication> findByIdUser(@Param("iduser") Integer iduser);

    public List<Publication> findByName(@Param("name") String name);
    Publication findAllById(@Param("idpublication") Integer idpublication);
}
