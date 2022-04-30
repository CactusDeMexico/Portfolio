package com.pancarte.architecte.controller;

import com.pancarte.architecte.model.*;
import com.pancarte.architecte.repository.*;
import com.pancarte.architecte.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pancarte.architecte.model.Log;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
/**
 * Controleur
 * @author Marjorie Pancarte
 * @version 1.0
 */
@RestController
public class ArchitectController {
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final RoleRepository roleRepository;
    private final MaterialRepository materialRepository;
    private final BlockedMailRepository blockedMailRepository;
    private final ProjectRepository projectRepository;
    private final QuoteRepository quoteRepository;
    private final UserService userService;
    private final JavaMailSender javaMailSender;

    private static final String SUBJECT = "Rendez vous avec M.architect ";



    /**
     * Le constructeur
     * @param roleRepository le répertoire du role de
     * @param userRepository  le répertoire de l'utilisateur
     * @param meetingRepository le répertoire des rendez-vous
     * @param materialRepository  le répertoire des materiaux
     * @param blockedMailRepository le répertoire des email bloqué
     * @param projectRepository le répertoire des project
     * @param quoteRepository
     * @param userService le service des utilisateur
     * @param javaMailSender config pour l'envoie d'email
     *
     */
    @Autowired
    public ArchitectController(@Qualifier("roleRepository") RoleRepository roleRepository,
                               @Qualifier("userRepository") UserRepository userRepository,
                               @Qualifier("meetingRepository") MeetingRepository meetingRepository, @Qualifier("materialRepository") MaterialRepository materialRepository, @Qualifier("blockedMailRepository") BlockedMailRepository blockedMailRepository, @Qualifier("projectRepository") ProjectRepository projectRepository,@Qualifier("quoteRepository") QuoteRepository quoteRepository, UserService userService, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.meetingRepository = meetingRepository;
        this.materialRepository = materialRepository;
        this.blockedMailRepository = blockedMailRepository;
        this.projectRepository = projectRepository;
        this.quoteRepository = quoteRepository;
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envoie un email
     * @param email    L'email du destinataire
     * @param subject  Le sujet du mail
     * @param text     Le contenu du mail
     */
    void sendEmail(String email, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
        Log log = new Log();
        log.setInfo(email,subject,text);
    }

    /**
     * Cree un utilisateur
     * @param lastName le nom de famillle de l'utilisateur
     * @param firstName le prenom  de l'utilisateur
     * @param email     l'email de l'utilisateur
     * @param password  le mot de passe de l'utilisateur
     */
    @RequestMapping(value = {"/createUser"}, method = RequestMethod.GET)
    public void createUser(@RequestParam("last_name") String lastName,
                           @RequestParam("first_name") String firstName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPassword(password);

        userService.saveUser(user);
    }

    /**
     * Recupère les utilisateur
     * @return renvoie les utilisateur
     */
    @RequestMapping(value = {"/queryUsers"}, method = RequestMethod.GET)
    public List<User> queryAllUser() {

        return userRepository.findAll();
    }

    /**
     * Recupere un utilisateur par son email
     * @param email de l'utilisateur
     * @return  un utilisateur
     */
    @RequestMapping(value = {"/queryUserByName"}, method = RequestMethod.GET)
    public User queryUserByEmail(@RequestParam("email") String email) {
        return userRepository.queryUser(email);
    }

    /**
     * Recupere un utilisateur par id
     * @param idUser  id de l'user
     * @return
     */
    @RequestMapping(value = {"/queryUserById"}, method = RequestMethod.GET)
    public User queryUserById(@RequestParam("id_user") int idUser) {
        User user = userRepository.findById(idUser);
        return user;
    }

    /**
     * Supprime un utilisateur
     * @param idUser
     */
 /*   @RequestMapping(value = {"/deleteUserById"}, method = RequestMethod.GET)
    public void deleteUserById(@RequestParam("id_user") int idUser) {
        User user = userRepository.findById(idUser);
        user.setHidden(true);
        userRepository.save(user);
    }
*/
    /**
     * Ajoute un materiaux
     * @param name Nom du materiaux
     * @param thickness Epaisseur du matériaux
     * @param opaque Si le materiaux est opaque
     */
    @RequestMapping(value = {"/addMaterial"}, method = RequestMethod.GET)
    public void addMaterial(@RequestParam("name") String name,
                            @RequestParam("thickness") int thickness,
                            @RequestParam("opaque") boolean opaque) {
        Material material = new Material();
        material.setOpaque(opaque);
        material.setThickness(thickness);
        material.setName(name);
        materialRepository.save(material);
    }


    /**
     *  Recupere tout les materiaux
     *   @return tout les materiaux
     */
    @RequestMapping(value = {"/queryAllMaterial"}, method = RequestMethod.GET)
    public List<Material> queryAllMaterial(){

        return materialRepository.findAll();
    }

    /**
     *  Recupere tout les projets
     * @return tout les projets
     */
    @RequestMapping(value = {"/queryProject"}, method = RequestMethod.GET)
    public List<Project> queryProject(){

        return projectRepository.findAll();
    }

    /**
     * Ajoute un projet
     * @param description du projet
     * @param name du projet
     * @param type du projet
     * @param material du projet
     * @param surface du projet
     * @param img du projet
     */
    @RequestMapping(value = {"/addProject"}, method = RequestMethod.GET)
    public void addProject(@RequestParam("description") String description,
                           @RequestParam("name") String name,
                           @RequestParam("type") String type,
                           @RequestParam("material") String material,
                           @RequestParam("surface") int surface,
                           @RequestParam("img") String img) {

        Project project = new Project();
        project.setHidden(false);
        project.setProjectName(name);
        project.setDescription(description);

        project.setSurface(surface);
        project.setType(type);
        project.setUrlImg(img);
        String idMaterial[]=material.split(",");
        List<Material> materialsList = new ArrayList<>();
        for (int i=0; i<idMaterial.length;i++) {
                materialsList.add( materialRepository.queryMaterialById(Integer.parseInt(idMaterial[i])));
        }

        project.setMaterials(new HashSet<Material>(materialsList));
        projectRepository.save(project);
    }

    /**
     * Met a jour le projet
     * @param idProject du projet
     * @param description du projet
     * @param name du projet
     * @param type du projet
     * @param material du projet
     * @param surface v
     */
    @RequestMapping(value = {"/updateProject"}, method = RequestMethod.GET)
    public void updateProject(@RequestParam("id_project") int idProject, @RequestParam("description") String description, @RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("material") String material, @RequestParam("surface") int surface) {

        Project projectUpdate = projectRepository.queryProjectById(idProject);

        projectUpdate.setDescription(description);
        projectUpdate.setProjectName(name);
        projectUpdate.setSurface(surface);
        projectUpdate.setType(type);

        projectRepository.save(projectUpdate);
    }

    /**
     * Supprime un projet
     * @param idProject du projet
     */
    @RequestMapping(value = {"/deleteProject"}, method = RequestMethod.GET)
    public void deleteProject(@RequestParam("id_project") int idProject) {
        Project project = projectRepository.queryProjectById(idProject);
        project.setHidden(true);

        projectRepository.save(project);
    }

    /**
     * Bloque un email
     * @param email de la personne qui sera bloqué
     * @param cause raison du blocage
     */
    @RequestMapping(value = {"/blockEmail"}, method = RequestMethod.GET)
    public void blockEmail(@RequestParam("email") String email, @RequestParam("cause") String cause) {
        BlockedMail blockedMail = new BlockedMail();
        blockedMail.setCause(cause);
        blockedMail.setEmail(email);
        blockedMailRepository.save(blockedMail);

    }

    /**
     * Recupere tout les emails bloqués
     * @return tout les emails bloqués
     */
    @RequestMapping(value = {"/queryBlockedEmail"}, method = RequestMethod.GET)
    public List<BlockedMail> queryblockedEmail(){

        return blockedMailRepository.findAll();
    }
   /* @RequestMapping(value = {"/queryProjectMaterial"}, method = RequestMethod.GET)
    public List<Project_Material> queryProjectMaterial(){

        return materialRepository.queryProjectMaterial();
    }*/


    /**
     * Creer un Devis
     * @param email
     * @param idProjet
     */
    @RequestMapping(value = {"/quoteMaking"}, method = RequestMethod.GET)
    public void quoteMaking(@RequestParam("email") String email,@RequestParam("id_projet") int idProjet) {
        Quote quote = new Quote();
        quoteRepository.save(quote);
    }

    /**
     * Verifie si l'email est valide
     * @param email a valider
     * @return retourne si l'email est valide
     */
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Envoie une demande de rendez vous par  mail a l'architecte
     * @param email du destinataire
     * @param dateSended la date de rendez vous
     * @param purpose le but du rendez vous
     */
    @RequestMapping(value = {"/sendMeeting"}, method = RequestMethod.GET)
    public void sendMeeting(@RequestParam("email") String email, @RequestParam("dateSended") Timestamp dateSended, @RequestParam("purpose") String purpose) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        List<BlockedMail> blockedMails = blockedMailRepository.findAll();
        Log log = new Log();
        if (!Arrays.toString(blockedMails.toArray()).contains(email) && isValidEmailAddress(email)) {
            User userArchitect = userRepository.queryUser("marj12@live.fr");
            System.out.println(email);
            Meeting meeting = new Meeting();
            meeting.setClosed(false);
            Date now = Date.valueOf(LocalDate.now());
            meeting.setDateSended(new Timestamp(now.getTime()));
            meeting.setDateMeeting(dateSended);
            meeting.setInvitationSended(true);
            meeting.setPurpose(purpose);
            meeting.setMeetingValidate(false);
            meeting.setEmail(email);
            meetingRepository.save(meeting);
            List<Meeting> allMeeting = meetingRepository.findAll();
            Meeting lastmeeting = allMeeting.get(allMeeting.size() - 1);

            sendEmail(userArchitect.getEmail(),
                    "Prise de rendez vous avec :" + email + " le :" + dateSended,
                    purpose +
                            "\n Rendez vous avec :" + email + " le :" + dateSended
                            + "\n pour valider le rendez vous cliquer sur : http://localhost:9090/verifyMeeting?id_meeting=" + lastmeeting.getId() + "&meeting=true"
                            + "\n pour annuler le rendez vous cliquer sur : http://localhost:9090/verifyMeeting?id_meeting=" + lastmeeting.getId() + "&meeting=false"
                            + "\n Pourbloquer cette email rendez vous cliquer sur : http://localhost:9090/blockEmail?email=" + email + "&cause=blocked"
            );
        } else {
            // possible mise en place d'envoie email pour les compte bloqué.

            log.resultMailFail(email,purpose);




        }
    }

    /**
     * Recupere tout les rendez-vous
     * @param idMeeting du rendez-vous
     * @return
     */
    @RequestMapping(value = {"/queryMeeting"}, method = RequestMethod.GET)
    public Meeting queryMeeting(@RequestParam("id_meeting") int idMeeting){

        return meetingRepository.queryMeetingById(idMeeting);
    }   @RequestMapping(value = {"/queryAllMeeting"}, method = RequestMethod.GET)
    /**
     * Recupere tout les rendez-vous
     */
    public List<Meeting> queryAllMeeting(){
        return meetingRepository.findAll();
    }

    /**
     * Verifie la reponse de l'architecte pour le  rendez-vous
     * @param meeting
     * @param idMeeting
     */
    @RequestMapping(value = {"/verifyMeeting"}, method = RequestMethod.GET)
    public void verifyMeeting(@RequestParam("meeting") boolean meeting, @RequestParam("id_meeting") int idMeeting) throws Exception {
        Meeting checkMeeting = meetingRepository.queryMeetingById(idMeeting);

        User userArchitect = userRepository.queryUser("marj12@live.fr");
        ConfigReader data = new ConfigReader();
        System.out.println(data.getTexte()+" lex txxxxxxxxxxx");
        if (meeting) {
            if(data.isPersonlaMail()){
                sendEmail(checkMeeting.getEmail(),
                        SUBJECT,
                        data.getTexte());
            }else{
            sendEmail(checkMeeting.getEmail(),
                    SUBJECT,
                    "Votre rendez vous avec Mr Architect est confirmé pour le :" + checkMeeting.getDateMeeting());
            }
        } else {
            if(data.isPersonlaMail()){
                sendEmail(checkMeeting.getEmail(),
                        SUBJECT,
                        data.getTexte());
            }else{
            sendEmail(checkMeeting.getEmail(),
                    SUBJECT,
                    "Votre rendez vous avec Mr Architect est annulé pour le :" + checkMeeting.getDateMeeting());
            }
        }
        checkMeeting.setMeetingValidate(meeting);
        checkMeeting.setClosed(true);

        meetingRepository.save(checkMeeting);
    }

    /**
     * Repond automatiquement en cas de non reponse (au bout de 1semaine) de l'architecte au rendz-vous
     * Se lance tout les jours
     */
    @Scheduled(cron = "	0 0 0 1/1 * ? ")// tout les jours à 0h00
    @RequestMapping(value = {"/cleanMeeting"}, method = RequestMethod.GET)
    public void cleanMeeting() {
        List<Meeting> meetings = meetingRepository.findAll();
        User userArchitect = userRepository.queryUser("marj12@live.fr");
        for (Meeting meeting : meetings
        ) {
            LocalDate sendedDate = meeting.getDateSended().toLocalDateTime().toLocalDate();

            LocalDate nextWeek = sendedDate.plus(1, ChronoUnit.WEEKS);

            if (sendedDate.compareTo(nextWeek) > 0 && !meeting.isMeetingValidate() && !meeting.isClosed()) {
                Log log = new Log();
                log.cancelMail(meeting.getEmail(),"annulation automatique");
                sendEmail(meeting.getEmail(),
                        SUBJECT,
                        "Votre rendez vous avec Mr Architect est annulé pour le :" + meeting.getDateMeeting()
                );
                sendEmail(userArchitect.getEmail(),
                        SUBJECT,
                        "Annulation automatique du rendez vous  le :" + meeting.getDateMeeting()+" avec :"+meeting.getEmail()
                );
                meeting.setClosed(true);
                meetingRepository.save(meeting);
            }
        }
    }
}
