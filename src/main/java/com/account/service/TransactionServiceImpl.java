package com.account.service;

import com.account.dto.CreateTransactionRequest;
import com.account.dto.TransactionDTO;
import com.account.dto.UpdateTransactionRequest;
import com.account.model.Transaction;
import com.account.repository.TransactionRepository;
import com.account.util.ResponseParser;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {

  final TransactionRepository transactionRepository;

  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public List<TransactionDTO> findAllTransactions() {
    List<Transaction> transactions = transactionRepository.findAll();
    return ResponseParser.parseTransactions(transactions);
  }

  @Override
  @Transactional
  public TransactionDTO findById(Long id) {
    Optional<Transaction> byId = transactionRepository.findById(id);
    if (byId.isPresent()) {
      return ResponseParser.parseTransactions(byId.get());
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  @Transactional
  public TransactionDTO createNewTransaction(CreateTransactionRequest createTransactionRequest) {
    Transaction transaction =
        transactionRepository.save(ResponseParser.parseCreateTransaction(createTransactionRequest));
    return ResponseParser.parseTransactions(transaction);
  }

  @Override
  @Transactional
  public TransactionDTO updateTransaction(
      UpdateTransactionRequest updateTransactionRequest, Long id) {
    Optional<Transaction> byId = transactionRepository.findById(id);
    if (byId.isPresent()) {
      updateTransactionRequest.getAmount().ifPresent(v -> byId.get().setAmount(v));
      updateTransactionRequest.getType().ifPresent(v -> byId.get().setType(v));
      Transaction save = transactionRepository.save(byId.get());
      return ResponseParser.parseTransactions(save);
    } else {
      throw new EntityNotFoundException();
    }
  }
}
