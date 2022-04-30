package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.*;
import com.pancarte.climb.demo.repository.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("topoService")
public class TopoServiceImpl implements TopoService {



    private final TopoRepository topoRepository;
    private final PublicationRepository publicationRepository;
    private final SpotRepository spotRepository;
    private final SecteurRepository secteurRepository;
    private final WayRepository wayRepository;

    private final UserRepository userRepository;
    private final CommentaireRepository commentaireRepository;

    private final RentRepository rentRepository;

    private final ProprietaireRepository proprietaireRepository;

    @Autowired
    public TopoServiceImpl(@Qualifier("topoRepository") TopoRepository topoRepository, @Qualifier("publicationRepository") PublicationRepository publicationRepository, @Qualifier("spotRepository") SpotRepository spotRepository, SecteurRepository secteurRepository, @Qualifier("wayRepository") WayRepository wayRepository, @Qualifier("userRepository") UserRepository userRepository, @Qualifier("commentaireRepository") CommentaireRepository commentaireRepository, @Qualifier("rentRepository") RentRepository rentRepository, @Qualifier("proprietaireRepository") ProprietaireRepository proprietaireRepository) {
        this.topoRepository = topoRepository;
        this.publicationRepository = publicationRepository;
        this.spotRepository = spotRepository;
        this.secteurRepository = secteurRepository;
        this.wayRepository = wayRepository;
        this.userRepository = userRepository;
        this.commentaireRepository = commentaireRepository;
        this.rentRepository = rentRepository;
        this.proprietaireRepository = proprietaireRepository;
    }

    @Override
    public void rentTopo(Rent rent) {
        rentRepository.save(rent);

    }
    @Override
    public List<Topo> findAllTopo() {

        return topoRepository.findAllTopo();
    }
    @Override
    public List<Proprietaire> findOwner(@Param("idtopo") int idtopo){
        return topoRepository.findOwner(idtopo);
    }


    @Override
    public List<Topo> findByLieu(@Param("nom") String nom){
        return topoRepository.findByLieu(nom);
    }

    @Override
    public Topo findOneById(@Param("idtopo") int idtopo){
        return topoRepository. findOneById(idtopo);
    }

    @Override
    public void borrow(Rent rent) {

        rentRepository.save(rent);
    }

    @Override
    public List<Topo> findById(@Param("idtopo") int idtopo) {

        return topoRepository.findById(idtopo);
    }

    @Override
    public void saveCommentaire(Commentaire commentaire) {
        commentaireRepository.save(commentaire);
    }

    @Override
    public void savePublication(Publication publication, Topo topo, Spot spot, Secteur secteur, Way way, int IdUser, String imgSpot, String imgSecteur) {
        publication.setIduser(IdUser);
        //INSERTION PUBLICATION ____________________________________
        Date now = Date.valueOf(LocalDate.now());
        publication.setCreationdate(now);
        publication.setUpdatedate(now);
        publicationRepository.save(publication);
        //INSERTION TOPO ____________________________________
        User userProprio = userRepository.findById(IdUser);
        topo.setUsers(new HashSet<User>(Arrays.asList(userProprio)));
        topoRepository.save(topo);

        Rent rent = new Rent();
        rent.setSeen(true);
        rent.setBorrow(false);
        rent.setReturnDate(now);
        rent.setIdtopo(topo.getIdtopo());
        rent.setIsloan(false);
        rent.setCreationDate(now);
        rent.setIduser(IdUser);
        rentRepository.save(rent);

        //INSERTION spot
        spot.setIdtopo(topoRepository.selectLastIdTopo());
        spot.setIdpublication(publicationRepository.selectLastIdPublication());
        spot.setLienSpot(imgSpot);
        spotRepository.save(spot);

        secteur.setLien(imgSecteur);
        secteur.setIdspot(spotRepository.selectLastIdspot());
        secteur.setIdpublication(publicationRepository.selectLastIdPublication());
        secteurRepository.save(secteur);

        way.setIdsecteur(secteurRepository.selectLastIdSecteur());

        wayRepository.save(way);

    }
}
