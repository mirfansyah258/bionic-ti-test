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
import id.bti.test.model.Users;
import id.bti.test.service.UsersService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UsersController { 
  @Autowired
  private UsersService usersService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody Users user) {
    Users usr = usersService.insert(user);
    return ResponseHandler.generateResponse(HttpStatus.CREATED, "Create user data success", usr);
  }

  @GetMapping()
  public ResponseEntity<Object> getAll() {
    Iterable<Users> usr = usersService.getAll();
    return ResponseHandler.generateResponse(HttpStatus.OK, "GetAll user data success", usr);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getById(@PathVariable("id") UUID id) {
    Users usr;
    try {
      usr = usersService.getById(id);
      return ResponseHandler.generateResponse(HttpStatus.OK, "GetById user data success", usr);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
    
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable("id") UUID id, @RequestBody Users user) {
    user.setUser_id(id);
    Users usr;
    try {
      usr = usersService.update(user);
      return ResponseHandler.generateResponse(HttpStatus.OK, "Update user data success", usr);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "request error", e.getMessage());
    }
  }
  
}
