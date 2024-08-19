package id.bti.test.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, insertable = false, updatable = false, nullable = false)
  private UUID user_id;

  @NotBlank
  @Column(unique = true, length = 16, nullable = false)
  private String user_name;

  @NotBlank
  @Column(length = 64, nullable = false)
  private String password;

  @NotBlank
  @Column(length = 16, nullable = false)
  private String role;
}
