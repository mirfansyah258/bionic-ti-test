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
import id.bti.test.model.InventoryProduct;
import id.bti.test.repository.InventoryProductRepository;
import id.bti.test.service.InventoryProductService;

@SpringBootTest
// @WebMvcTest(InventoryProductController.class)
@AutoConfigureMockMvc
public class InventoryProductTest {
  
  @Autowired
	private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private InventoryProductService invService;

  @MockBean
  private InventoryProductRepository invRepository;

  @InjectMocks
  private InventoryProductController invController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(invController).build();
  }

  private InventoryProduct sampleUser() {
    InventoryProduct newProduct = new InventoryProduct();

    newProduct.setProductName("coffee");
    newProduct.setProductType("product");
    newProduct.setQuantity(0);

    return newProduct;
  }

  @Nested
  class InsertInventoryProduct {
    @Test
    public void insertInventoryProduct_success() throws Exception {
      InventoryProduct newProduct = sampleUser();

      // Mock the service method
      Mockito.when(invService.insert(newProduct)).thenReturn(newProduct);

      mockMvc.perform(post("/inventoryProduct")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(newProduct)))
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.data.productName").value(newProduct.getProductName()));
    }

    @Test
    public void testInsertProductWithBlankProductName() throws Exception {
      InventoryProduct product = new InventoryProduct();
      product.setProductName("");

      // Perform a POST request to the insert endpoint
      mockMvc.perform(post("/inventoryProduct")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(product)))
              .andExpect(status().isBadRequest()); // Expect HTTP 400 Bad Request
    }
  }
  
  @Nested
  class GetInventoryProduct {
    @Test
    public void getAll_success() throws Exception {
      // Create a list of InventoryProduct for testing
      List<InventoryProduct> productList = new ArrayList<>();
      productList.add(new InventoryProduct());
      productList.add(new InventoryProduct());

      // Mock the service method
      Mockito.when(invService.getAll()).thenReturn(productList);

      // Act & Assert
      mockMvc.perform(
        get("/inventoryProduct")
        .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data").isArray());
    }
    
    @Test
    public void getById_success() throws Exception {
      // Arrange: Create a sample product and retrieve its ID
      InventoryProduct product = sampleUser();
      // Set product properties here...
      // Create a UUID
      UUID uuid = UUID.randomUUID();

      // Convert UUID to String
      String uuidStr = uuid.toString();
      product.setProductId(uuid);

      Mockito.when(invService.getById(uuid)).thenReturn(product);

      // Assert: Send a GET request to retrieve the product by ID
      mockMvc.perform(
        get("/inventoryProduct/{id}", uuidStr)
        .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data.productId").value(uuidStr));
    }
  }

  @Nested
  class UpdateInventoryProduct {
    @Test
    public void update_success() throws Exception {
      // Arrange: Create a sample product and retrieve its ID
      InventoryProduct product = sampleUser();
      // Set product properties here...
      // Create a UUID
      UUID uuid = UUID.randomUUID();
  
      // Convert UUID to String
      String uuidStr = uuid.toString();
      product.setProductId(uuid);
  
      Mockito.when(invService.update(product)).thenReturn(product);
  
      // Assert: Send a PUT request to retrieve the product by ID
      mockMvc.perform(
        put("/inventoryProduct/{id}", uuidStr)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(product)))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.data.productId").value(uuidStr))
          .andExpect(jsonPath("$.data.productName").value("coffee"));
    }
    
  }
  
}