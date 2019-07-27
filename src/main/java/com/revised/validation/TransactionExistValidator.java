package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Transaction;
import com.revised.repository.TransactionRepository;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionExistValidator implements Function<Long, Transaction> {

  TransactionRepository transactionRepository;

  @Autowired
  public TransactionExistValidator(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(Long id) {
    return transactionRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Transaction is not valid:" + id));
  }
}
