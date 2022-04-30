package com.pancarte.climb.demo.service;
import com.pancarte.climb.demo.model.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopoService {
    public void rentTopo(Rent rent);
    public void borrow(Rent rent);
    public List<Topo> findAllTopo();


    public List<Topo> findById(@Param("idtopo") int idtopo);
    public  List<Topo> findByLieu(@Param("nom") String nom);

    public void saveCommentaire(Commentaire commentaire);

    Topo findOneById(@Param("idtopo") int idtopo);

    public void savePublication(Publication publication, Topo topo, Spot spot, Secteur secteur, Way way, int IdUser, String imgSpot, String imgSecteur);
    public List<Proprietaire> findOwner(@Param("idtopo") int idtopo);


}
