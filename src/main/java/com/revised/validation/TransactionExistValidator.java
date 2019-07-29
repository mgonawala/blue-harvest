package com.revised.validation;

import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Transaction;
import com.revised.repository.TransactionRepository;
import java.util.function.Function;
import java.util.function.LongFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Function to check if given Transaction is valid or not. It returns true if valid else throws
 * ResourceNotFoundException..
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Component
public class TransactionExistValidator implements LongFunction<Transaction> {

  /** Transaction repository for DAO Access * */
  TransactionRepository transactionRepository;

  @Autowired
  public TransactionExistValidator(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction apply(long id) {
    return transactionRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction is not valid:" + id));
  }
}
