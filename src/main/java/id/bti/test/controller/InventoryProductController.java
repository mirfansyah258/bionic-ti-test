package id.bti.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import id.bti.test.helpers.ResponseHandler;
import id.bti.test.model.InventoryProduct;
import id.bti.test.service.InventoryProductService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/inventoryProduct")
public class InventoryProductController { 
  @Autowired
  private InventoryProductService inventoryProductService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody InventoryProduct invProduct) {
    try {
      InventoryProduct inv = inventoryProductService.insert(invProduct);
      return ResponseHandler.generateResponse(HttpStatus.CREATED, "Create inventory product data success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @GetMapping()
  public ResponseEntity<Object> getAll() {
    Iterable<InventoryProduct> inv = inventoryProductService.getAll();
    return ResponseHandler.generateResponse(HttpStatus.OK, "GetAll inventory product data success", inv);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getById(@PathVariable("id") UUID id) {
    try {
      InventoryProduct inv = inventoryProductService.getById(id);
      return ResponseHandler.generateResponse(HttpStatus.OK, "GetById inventory product data success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
    
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody InventoryProduct invProduct) {
    invProduct.setProductId(id);
    try {
      InventoryProduct usr = inventoryProductService.update(invProduct);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Update inventory product data success", usr);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }
  
}
