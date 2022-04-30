package com.pancarte.architect.front.controller;

import com.pancarte.architect.front.model.*;
import com.pancarte.architect.front.proxy.MicroserviceArchitectProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Controller
public class ArchitectController {

    @Autowired
    private MicroserviceArchitectProxy Architect;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Met en place la vue de base
     * @param auth des utilisateur  présent sur le site
     * @param model
     * @param user
     */
    void initView( Authentication auth, ModelAndView model,  User user ){
        int userId = 0;
        if (!auth.getName().equals("anonymousUser")) {
            userId = user.getId();
            model.addObject("userName", user.getFirstName() + " " + user.getLastName());
            model.addObject("userId", userId);
        } else {
            model.addObject("userName", "0");
            model.addObject("user", userId);
        }
    }
    @RequestMapping(value = {"/content"}, method = RequestMethod.GET)
    public ModelAndView content() throws ParseException {
        ModelAndView model = new ModelAndView();
        User user = new User();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = 0;
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        String blockedMails = Architect.queryblockedEmail().toString();
        boolean meeting2= false;
        Meeting meetingCheck = new Meeting();
        model.addObject("meeting", meetingCheck);
        model.addObject("meetingValidate", meeting2);
        String search = "0";
        Project project = new Project();
        model.addObject("NoMeeting", false);
        model.addObject("project", project);
        Material material = new Material();
        List<Material> materials = Architect.queryAllMaterial();

        List<Project> projects = Architect.queryProject();
        model.addObject("projectList", projects);


        model.addObject("materials", materials);
        model.addObject("project",material);
        Meeting meeting = new Meeting();
        List<Meeting> meetingCheck2 = Architect.queryAllMeeting();
        model.addObject("meetingList", meetingCheck2);
        model.addObject("blockedmails", blockedMails);
        model.addObject("meeting", meeting);

        model.addObject("search", search);

        model.addObject("userId", userId);

        model.addObject("user", user);

        model.setViewName("fragment/content");

        return model;
    }

    /**
     * Met en place le header
     * @return le model
     */
    @RequestMapping(value = {"/header"}, method = RequestMethod.GET)
    public ModelAndView header() {
        ModelAndView model = new ModelAndView();
        model.addObject("userName", "0");

        model.setViewName("fragment/header");
        return model;
    }

    /**
     * Met en place la vue index
     * @param search
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "search", required = false) String search) throws ParseException {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = Architect.queryUserByName(auth.getName());
        int userId = 0;
        if (search == null) {
            search = "0";
        } else {
            String cap = search.substring(0, 1).toUpperCase() + search.substring(1);
            String caps = search.toUpperCase();
        }


        initView(auth,model,user);

        List<Project> projects = Architect.queryProject();
        model.addObject("projectList", projects);

        model.addObject("search", search);
        model.addObject("view", "home");
        model.setViewName("index");
        return model;
    }

    /**
     * Creer le rendez vous
     * @param email du demandeur
     * @param dateSended date du rendez vous
     * @param purpose le sujet du rendez vous
     * @return redirige vez l'index
     */
    @RequestMapping(value = {"/meeting"}, method = RequestMethod.POST)
    public String createMeeting(@RequestParam("email") String email,
                                @RequestParam("dateSended")DateTimeException dateSended,
                                @RequestParam("purpose") String purpose ) {

        String conformDate =dateSended.toString().replace("T"," ");
        conformDate=conformDate.replace("java.time.Date imeException: ","")+":00";

        Timestamp dateMeeting = Timestamp.valueOf( conformDate);
        //send only if mail is not blocked
        if(!Architect.queryblockedEmail().toString().contains(email)){
            System.out.println(email);
            Architect.sendMeeting(email,dateMeeting,purpose);
        }

        return "redirect:/index";
    }

    /**
     *  Affiche la vue pour la prise de rendez vous
     * @return
     */
    @RequestMapping(value = {"/meeting"}, method = RequestMethod.GET)
    public ModelAndView createMeeting() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Meeting meeting = new Meeting();
        String blockedMails = Architect.queryblockedEmail().toString();
        User user = Architect.queryUserByName(auth.getName());
        int userId = 0;

        initView(auth,model,user);
        model.addObject("meeting", meeting);
        model.addObject("blockedmails", blockedMails);
        model.addObject("view", "meeting");

        model.setViewName("index");
        return model;
    }

    /**
     *  Affiche la vue pour la verification
     * @param idMeeting du rendez-vous
     * @param meeting la reponse de l'architect
     * @return
     */
    @RequestMapping(value = {"/verifyMeeting"}, method = RequestMethod.GET)
    public String verifyMeeting(@RequestParam("id_meeting") int idMeeting,
                                      @RequestParam("meeting") boolean meeting){

        Architect.verifyMeeting(meeting,idMeeting);



        return "redirect:/index";

    }
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String search(String search) {

        return "redirect:/?search=" + search;
    }

    /**
     *  Affiche la vue pour la recherche
     * @param search la recherche
     * @return
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public ModelAndView searchIt(@RequestParam("search") String search) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addObject("view", "home");

        model.setViewName("index");

        return model;
    }

    /**
     *  Affiche la vue pour la connection
     * @return
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Architect.queryUserByName(auth.getName());
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getFirstName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        User userv = new User();
        model.addObject("view", "login");

        model.addObject("userName", "0");
        model.addObject("user", userv);

        model.setViewName("index");
        return model;
    }

    /**
     *  Affiche la vue pour pour se connecter
     * @return
     */
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

    /**
     * Creer un utilisateur
     * @param user
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = Architect.queryUserByName(user.getEmail());

        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "L'email existe déja");
        }
        if (bindingResult.hasErrors()) {
            // model.setViewName("user/signup");
            model.addObject("view", "signup");
            model.setViewName("index");
        } else {
            //bCryptPasswordEncoder.encode(user.getPassword());
            Architect.createUser(user.getLastName(), user.getFirstName(), user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()));
            model.addObject("msg", "L'utilisateur à été enregistré");
            model.addObject("user", new User());

            model.addObject("userName", "0");

            //model.setViewName("user/signup");
            model.addObject("view", "login");
            model.setViewName("index");
        }

        return model;
    }

    /**
     *  Affiche la vue apres un connexion reussi
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = {"/loggedhome"}, method = RequestMethod.GET)
    public ModelAndView loggedHome() throws ParseException {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Architect.queryUserByName(auth.getName());
        initView(auth,model,user);
        List<Meeting> meetingCheck = Architect.queryAllMeeting();
        int count=0;
        for (Meeting meeting : meetingCheck
             ) {
            if(!meeting.isClosed()){
                count++;
            }

        }if(count==0){
            model.addObject("NoMeeting", true);
        }


       model.addObject("meetingList", meetingCheck);
        model.addObject("view", "loggedhome");
        model.setViewName("index");
        return model;
    }

    /**
     *  Affiche la vue pour ajouter un projet
     * @return
     */
    @RequestMapping(value = {"/addProject"}, method = RequestMethod.GET)
    public ModelAndView addProject()  {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Architect.queryUserByName(auth.getName());
        initView(auth,model,user);
        Project project = new Project();
        List<Material> materials = Architect.queryAllMaterial();
        Material material = new Material();

        model.addObject("material",material);
        model.addObject("materials", materials);
        model.addObject("project", project);
        model.addObject("view", "addProject");
        model.setViewName("index");
        return model;
    }
    @RequestMapping(value = {"/addProject"}, method = RequestMethod.POST)
    public String  addProject(@RequestParam("imgSpot") MultipartFile imgSpot,
                              @RequestParam("name") String nameMaterial,
                              @RequestParam("description") String description,
                              @RequestParam("projectName") String name,
                              @RequestParam("type") String type,

                              @RequestParam("thickness") String thickness,
                              @RequestParam("opaque") String opaque,
                              @RequestParam("surface") int surface) throws IOException {

        Path currentRelativePath = Paths.get("");
        String path = "\\front\\src\\main\\resources\\static\\img\\";
        String s = (currentRelativePath.toAbsolutePath().toString()) + path;
        byte[] bytesSpot = imgSpot.getBytes();

        Random random = new Random();
        int numb;
        Path pathSpot = Paths.get(s + imgSpot.getOriginalFilename());


        while (pathSpot.toFile().exists()) {
            numb = random.nextInt(10);

            if (pathSpot.toFile().exists()) {
                pathSpot = Paths.get(s + numb + imgSpot.getOriginalFilename());
            }

        }
        Files.write(pathSpot, bytesSpot);
        Architect.addProject(description,name,type,nameMaterial,surface,imgSpot.getOriginalFilename());

        return "redirect:/index";
    }

    /**
     *  Affiche la vue pour ajout un materiel
     * @return
     */
    @RequestMapping(value = {"/addMaterial"}, method = RequestMethod.GET)
    public ModelAndView addMaterials()  {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Architect.queryUserByName(auth.getName());
        initView(auth,model,user);
        Material material = new Material();

        model.addObject("material",material);
        model.addObject("view", "addMaterial");
        model.setViewName("index");
        return model;
    }

    /**
     * Enregistre un material
     * @param name
     * @param thickness
     * @param opaque
     * @return
     */
    @RequestMapping(value = {"/addMaterial"}, method = RequestMethod.POST)
    public String addMaterial(@RequestParam("name") String name,
                            @RequestParam("thickness") int thickness,
                            @RequestParam("opaque") boolean opaque){
        Architect.addMaterial(name,thickness,opaque);
        return "redirect:/index";
    }

    /**
     *  Affiche la vue pour demander un devis
     * @param id_project
     * @return
     */
    @RequestMapping(value = {"/addQuote"}, method = RequestMethod.GET)
    public ModelAndView addQuote( @RequestParam("id_project") int id_project)  {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Architect.queryUserByName(auth.getName());
        initView(auth,model,user);
        Project project = new Project();
        Material material = new Material();
        List<Material> materials = Architect.queryAllMaterial();


        model.addObject("materials", materials);
        model.addObject("material",material);
        model.addObject("project", project);
        model.addObject("view", "addProject");
        model.setViewName("index");
        return model;
    }

}
