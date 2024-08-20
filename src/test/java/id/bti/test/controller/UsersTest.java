package id.bti.test.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.bti.test.model.Users;
import id.bti.test.repository.UsersRepository;
import id.bti.test.service.UsersService;

@SpringBootTest
// @WebMvcTest(UsersController.class)
@AutoConfigureMockMvc
public class UsersTest {
  
  @Autowired
	private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private UsersService usersService;

  @MockBean
  private UsersRepository usersRepository;

  @InjectMocks
  private UsersController usersController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
  }

  private Users sampleUser() {
    Users newUser = new Users();

    newUser.setUser_name("username");
    newUser.setPassword("password");
    newUser.setRole("admin");

    return newUser;
  }

  @Nested
  class InsertUsers {
    @Test
    public void insertUsers_success() throws Exception {
      Users newUser = sampleUser();

      // Mock the service method
      Mockito.when(usersService.insert(newUser)).thenReturn(newUser);

      mockMvc.perform(post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(newUser)))
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.data.user_name").value(newUser.getUser_name()));
    }

    @Test
    public void testInsertUserWithBlankUsername() throws Exception {
      // Create a Users object with a blank username
      Users user = new Users();
      user.setUser_name(""); // Blank username violates @NotBlank constraint

      // Perform a POST request to the insert endpoint
      mockMvc.perform(post("/users")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(user)))
              .andExpect(status().isBadRequest()); // Expect HTTP 400 Bad Request
    }
  }
  
  @Nested
  class GetUsers {
    @Test
    public void getAll_success() throws Exception {
      // Create a list of Users for testing
      List<Users> usersList = new ArrayList<>();
      usersList.add(new Users());
      usersList.add(new Users());

      // Mock the service method
      Mockito.when(usersService.getAll()).thenReturn(usersList);

      // Act & Assert
      mockMvc.perform(
        get("/users")
        .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data").isArray());
    }
    
    @Test
    public void getById_success() throws Exception {
      // Arrange: Create a sample user and retrieve its ID
      Users user = sampleUser();
      // Set user properties here...
      // Create a UUID
      UUID uuid = UUID.randomUUID();

      // Convert UUID to String
      String uuidStr = uuid.toString();
      user.setUser_id(uuid);

      Mockito.when(usersService.getById(uuid)).thenReturn(user);

      // Assert: Send a GET request to retrieve the user by ID
      mockMvc.perform(
        get("/users/{id}", uuidStr)
        .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data.user_id").value(uuidStr));
    }
  }
  @Nested
  class UpdateUsers {
    @Test
    public void update_success() throws Exception {
      // Arrange: Create a sample user and retrieve its ID
      Users user = sampleUser();
      // Set user properties here...
      // Create a UUID
      UUID uuid = UUID.randomUUID();
  
      // Convert UUID to String
      String uuidStr = uuid.toString();
      user.setUser_id(uuid);
  
      Mockito.when(usersService.update(user)).thenReturn(user);
  
      // Assert: Send a PUT request to retrieve the user by ID
      mockMvc.perform(
        put("/users/{id}", uuidStr)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(user)))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data.user_id").value(uuidStr))
          .andExpect(jsonPath("$.data.user_name").value("username"));
    }
    
  }
  
}