package com.pancarte.climb.demo.controller;

import com.pancarte.climb.demo.model.*;
import com.pancarte.climb.demo.repository.*;
import com.pancarte.climb.demo.service.PublicationService;
import com.pancarte.climb.demo.service.SpotService;
import com.pancarte.climb.demo.service.TopoService;
import com.pancarte.climb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final TopoRepository topoRepository;
    private final TopoService topoService;
    private final SpotService spotService;
    private final ProprietaireRepository proprietaireRepository;
    private final PublicationService publicationService;
    private final SecteurRepository secteurRepository;
    private final WayRepository wayRepository;
    private final CommentaireRepository commentaireRepository;
    private final RentRepository rentRepository;

    @Autowired
    public UserController(@Qualifier("proprietaireRepository") ProprietaireRepository proprietaireRepository, @Qualifier("topoRepository") TopoRepository topoRepository, @Qualifier("commentaireRepository") CommentaireRepository commentaireRepository, UserService userService, TopoService topoService, SpotService spotService, PublicationService publicationService, SecteurRepository secteurRepository, @Qualifier("wayRepository") WayRepository wayRepository, @Qualifier("rentRepository") RentRepository rentRepository) {
        this.commentaireRepository = commentaireRepository;
        this.topoRepository = topoRepository;
        this.userService = userService;
        this.topoService = topoService;
        this.spotService = spotService;
        this.publicationService = publicationService;
        this.secteurRepository = secteurRepository;
        this.wayRepository = wayRepository;
        this.rentRepository = rentRepository;
        this.proprietaireRepository = proprietaireRepository;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.addObject("view", "login");
        model.addObject("userName", "0");
        //model.setViewName("user/login");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        // model.setViewName("user/signup");
        model.addObject("view", "signup");
        model.setViewName("index");

        model.addObject("userName", "0");

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "L'email existe déja");
        }
        if (bindingResult.hasErrors()) {
            // model.setViewName("user/signup");
            model.addObject("view", "signup");
            model.setViewName("index");
        } else {
            userService.saveUser(user);
            model.addObject("msg", "L'utilisateur à été enregistré");
            model.addObject("user", new User());

            model.addObject("userName", "0");

            //model.setViewName("user/signup");
            model.addObject("view", "login");
            model.setViewName("index");

        }

        return model;
    }

    @RequestMapping(value = {"/logged"}, method = RequestMethod.GET)
    public String logged() {


        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/loggedhome"}, method = RequestMethod.GET)
    public ModelAndView loggedHome() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Rent> rent = rentRepository.findAll();

        if (rent.size() > 0) {
            model.addObject("rent", rent);
        } else {
            Rent dummy = new Rent();
            java.sql.Date now = java.sql.Date.valueOf(LocalDate.now());
            dummy.setCreationDate(now);
            dummy.setReturnDate(now);
            dummy.setIsloan(false);
            dummy.setIdtopo(0);
            dummy.setIduser(0);
            rent.add(dummy);
            model.addObject("rent", rent);
        }

        List<Publication> publication = publicationService.findByIdUser(user.getId());

        List<Topo> topos = topoService.findAllTopo();
        List<Secteur> secteurs = secteurRepository.findAll();
        List<Spot> spots = spotService.findAllSpot();
        List<Way> ways = wayRepository.findAll();
        int nb = 0;
        List<Proprietaire> proprietaires = proprietaireRepository.findAllPro();
        for (Proprietaire proprietaire : proprietaires) {
            if (proprietaire.getIduser() != user.getId()) {
                nb++;
            }
        }
        model.addObject("owner", false);
        if (nb == proprietaires.size()) {
            model.addObject("owner", true);
        }
        String lastSpotDescription = "";
        String lastSpotName = "";
        String lastSpotlink = "";
        String secondSpotDescription = "";
        String secondSpotName = "";
        String secondSpotlink = "";
        String thridSpotDescription = "";
        String thridSpotName = "";
        String thridSpotlink = "";

        for (Spot spot : spots) {
            if (spots.indexOf(spot) == spots.size() - 1) {
                lastSpotName = spot.getNomSpot();
                lastSpotDescription = spot.getDescription();
                lastSpotlink = spot.getLienSpot();
            }
            if (spots.indexOf(spot) == spots.size() - 2) {
                secondSpotName = spot.getNomSpot();
                secondSpotDescription = spot.getDescription();
                secondSpotlink = spot.getLienSpot();
            }
            if (spots.indexOf(spot) == spots.size() - 3) {
                thridSpotName = spot.getNomSpot();
                thridSpotDescription = spot.getDescription();
                thridSpotlink = spot.getLienSpot();
            }
        }
        String Topo = "";
        String topoSelec = "";
        Spot selectedTopo = new Spot();
        List<Spot> topoList = new ArrayList();
        for (Topo topo : topos) {
            if (!topo.isHidden()) {
                selectedTopo = new Spot();
                for (Spot spot : spots) {
                    if (topo.getIdtopo() == spot.getIdtopo()) {
                        Topo = topo.getLieuTopo();
                        if (selectedTopo.getNomSpot()==null) {
                            selectedTopo = spots.get(spots.indexOf(spot));
                        }

                        if (spots.indexOf(spot) + 1 < spots.size()) {
                            Spot test = spots.get(spots.indexOf(spot) + 1);
                            Spot test2 = spots.get(spots.indexOf(spot) + 1);

                            if (test.getIdtopo() != (spot.getIdtopo())) {

                            }
                        }
                    }
                }
                topoSelec += Topo + " ";
                topoList.add(selectedTopo);
            }
        }




        model.addObject("selectedTopo", topoList);
        model.addObject("lastSpotName", lastSpotName);
        model.addObject("lastSpotDescription", lastSpotDescription);
        model.addObject("lastSpotlink", lastSpotlink);
        model.addObject("secondSpotName", secondSpotName);
        model.addObject("secondSpotDescription", secondSpotDescription);
        model.addObject("secondSpotlink", secondSpotlink);
        model.addObject("thridSpotName", thridSpotName);
        model.addObject("thridSpotDescription", thridSpotDescription);
        model.addObject("thridSpotlink", thridSpotlink);
        Commentaire commentaires = new Commentaire();
        model.addObject("UserId", user.getId());

        model.addObject("publication", publication);
        model.addObject("spot", spots);
        model.addObject("topo", topos);
        model.addObject("secteur", secteurs);
        model.addObject("way", ways);
        model.addObject("commentaire", commentaires);
        model.addObject("userName", user.getName() + " " + user.getLastname());

        //model.setViewName("home/loggedHome");
        model.addObject("view", "loggedhome");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }

    @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/error");
        return model;
    }
}
