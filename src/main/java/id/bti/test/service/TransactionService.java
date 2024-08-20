package id.bti.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import id.bti.test.dto.TransactionDTO;
import id.bti.test.model.InventoryProduct;
import id.bti.test.model.Transaction;
import id.bti.test.repository.InventoryProductRepository;
import id.bti.test.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private InventoryProductRepository invRepository;

  public Iterable<Transaction> getAll() {
    return transactionRepository.findAll();
  }
  
  public Transaction getById(UUID id) throws Exception {
    Transaction inv = transactionRepository.findById(id).orElse(null);
    if (inv != null) {
      return inv;
    }
    throw new Exception("Inventory product with ID " + id + " not found.");
  }

  public Transaction purchasing(TransactionDTO dto) throws Exception {
    InventoryProduct inv = invRepository.getReferenceById(dto.getProductId());
    Integer stockLeft = inv.getQuantity();

    inv.setQuantity(stockLeft + dto.getQuantity());
    // invRepository.save(inv);

    Transaction transaction = new Transaction();
    transaction.setQuantity(dto.getQuantity());
    transaction.setTransactionType("purchase");
    transaction.setInventoryProduct(inv);

    return transactionRepository.save(transaction);
  }

  public Transaction sellProduct(TransactionDTO dto) throws Exception {
    InventoryProduct inv = invRepository.getReferenceById(dto.getProductId());
    Integer stockLeft = inv.getQuantity();

    if (stockLeft < dto.getQuantity()) {
      throw new Exception("Quantity of product out cannot exceed total of stock left");
    }

    inv.setQuantity(stockLeft - dto.getQuantity());
    // invRepository.save(inv);

    Transaction transaction = new Transaction();
    transaction.setQuantity(dto.getQuantity());
    transaction.setTransactionType("sell");
    transaction.setInventoryProduct(inv);

    return transactionRepository.save(transaction);
  }

  public Transaction borrowProduct(TransactionDTO dto) throws Exception {
    InventoryProduct inv = invRepository.getReferenceById(dto.getProductId());
    Integer stockLeft = inv.getQuantity();

    if (stockLeft < dto.getQuantity()) {
      throw new Exception("Quantity of product out cannot exceed total of stock left");
    }

    inv.setQuantity(stockLeft - dto.getQuantity());
    // invRepository.save(inv);

    Transaction transaction = new Transaction();
    transaction.setQuantity(dto.getQuantity());
    transaction.setTransactionType("borrow");
    transaction.setInventoryProduct(inv);
    return transactionRepository.save(transaction);
  }

  public Transaction usingMaterialProduct(TransactionDTO dto) throws Exception {
    InventoryProduct inv = invRepository.getReferenceById(dto.getProductId());
    Integer stockLeft = inv.getQuantity();

    if (stockLeft < dto.getQuantity()) {
      throw new Exception("Quantity of product out cannot exceed total of stock left");
    }

    inv.setQuantity(stockLeft - dto.getQuantity());
    // invRepository.save(inv);

    Transaction transaction = new Transaction();
    transaction.setQuantity(dto.getQuantity());
    transaction.setTransactionType("using-material");
    transaction.setInventoryProduct(inv);
    return transactionRepository.save(transaction);
  }

  public Transaction returnProduct(TransactionDTO dto) throws Exception {
    InventoryProduct inv = invRepository.getReferenceById(dto.getProductId());

    if (!StringUtils.hasText(dto.getReferenceId().toString())) {
      throw new Exception("Reference ID is empty");
    }

    Transaction ref = transactionRepository.getReferenceById(dto.getReferenceId());
    if (ref != null) {
      if (ref.getQuantity() < dto.getQuantity()) {
        throw new Exception("Quantity of product return out cannot exceed reference quantity");
      }

      Integer stockLeft = inv.getQuantity();

      List<String> trxTypes = List.of("sell", "borrow", "using-material");
      if (trxTypes.contains(ref.getTransactionType().toLowerCase())) { // return of product out transaction which is increase the quantity of the stock
        inv.setQuantity(stockLeft + dto.getQuantity());
      } else { // return of purchase transaction which is reduce the quantity of the stock
        if (stockLeft < dto.getQuantity()) {
          throw new Exception("Quantity of product return cannot exceed total of stock left");
        }
        inv.setQuantity(stockLeft - dto.getQuantity());
      }
  
      Transaction transaction = new Transaction();
      transaction.setQuantity(dto.getQuantity());
      transaction.setTransactionType("return");
      transaction.setReferenceId(dto.getReferenceId());
      transaction.setInventoryProduct(inv);
  
      return transactionRepository.save(transaction);
    }
    throw new Exception("Reference ID is not found");
  }

}
