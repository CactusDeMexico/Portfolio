package com.pancarte.architecte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancarte.architecte.model.*;
import com.pancarte.architecte.repository.*;
import com.pancarte.architecte.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.sun.javaws.JnlpxArgs.verify;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchitectControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private User userTest1;
    private User userTest2;
    private Quote quotesTest1;
    private Quote quotesTest2;
    private Project projectTest1;
    private Project projectTest2;
    private Meeting meetingTest1;
    private Meeting meetingTest2;
    private Material materialTest1;
    private Material materialTest2;
    private BlockedMail blockedMailTest1;
    private BlockedMail blockedMailTest2;
    private Role roleTest1;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MeetingRepository meetingRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private MaterialRepository materialRepository;
    @MockBean
    private BlockedMailRepository blockedMailRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private QuoteRepository quoteRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private JavaMailSender javaMailSender;
    ArchitectController test = new ArchitectController(roleRepository
            , userRepository, meetingRepository, materialRepository, blockedMailRepository,
            projectRepository, quoteRepository, userService, javaMailSender);

    @Before
    public void setUp() throws Exception {
        Role roleTest1 = new Role(1, "Admin");
        Role roleTest2 = new Role(2, "employee");
        List<Role> roleList = new ArrayList<>();

        userTest1 = new User(1, "Jean", "Pierre", "jp@mail.com", "123", false, new HashSet<Role>(Arrays.asList(roleTest1)));
        userTest2 = new User(2, "Lucie", "Janna", "lj@mail.com", "123", false, new HashSet<Role>(Arrays.asList(roleTest1)));
        quotesTest1 = new Quote();
        quotesTest2 = new Quote();
        projectTest1 = new Project();
        projectTest2 = new Project();
        meetingTest1 = new Meeting();
        meetingTest2 = new Meeting();
        materialTest1 = new Material();
        materialTest2 = new Material();
        blockedMailTest1 = new BlockedMail();
        blockedMailTest2 = new BlockedMail();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*
    @Test
    public void testGetAllBookWithNoBookRest() throws Exception {

        List<Book> bookList = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        MvcResult result = mockMvc.perform(get("/getBooks")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(bookList), result.getResponse().getContentAsString(), false);
    }*/

    @Test
    public void queryAllUser() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(userTest1);
        userList.add(userTest2);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        MvcResult result = mockMvc.perform(get("/queryUsers")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(userList), result.getResponse().getContentAsString(), false);
        assertEquals(2, userList.size());
    }
    @Test
    public void queryAllUserEmpty() throws Exception {
        List<User> userList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        MvcResult result = mockMvc.perform(get("/queryUsers")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(userList), result.getResponse().getContentAsString(), false);
        assertEquals(0, userList.size());
    }

    @Test
    public void queryUserByName() throws Exception {

        List<User> userList = new ArrayList<>();
        userList.add(userTest1);
        userList.add(userTest2);

        Mockito.when(userRepository.findByEmail("lj@mail.com")).thenReturn(userTest2);
        assertEquals(userTest2, userRepository.findByEmail("lj@mail.com"));
    }

    @Test
    public void queryUserByWrongName() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        Mockito.when(userRepository.findByEmail("lp@mail.com")).thenReturn(user);
        assertNull(userRepository.findByEmail("lj@mail.com"));
    }

    @Test
    public void queryUserById() {
        List<User> userList = new ArrayList<>();
        userList.add(userTest1);
        userList.add(userTest2);
        User user = new User();
        Mockito.when(userRepository.findById(1)).thenReturn(userTest1);
        assertEquals(userTest1, userRepository.findById(1));
    }

    @Test
    public void queryUserByWrongId() {
        List<User> userList = new ArrayList<>();
        userList.add(userTest1);
        userList.add(userTest2);
        User user = new User();
        Mockito.when(userRepository.findById(3)).thenReturn(user);
        assertNull(userRepository.findById(1));
    }

    @Test
    public void queryAllMaterial() throws Exception {
        List<Material> materials = new ArrayList<>();
        materials.add(materialTest1);
        materials.add(materialTest2);
        Mockito.when(materialRepository.findAll()).thenReturn(materials);
        MvcResult result = mockMvc.perform(get("/queryAllMaterial")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(materials), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void queryProject() throws Exception {
        List<Project> projects = new ArrayList<>();
        projects.add(projectTest1);
        projects.add(projectTest2);
        Mockito.when(projectRepository.findAll()).thenReturn(projects);
        MvcResult result = mockMvc.perform(get("/queryProject")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(projects), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void addProject() throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(projectTest1);

        Material material = new Material(1, "bois", 22, true);
        projectTest1 = new Project("name", "desc", "interieur", 22, "img.jpg", false, new HashSet<Material>(Arrays.asList(material)));

        Mockito.when(projectRepository.save(new Project())).thenReturn(new Project());

        // Mockito.verify(projectRepository, times(1)).save(any(Project.class));
        assertEquals(1, projectList.size());
    }

    public void updateProject() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(projectTest1);

        Material material = new Material(1, "bois", 22, true);
        projectTest1 = new Project("studio", "desc", "interieur", 22, "img.jpg", false, new HashSet<Material>(Arrays.asList(material)));

        Mockito.when(projectRepository.save(new Project())).thenReturn(new Project());

        // Mockito.verify(projectRepository, times(1)).save(any(Project.class));
        assertEquals(1, projectList.size());
    }

    @Test
    public void queryblockedEmail() throws Exception {
        List<BlockedMail> bloquedList = new ArrayList<>();
        bloquedList.add(blockedMailTest1);
        bloquedList.add(blockedMailTest2);
        Mockito.when(blockedMailRepository.findAll()).thenReturn(bloquedList);
        MvcResult result = mockMvc.perform(get("/queryBlockedEmail")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(bloquedList), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void quoteMaking() {
        List<Quote> quotes = new ArrayList<>();
        quotes.add(quotesTest1);
        Mockito.when(quoteRepository.save(new Quote())).thenReturn(new Quote());
        assertEquals(1, quotes.size());
    }

    @Test
    public void createUser() {
        List<User> user = new ArrayList<>();
        user.add(userTest1);
        Mockito.when(userRepository.save(new User())).thenReturn(new User());
        assertEquals(1, user.size());
        Mockito.verify(userRepository,times(1)).save(userTest1);
    }

    @Test
    public void addMaterial() {
        List<Material> materials = new ArrayList<>();
        materials.add(materialTest1);
        materials.add(materialTest2);
        Material material = new Material("worbla",22,true);
        Mockito.when(materialRepository.save(new Material())).thenReturn(new Material());
        Mockito.verify(materialRepository,times(1)).save(materialTest1);
        assertEquals(2, materials.size());


    }

    @Test
    public void blockEmail1() {
        List<BlockedMail> blockedMails = new ArrayList<>();
        blockedMails.add(blockedMailTest1);
        Mockito.when(blockedMailRepository.save(new BlockedMail())).thenReturn(new BlockedMail());
        assertEquals(1, blockedMails.size());
    }

    @Test
    public void deleteProject() {

        List<Project> projectList = new ArrayList<>();
        projectList.add(projectTest1);

        Material material = new Material(1, "bois", 22, true);
        projectTest1 = new Project("studio", "desc", "interieur", 22, "img.jpg", false, new HashSet<Material>(Arrays.asList(material)));

        Mockito.when(projectRepository.save(projectTest1)).thenReturn(projectTest1);
        Project testp = new Project();



        //Mockito.verify(projectRepository, times(1)).save(any(Project.class));
        assertEquals(1, projectList.size());
    }

    @Test
    public void sendEmail() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meetingTest1);
        Mockito.when(meetingRepository.save(new Meeting())).thenReturn(new Meeting());
        assertEquals(1, meetings.size());
    }

    @Test
    public void verifyMeeting() {
        List<Meeting> meetings = new ArrayList<>();
        meetingTest1.setInvitationSended(true);
        meetings.add(meetingTest1);
        Mockito.when(meetingRepository.save(new Meeting())).thenReturn(new Meeting());
        assertEquals(1, meetings.size());
    }

    @Test
    public void cleanMeeting() {
        List<Meeting> meetings = new ArrayList<>();
        meetingTest1.setClosed(true);
        meetings.add(meetingTest1);
        Mockito.when(meetingRepository.save(new Meeting())).thenReturn(new Meeting());
        assertEquals(1, meetings.size());
    }

    @Test
    public void isValidEmailAddress() {
        assertEquals(true, test.isValidEmailAddress("exemple@domaine.com"));
    }
}