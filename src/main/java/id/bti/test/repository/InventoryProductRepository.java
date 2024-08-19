package id.bti.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import id.bti.test.model.InventoryProduct;

public interface InventoryProductRepository extends JpaRepository<InventoryProduct, UUID> {
  
}
