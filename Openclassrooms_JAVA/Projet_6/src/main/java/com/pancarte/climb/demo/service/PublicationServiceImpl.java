package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.Publication;
import com.pancarte.climb.demo.repository.PublicationRepository;
import com.pancarte.climb.demo.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService {

    @Qualifier("publicationRepository")
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public List<Publication> findByIdUser(@Param("iduser") Integer iduser) {
        return publicationRepository.findByIdUser(iduser);
    }

    @Override
    public List<Publication> findByName(@Param("name") String name) {
        return publicationRepository.findByName(name);
    }

    @Override
    public Publication findAllById(@Param("idpublication") Integer idpublication){
        return publicationRepository.findAllById(idpublication);
    }
}
