package com.pancarte.climb.demo.controller;

import com.pancarte.climb.demo.model.*;
import com.pancarte.climb.demo.repository.*;
import com.pancarte.climb.demo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class TopoController {

    private final TopoService topoService;
    private final TopoRepository topoRepository;
    private final SpotService spotService;
    private final UserService userService;
    private final PublicationService publicationService;
    private final SecteurRepository secteurRepository;
    private final WayRepository wayRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final WayService wayService;
    private final SpotRepository spotRepository;
    private final CommentaireRepository commentaireRepository;
    private final RentRepository rentRepository;
    private final PublicationRepository publicationRepository;

    @Autowired
    public TopoController(TopoService topoService, @Qualifier("topoRepository") TopoRepository topoRepository, @Qualifier("publicationRepository") PublicationRepository publicationRepository, @Qualifier("spotRepository") SpotRepository spotRepository, SpotService spotService, UserService userService, WayService wayService, PublicationService publicationService, SecteurRepository secteurRepository, @Qualifier("wayRepository") WayRepository wayRepository, @Qualifier("commentaireRepository") CommentaireRepository commentaireRepository, @Qualifier("rentRepository") RentRepository rentRepository, @Qualifier("proprietaireRepository") ProprietaireRepository proprietaireRepository) {
        this.publicationRepository = publicationRepository;
        this.topoService = topoService;
        this.topoRepository = topoRepository;
        this.spotService = spotService;
        this.spotRepository = spotRepository;
        this.userService = userService;
        this.wayService = wayService;
        this.publicationService = publicationService;
        this.secteurRepository = secteurRepository;
        this.wayRepository = wayRepository;
        this.commentaireRepository = commentaireRepository;
        this.rentRepository = rentRepository;
        this.proprietaireRepository = proprietaireRepository;
    }

    @RequestMapping(value = {"/content"}, method = RequestMethod.GET)
    public ModelAndView content(@RequestParam("idtopo") int idtopo) {
        ModelAndView model = new ModelAndView();
        Rent rent = new Rent();
        User user = new User();
        Publication pub = new Publication();
        Topo topo = new Topo();
        Spot spot = new Spot();
        Secteur secteur = new Secteur();
        List<Way> way = wayRepository.findAll();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        List<Topo> topos = topoService.findAllTopo();
        String search = "";
        model.addObject("search", search);
        model.addObject("topos", topos);
        model.addObject("userName", user);
        model.addObject("publication", pub);
        model.addObject("topo", topo);
        model.addObject("spot", spot);
        model.addObject("secteur", secteur);
        model.addObject("way", way);
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("fragment/content");

        return model;
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        model.addObject("view", "home");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/borrow"}, method = RequestMethod.GET)
    public ModelAndView borrowTopo(@RequestParam("idtopo") int idtopo) {
        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Topo> topo = topoService.findById(idtopo);
        List<Spot> spot = spotService.findAllSpot();
        Rent rent = rentRepository.getOne(idtopo);

        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.addObject("topo", topo);
        model.addObject("spot", spot);
        model.addObject("rent", rent);
        model.addObject("view", "borrow");
        // model.setViewName("user/borrow");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/validateDele"}, method = RequestMethod.GET)
    public String CancelRent(@RequestParam("idtopo") int idtopo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Rent rent = rentRepository.getOne(idtopo);
        rent.setSeen(true);
        rent.setBorrow(false);
        Proprietaire proprietaire = proprietaireRepository.getOne(rent.getIdtopo());
        rent.setIduser(proprietaire.getIduser());
        rentRepository.save(rent);

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/borrow"}, method = RequestMethod.POST)
    public String borrowIt(@RequestParam("idtopo") int idtopo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Rent rent = rentRepository.getOne(idtopo);
        rent.setIsloan(true);
        rent.setIduser(user.getId());
        rent.setIdtopo(idtopo);
        rent.setBorrow(true);
        rentRepository.save(rent);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/borrowed"}, method = RequestMethod.GET)
    public ModelAndView borrowed() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Rent> rents = rentRepository.findAll();
        List<Proprietaire> proprietaires = proprietaireRepository.findAll();
        int nb = 0;
        for (Rent rent : rents) {
            if (!rent.isBorrow()) {
                nb++;
            }
        }
        model.addObject("location", false);
        if (nb == rents.size()) {
            model.addObject("location", true);
        }

        model.addObject("userName", user.getName() + " " + user.getLastname());
        List<Topo> topo = topoService.findAllTopo();
        List<Spot> spot = spotService.findAllSpot();
        model.addObject("proprietaire", proprietaires);
        model.addObject("user", user);
        model.addObject("topo", topo);
        model.addObject("spot", spot);
        model.addObject("rent", rents);
        model.addObject("view", "borrowed");
        // model.setViewName("user/borrowed");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/rent"}, method = RequestMethod.GET)
    public ModelAndView rentTopo(@RequestParam("idtopo") int idtopo) {
        ModelAndView model = new ModelAndView();
        Rent rent = new Rent();
        List<Topo> topo = topoService.findById(idtopo);
        List<Spot> spot = spotService.findAllSpot();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.addObject("topo", topo);
        model.addObject("spot", spot);
        model.addObject("rent", rent);
        model.addObject("view", "rent");
        //model.setViewName("user/rent");
        model.setViewName("index");

        return model;
    }

    @RequestMapping(value = {"/UpdateSpot"}, method = RequestMethod.GET)
    public ModelAndView updateSpot(@RequestParam("idspot") int idspot) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Spot spot = spotRepository.getOne(idspot);

        model.addObject("spot", spot);

        model.addObject("view", "updateSpot");
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/updateSpot"}, method = RequestMethod.POST)
    public String updateSpot(Spot spot) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Spot upSpot = spotRepository.getOne(spot.getIdspot());
        Date now = Date.valueOf(LocalDate.now());
        Publication publication = publicationService.findAllById(upSpot.getIdpublication());
        publication.setUpdatedate(now);
        publicationRepository.save(publication);
        spot.setLienSpot(upSpot.getLienSpot());
        spotRepository.save(spot);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/addspot"}, method = RequestMethod.GET)
    public ModelAndView addSpot(@RequestParam("idtopo") int idtopo, @RequestParam("idpublication") int idpublication, Spot spot, Secteur secteur, Way way) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        spot.setIdtopo(idtopo);
        spot.setIdpublication(idpublication);
        model.addObject("way", way);
        model.addObject("spot", spot);
        model.addObject("secteur", secteur);
        model.addObject("view", "addSpot");
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/addspot"}, method = RequestMethod.POST)
    public String addedSpot(@RequestParam("file") MultipartFile imgSpot, @RequestParam("fileSecteur") MultipartFile imgSecteur, @RequestParam("idtopo") int idtopo, Spot spot, Secteur secteur, Way way) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Path currentRelativePath = Paths.get("");
        String path = "\\src\\main\\resources\\static\\img\\";
        String s = (currentRelativePath.toAbsolutePath().toString()) + path;
        byte[] bytesSpot = imgSpot.getBytes();
        byte[] bytesSecteur = imgSecteur.getBytes();
        Random random = new Random();
        int numb;
        Path pathSpot = Paths.get(s + imgSpot.getOriginalFilename());
        Path pathSector = Paths.get(s + imgSecteur.getOriginalFilename());

        while (pathSpot.toFile().exists() || pathSector.toFile().exists() || pathSector == pathSpot) {
            numb = random.nextInt(10);

            if (pathSpot.toFile().exists()) {
                pathSpot = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
            if (pathSector.toFile().exists()) {
                pathSector = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
            if (pathSector == pathSpot) {
                pathSector = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
        }
        Date now = Date.valueOf(LocalDate.now());
        Publication publication = publicationService.findAllById(spot.getIdpublication());
        publication.setUpdatedate(now);
        publicationRepository.save(publication);

        Files.write(pathSpot, bytesSpot);
        Files.write(pathSector, bytesSecteur);
        spot.setLienSpot(imgSpot.getOriginalFilename());
        spotRepository.save(spot);
        secteur.setIdspot(spotRepository.selectLastIdspot());
        secteur.setLien(imgSecteur.getOriginalFilename());
        secteurRepository.save(secteur);
        way.setIdsecteur(secteurRepository.selectLastIdSecteur());
        wayRepository.save(way);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/updateSecteur"}, method = RequestMethod.GET)
    public ModelAndView updateSecteur(@RequestParam("idsecteur") int idsecteur) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        ModelAndView model = new ModelAndView();
        Secteur secteur = secteurRepository.getOne(idsecteur);

        model.addObject("secteur", secteur);
        model.addObject("view", "updateSecteur");
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/updateSecteur"}, method = RequestMethod.POST)
    public String updatedSecteur(Secteur secteur) {

        Secteur upsec = secteurRepository.getOne(secteur.getIdsecteur());
        Date now = Date.valueOf(LocalDate.now());
        Publication publication = publicationService.findAllById(upsec.getIdpublication());
        publication.setUpdatedate(now);
        publicationRepository.save(publication);
        secteur.setIdpublication(upsec.getIdpublication());
        secteur.setIdspot(upsec.getIdspot());
        secteur.setLien(upsec.getLien());
        secteurRepository.save(secteur);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/addSecteur"}, method = RequestMethod.GET)
    public ModelAndView addSecteur(@RequestParam("idpublication") int idpublication, @RequestParam("idspot") int idspot, Secteur secteur, Way way) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        ModelAndView model = new ModelAndView();
        secteur.setIdpublication(idpublication);
        secteur.setIdspot(idspot);

        model.addObject("way", way);
        model.addObject("secteur", secteur);
        model.addObject("view", "addSecteur");
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/addSecteur"}, method = RequestMethod.POST)
    public String addedSecteur(@RequestParam("fileSecteur") MultipartFile imgSecteur, Secteur secteur, Way way, BindingResult bindingResult) throws Exception {

        bindingResult.getTarget();
        Path currentRelativePath = Paths.get("");
        String path = "\\src\\main\\resources\\static\\img\\";
        String s = (currentRelativePath.toAbsolutePath().toString()) + path;
        byte[] bytesSecteur = imgSecteur.getBytes();
        Random random = new Random();
        int numb;
        Path pathSector = Paths.get(s + imgSecteur.getOriginalFilename());
        while (pathSector.toFile().exists()) {
            numb = random.nextInt(10);

            if (pathSector.toFile().exists()) {
                pathSector = Paths.get(s + numb + imgSecteur.getOriginalFilename());
            }
        }
        Files.write(pathSector, bytesSecteur);
        secteur.setLien(imgSecteur.getOriginalFilename());
        secteurRepository.save(secteur);
        way.setIdsecteur(secteurRepository.selectLastIdSecteur());
        wayRepository.save(way);

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/updateVoie"}, method = RequestMethod.GET)
    public ModelAndView updateVoie(@RequestParam("idsecteur") int idsecteur, @RequestParam("idway") int idway) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Way way = wayService.findAllByIdWay(idway);
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.addObject("view", "updateVoie");
        model.addObject("way", way);
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/updateVoie"}, method = RequestMethod.POST)
    public String updatedVoie(Way way) {

        wayRepository.save(way);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/addVoie"}, method = RequestMethod.GET)
    public ModelAndView addVoie(@RequestParam("idsecteur") int idsecteur, Way way) {
        ModelAndView model = new ModelAndView();
        way.setIdsecteur(idsecteur);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addObject("view", "addVoie");
        model.addObject("way", way);
        model.addObject("userName", user.getName() + " " + user.getLastname());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/addVoie"}, method = RequestMethod.POST)
    public String addedVoie(Way way) {
        wayRepository.save(way);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/rent"}, method = RequestMethod.POST)
    public String rentIt(@RequestParam("idtopo") int idtopo, Rent rent) {
        //Date creationDate, Date returnDate
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        rent.setIsloan(true);
        rent.setIdtopo(idtopo);
        rent.setIduser(user.getId());
        rent.setBorrow(false);
        rent.setSeen(true);
        topoService.rentTopo(rent);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/unrent"}, method = RequestMethod.GET)
    public String unrentIt(@RequestParam("idtopo") int idtopo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Rent rent = rentRepository.getOne(idtopo);
        if (rent.isIsloan() && rent.isBorrow()) {
            rent.setSeen(false);
            rent.setIsloan(false);
            rent.setBorrow(false);
        }
        if ((rent.isIsloan()) && (!rent.isBorrow())) {
            rent.setIsloan(false);
        }
        rentRepository.save(rent);

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/deleCom"}, method = RequestMethod.GET)
    public String deleteCom(@RequestParam("idcommentaire") int idcommentaire) {
        Commentaire com = commentaireRepository.getOne(idcommentaire);
        com.setHidden(true);
        commentaireRepository.save(com);
        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/deleTopo"}, method = RequestMethod.GET)
    public String deleteTopo(@RequestParam("idtopo") int idtopo) {
        Rent rent = rentRepository.getOne(idtopo);
        Topo topo = topoService.findOneById(idtopo);

        topo.setHidden(true);

        if (rent.isBorrow() && rent.isIsloan()) {

            rent.setIsloan(false);

            rent.setSeen(false);

            rentRepository.save(rent);
            topoRepository.save(topo);
        }

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/com"}, method = RequestMethod.POST)
    public String comment(Commentaire commentaire, @RequestParam("idtopo") int idtopo, @RequestParam("idspot") int idspot, @RequestParam("idpublication") int idpublication, @RequestParam("iduser") int iduser) {

        commentaire.setIdpublication(idpublication);
        commentaire.setIduser(iduser);

        topoService.saveCommentaire(commentaire);

        return "redirect:/publication/?idtopo=" + idtopo + "&idspot=" + idspot;
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public ModelAndView searchIt(@RequestParam("search") String search) {
        ModelAndView model = new ModelAndView();


        String cap = search.substring(0, 1).toUpperCase() + search.substring(1);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Spot>  spots = spotService.findByName(search);
        List<Topo>  topos = topoService.findByLieu(search);
        topos.addAll(topoService.findByLieu(cap));
        spots.addAll(spotService.findByName(cap));
        for (Spot spot : spots) {
            for (Topo topo : topos) {
                if ((topos.toArray().toString()).contains(Integer.toString(spot.getIdtopo()))) {
                    topos.addAll(topoService.findById(spot.getIdtopo()));
                }
            }
        }

        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastname());
        } else {
            model.addObject("userName", "0");
        }
        spots = spotService.findAllSpot();

        model.addObject("spot", spots);
        model.addObject("topo", topos);
        model.addObject("search", search);

        model.addObject("view", "search");
        //model.setViewName("home/home");
        model.setViewName("index");

        return model;
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String search(String search) {
        System.out.println(search);

        return "redirect:/search/?search=" + search;
    }

    @RequestMapping(value = {"/publication/"}, method = RequestMethod.GET)
    public ModelAndView publication(@RequestParam("idtopo") int idtopo, @RequestParam("idspot") int idspot) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Spot> spots = spotService.findAllSpot();
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
        model.addObject("lastSpotName", lastSpotName);
        model.addObject("lastSpotDescription", lastSpotDescription);
        model.addObject("lastSpotlink", lastSpotlink);
        model.addObject("secondSpotName", secondSpotName);
        model.addObject("secondSpotDescription", secondSpotDescription);
        model.addObject("secondSpotlink", secondSpotlink);
        model.addObject("thridSpotName", thridSpotName);
        model.addObject("thridSpotDescription", thridSpotDescription);
        model.addObject("thridSpotlink", thridSpotlink);
        List<Topo> topos = topoService.findById(idtopo);
        List<Proprietaire> Proprietaire = proprietaireRepository.findAllPro();
        List<User> userL = userService.findAll();

        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastname());
            model.addObject("userConnectedId", user.getId());
            model.addObject("UserId", user.getId());
        } else {
            model.addObject("userName", "0");
            model.addObject("UserId", 0);
        }
              List<Rent> rent = rentRepository.findAll();
        String rentContain = "";
        if (rent.size() > 0) {
            model.addObject("rent", rent);
            for (Rent rents : rent) {
                rentContain += rents.getIdtopo();
            }
        } else {
            Rent dummy = new Rent();
            Date now = Date.valueOf(LocalDate.now());
            dummy.setCreationDate(now);
            dummy.setReturnDate(now);
            dummy.setIsloan(false);
            dummy.setIdtopo(0);
            dummy.setIduser(0);
            rent.add(dummy);
            model.addObject("rent", rent);
        }
        List<Secteur> secteurs = secteurRepository.findByIdSpot(idspot);
        spots = spotService.findByIdtopo(idtopo);
        List<Way> ways = wayRepository.findAll();
        int idPub = 0;
        Commentaire commentaires = new Commentaire();
        for (Spot spot : spots) {

            idPub = spot.getIdpublication();
            Publication pub = publicationService.findAllById(spot.getIdpublication());

            model.addObject("rentContain", rentContain);
        }
        // List<User> users = userService.f
        List<Commentaire> comment = commentaireRepository.findAllByIdPublication(idPub);
        Publication pub = publicationService.findAllById(idPub);

        model.addObject("idtopo", idtopo);
        model.addObject("idspot", idspot);
        model.addObject("user", userL);
        model.addObject("view", "publication");
        model.addObject("proprietaire", Proprietaire);
        model.addObject("comment", comment);
        model.addObject("publication", pub);
        model.addObject("spot", spots);
        model.addObject("topo", topos);
        model.addObject("secteur", secteurs);
        model.addObject("way", ways);
        model.addObject("commentaire", commentaires);
        //model.setViewName("home/publication");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/insertPublication"}, method = RequestMethod.GET)
    public ModelAndView insertPublication() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Publication pub = new Publication();
        Topo topo = new Topo();
        Spot spot = new Spot();
        Secteur secteur = new Secteur();
        Way way = new Way();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        List<Topo> topos = topoService.findAllTopo();
        String search = "";
        model.addObject("search", search);
        model.addObject("topos", topos);
        model.addObject("userName", user);
        model.addObject("publication", pub);
        model.addObject("topo", topo);
        model.addObject("spot", spot);
        model.addObject("secteur", secteur);
        model.addObject("way", way);
        model.addObject("userName", user.getName() + " " + user.getLastname());
        // model.setViewName("user/inscriptionTopo");
        model.addObject("view", "inscriptionTopo");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/insertPublication"}, method = RequestMethod.POST, headers = "content-type=multipart/*")
    public String savePublication(@RequestParam("file") MultipartFile imgSpot, @RequestParam("fileSecteur") MultipartFile imgSecteur, Publication publication, Topo topo, Spot spot, Secteur secteur, Way way) throws IOException {

        Path currentRelativePath = Paths.get("");
        String path = "\\src\\main\\resources\\static\\img\\";
        String s = (currentRelativePath.toAbsolutePath().toString()) + path;
        byte[] bytesSpot = imgSpot.getBytes();
        byte[] bytesSecteur = imgSecteur.getBytes();
        Random random = new Random();
        int numb;
        Path pathSpot = Paths.get(s + imgSpot.getOriginalFilename());
        Path pathSector = Paths.get(s + imgSecteur.getOriginalFilename());

        while (pathSpot.toFile().exists() || pathSector.toFile().exists() || pathSector == pathSpot) {
            numb = random.nextInt(10);

            if (pathSpot.toFile().exists()) {
                pathSpot = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
            if (pathSector.toFile().exists()) {
                pathSector = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
            if (pathSector == pathSpot) {
                pathSector = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }
        }

        Files.write(pathSpot, bytesSpot);
        Files.write(pathSector, bytesSecteur);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        topoService.savePublication(publication, topo, spot, secteur, way, user.getId(), imgSpot.getOriginalFilename(), imgSecteur.getOriginalFilename());

        return "redirect:home";
    }

    @RequestMapping(value = {"/borrowTopo"}, method = RequestMethod.GET)
    public ModelAndView borrowTopo() {
        ModelAndView model = new ModelAndView();
        List<Topo> topos = topoService.findAllTopo();
        List<Spot> spots = spotService.findAllSpot();
        List<Topo> selectedTopo = new ArrayList();
        List<Rent> rent = rentRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
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
        for (Topo topos2 : topos) {

            for (Rent rents : rent) {
                if ((rents.getIdtopo() == topos2.getIdtopo()) && rents.isIsloan() && !rents.isBorrow()) {
                    selectedTopo.add(topos2);

                }
            }
        }

        model.addObject("lastSpotName", lastSpotName);
        model.addObject("lastSpotDescription", lastSpotDescription);
        model.addObject("lastSpotlink", lastSpotlink);
        model.addObject("secondSpotName", secondSpotName);
        model.addObject("secondSpotDescription", secondSpotDescription);
        model.addObject("secondSpotlink", secondSpotlink);
        model.addObject("thridSpotName", thridSpotName);
        model.addObject("thridSpotDescription", thridSpotDescription);
        model.addObject("thridSpotlink", thridSpotlink);
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastname());
        } else {
            model.addObject("userName", "0");
        }

        model.addObject("spot", spots);
        model.addObject("view", "home");
        model.addObject("topo",selectedTopo);

        //model.setViewName("home/home");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        List<Topo> topos = topoService.findAllTopo();
        List<Spot> spots = spotService.findAllSpot();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
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
        model.addObject("lastSpotName", lastSpotName);
        model.addObject("lastSpotDescription", lastSpotDescription);
        model.addObject("lastSpotlink", lastSpotlink);
        model.addObject("secondSpotName", secondSpotName);
        model.addObject("secondSpotDescription", secondSpotDescription);
        model.addObject("secondSpotlink", secondSpotlink);
        model.addObject("thridSpotName", thridSpotName);
        model.addObject("thridSpotDescription", thridSpotDescription);
        model.addObject("thridSpotlink", thridSpotlink);
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastname());
        } else {
            model.addObject("userName", "0");
        }

        model.addObject("spot", spots);
        model.addObject("view", "home");
        model.addObject("topo", topos);

        //model.setViewName("home/home");
        model.setViewName("index");
        return model;
    }
    @RequestMapping(value = {"/header"}, method = RequestMethod.GET)
    public ModelAndView header() {
        ModelAndView model = new ModelAndView();
        model.addObject("userName", "0");


        model.setViewName("fragment/header");
        return model;
    }
}
