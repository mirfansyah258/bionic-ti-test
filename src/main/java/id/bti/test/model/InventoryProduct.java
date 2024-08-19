package id.bti.test.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventoryproduct")
public class InventoryProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, insertable = false, updatable = false, nullable = false)
  private UUID productId;

  @NotBlank
  @Column(unique = true, length = 32, nullable = false)
  private String productName;

  @NotNull
  @Min(value = 0, message = "quantity must be at least 0")
  @Column(nullable = false)
  private Integer quantity;

  @NotBlank
  @Column(length = 16, nullable = false) // products, tools, materials
  private String productType;
}
