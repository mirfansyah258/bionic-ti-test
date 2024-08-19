package id.bti.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import id.bti.test.model.Users;
import id.bti.test.repository.UsersRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsersService {
  private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

  @Autowired
  private UsersRepository usersRepository;

  private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public Users insert(Users users) {
    logger.info("plain password", users.getPassword());
    String encodedPw = passwordEncoder.encode(users.getPassword());
    users.setPassword(encodedPw);
    logger.info("encoded password", users.getPassword());

    return usersRepository.save(users);
  }

  public Iterable<Users> getAll() {
    return usersRepository.findAll();
  }
  
  public Users getById(UUID id) throws Exception {
    Users user = usersRepository.findById(id).orElse(null);
    if (user != null) {
      return user;
    }
    throw new Exception("User with ID " + id + " not found.");
  }

  public Users update(Users users) throws Exception {
    Users user = getById(users.getUser_id());
    
    logger.info("plain password", users.getPassword());
    String encodedPw = passwordEncoder.encode(users.getPassword());
    users.setPassword(encodedPw);
    logger.info("encoded password", users.getPassword());
    
    users.setUser_name(user.getUser_name());
    users.setPassword(user.getPassword());
    users.setRole(user.getRole());
    return usersRepository.save(users);
  }
}
