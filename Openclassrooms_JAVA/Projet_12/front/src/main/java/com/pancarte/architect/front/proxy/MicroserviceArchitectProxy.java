package com.pancarte.architect.front.proxy;

import com.pancarte.architect.front.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

@FeignClient(name = "microservice-architecte", url = "http://localhost:9090", decode404 = true)
public interface MicroserviceArchitectProxy {
    /**
     * Cree un utilisateur
     *
     * @param lastName  le nom de famillle de l'utilisateur
     * @param firstName le prenom  de l'utilisateur
     * @param email     l'email de l'utilisateur
     * @param password  le mot de passe de l'utilisateur
     */
    @RequestMapping(value = {"/createUser"})
     void createUser(@RequestParam("last_name") String lastName,
                           @RequestParam("first_name") String firstName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password);

    /**
     * Recupère les utilisateur
     *
     * @return renvoie les utilisateur
     */
    @RequestMapping(value = "/queryUsers")
    List<User> queryAllUser();

    @RequestMapping(value = {"/queryUserById"})
    User queryUserById(@RequestParam("id_user") int idUser);

    /**
     * Recupere un utilisateur par son email
     *
     * @param email de l'utilisateur
     * @return un utilisateur
     */
    @RequestMapping(value = {"/queryUserByName"})
    User queryUserByName(@RequestParam("email") String email);

    /**
     * Recupere tout les projets
     *
     * @return tout les projets
     */
    @RequestMapping(value = {"/queryProject"})
    public List<Project> queryProject();

    /**
     * Ajoute un materiaux
     *
     * @param name      Nom du materiaux
     * @param thickness Epaisseur du matériaux
     * @param opaque    Si le materiaux est opaque
     */
    @RequestMapping(value = {"/addMaterial"})
   void addMaterial(@RequestParam("name") String name,
                            @RequestParam("thickness") int thickness,
                            @RequestParam("opaque") boolean opaque);

    /**
     * Recupere tout les materiaux
     *
     * @return tout les materiaux
     */
    @RequestMapping(value = {"/queryAllMaterial"})
    List<Material> queryAllMaterial();

    /**
     * Ajoute un projet
     *
     * @param description du projet
     * @param name        du projet
     * @param type        du projet
     * @param material    du projet
     * @param surface     du projet
     * @param img         du projet
     */
    @RequestMapping(value = {"/addProject"})
     void addProject(@RequestParam("description") String description,
                           @RequestParam("name") String name,
                           @RequestParam("type") String type,
                           @RequestParam("material") String material,
                           @RequestParam("surface") int surface,
                           @RequestParam("img") String img);

    /**
     * Met a jour le projet
     *
     * @param idProject   du projet
     * @param description du projet
     * @param name        du projet
     * @param type        du projet
     * @param material    du projet
     * @param surface     v
     */
    @RequestMapping(value = {"/updateProject"})
     void updateProject(@RequestParam("id_project") int idProject,
                              @RequestParam("description") String description,
                              @RequestParam("name") String name,
                              @RequestParam("type") String type,
                              @RequestParam("material") String material,
                              @RequestParam("surface") int surface);

    /**
     * Supprime un projet
     *
     * @param idProject du projet
     */
    @RequestMapping(value = {"/deleteProject"})
    void deleteProject(@RequestParam("id_project") int idProject);
    /**
     * Recupere un utilisateur par son email
     * @param email de l'utilisateur
     * @return  un utilisateur
     */
    @RequestMapping(value = {"/queryUserByName"})
    String queryUser(@RequestParam("email") String email);


    /**
     * Recupere tout les rendez-vous
     * @param idMeeting du rendez-vous
     * @return
     */
    @RequestMapping(value = {"/queryMeeting"})
     Meeting queryMeeting(@RequestParam("id_meeting") int idMeeting);
    /**
     * Envoie une demande de rendez vous par  mail a l'architecte
     * @param email du destinataire
     * @param dateSended la date de rendez vous
     * @param purpose le but du rendez vous
     */
    @RequestMapping(value = {"/sendMeeting"})
    void sendMeeting(@RequestParam("email") String email,
                            @RequestParam("dateSended") Timestamp dateSended,
                            @RequestParam("purpose") String purpose);

    /**
     * Creer un Devis
     * @param email
     * @param idProjet
     */
    @RequestMapping(value = {"/quoteMaking"})
    public void quoteMaking(@RequestParam("email") String email,@RequestParam("id_projet") int idProjet);


    /**
     * Recupere tout les emails bloqués
     * @return tout les emails bloqués
     */
    @RequestMapping(value = {"/queryBlockedEmail"})
    List<BlockedMail> queryblockedEmail();
    /**
     * Recupere tout les rendez-vous
     */
    @RequestMapping(value = {"/queryAllMeeting"})
     List<Meeting> queryAllMeeting();
    /**
     * Verifie la reponse de l'architecte pour le  rendez-vous
     * @param meeting
     * @param idMeeting
     */
    @RequestMapping(value = {"/verifyMeeting"}, method = RequestMethod.GET)
     void verifyMeeting(@RequestParam("meeting") boolean meeting, @RequestParam("id_meeting") int idMeeting);
}
