package id.bti.test.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, insertable = false, updatable = false, nullable = false)
  private UUID transactionId;

  @NotNull
  @Min(value = 1, message = "quantity must be at least 1")
  @Column(nullable = false)
  private Integer quantity;

  @NotBlank
  @Column(length = 16, nullable = false) // purchase, sell, borrow, using-material
  private String transactionType;

  private UUID referenceId;

  @Column(nullable = false)
  private LocalDateTime transactionDate;

  @ManyToOne(targetEntity = InventoryProduct.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "productId", nullable = false)
  @JsonIgnore
  private InventoryProduct inventoryProduct;

  @PrePersist
  protected void onCreate() {
    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
    transactionDate = now.toLocalDateTime();
  }
}
