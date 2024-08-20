package id.bti.test.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
  private UUID productId;

  @NotNull
  @Min(value = 1, message = "quantity must be at least 1")
  private Integer quantity;

  private UUID referenceId;
  private LocalDateTime transactionDate;
}
