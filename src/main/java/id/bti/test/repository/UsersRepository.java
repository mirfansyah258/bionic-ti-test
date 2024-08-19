package id.bti.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import id.bti.test.model.Users;

public interface UsersRepository extends JpaRepository<Users, UUID> {
  
}
