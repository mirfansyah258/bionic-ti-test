package id.bti.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

import id.bti.test.model.InventoryProduct;
import id.bti.test.repository.InventoryProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryProductService {

  @Autowired
  private InventoryProductRepository invProductRepository;

  public InventoryProduct insert(InventoryProduct invProduct) throws Exception {
    String productType = checkProductType(invProduct.getProductType());
    invProduct.setProductType(productType);

    return invProductRepository.save(invProduct);
  }

  public Iterable<InventoryProduct> getAll() {
    return invProductRepository.findAll();
  }
  
  public InventoryProduct getById(UUID id) throws Exception {
    InventoryProduct inv = invProductRepository.findById(id).orElse(null);
    if (inv != null) {
      return inv;
    }
    throw new Exception("Inventory product with ID " + id + " not found.");
  }

  public InventoryProduct update(InventoryProduct invProduct) throws Exception {
    InventoryProduct inv = getById(invProduct.getProductId());

    String productType = checkProductType(invProduct.getProductType());
    
    inv.setProductName(invProduct.getProductName());
    inv.setQuantity(invProduct.getQuantity());
    inv.setProductType(productType);

    return invProductRepository.save(inv);
  }

  private String checkProductType(String productType) throws Exception {
    List<String> productTypes = List.of("product", "tools", "materials");

    if (!productTypes.contains(productType.toLowerCase())) {
      throw new Exception("Product type is not valid");
    }
    return productType.toLowerCase();
  }
}
