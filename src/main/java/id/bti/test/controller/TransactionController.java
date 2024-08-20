package id.bti.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import id.bti.test.dto.TransactionDTO;
import id.bti.test.helpers.ResponseHandler;
import id.bti.test.model.Transaction;
import id.bti.test.service.TransactionService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transaction")
public class TransactionController { 
  @Autowired
  private TransactionService transactionService;

  @GetMapping()
  public ResponseEntity<Object> getAll() {
    Iterable<Transaction> inv = transactionService.getAll();
    return ResponseHandler.generateResponse(HttpStatus.OK, "GetAll transaction data success", inv);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getById(@PathVariable("id") UUID id) {
    try {
      Transaction inv = transactionService.getById(id);
      return ResponseHandler.generateResponse(HttpStatus.OK, "GetById transaction data success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @PostMapping("/purchase")
  public ResponseEntity<Object> purchaseProduct(@Valid @RequestBody TransactionDTO dto) {
    try {
      Transaction inv = transactionService.purchasing(dto);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Transaction success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @PostMapping("/sell")
  public ResponseEntity<Object> sellProduct(@Valid @RequestBody TransactionDTO dto) {
    try {
      Transaction inv = transactionService.sellProduct(dto);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Transaction success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @PostMapping("/borrow")
  public ResponseEntity<Object> borrowProduct(@Valid @RequestBody TransactionDTO dto) {
    try {
      Transaction inv = transactionService.borrowProduct(dto);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Transaction success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @PostMapping("/material")
  public ResponseEntity<Object> usingMaterialProduct(@Valid @RequestBody TransactionDTO dto) {
    try {
      Transaction inv = transactionService.usingMaterialProduct(dto);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Transaction success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }

  @PostMapping("/return")
  public ResponseEntity<Object> returnProduct(@Valid @RequestBody TransactionDTO dto) {
    try {
      Transaction inv = transactionService.returnProduct(dto);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Transaction success", inv);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }
  
}
