package com.revised.service;

import com.revised.exception.NotEnoughBalanceException;
import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.model.TxStatus;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import com.revised.validation.AccountExistsValidator;
import com.revised.validation.OperationFactory;
import com.revised.validation.TransactionExistValidator;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountExistsValidator accountExistsValidator;

  @Autowired
  private TransactionExistValidator transactionExistValidator;

  @Autowired
  @Qualifier("revertTransaction")
  private IValidationStrategy revertTransactionValidation;

  @Autowired
  @Qualifier("newTransaction")
  private IValidationStrategy newTransactionStrategy;

  @Autowired
  private OperationFactory operationFactory;

  @Override
  public List<Transaction> getAllTransactionOfAccount(Long id) {
    accountExistsValidator.apply(id);
    return transactionRepository.findAllByAccount_Id(id);
  }

  @Override
  public Transaction commitTransaction(Transaction transaction, Long accountId) {
    Account account = accountExistsValidator.apply(accountId);
    if(newTransactionStrategy.isValid(account,transaction))
    return operationFactory.
        getOperation(transaction.getType(), transactionRepository, accountRepository)
        .apply(transaction, account);
    else
      throw new NotEnoughBalanceException("Account balance is not enough:");
  }

  @Override
  public Transaction revertTransaction(Long transactionId, Long accountId) {

    Account account = accountExistsValidator.apply(accountId);
    Transaction transaction = transactionExistValidator.apply(transactionId);

    if (revertTransactionValidation.isValid(account, transaction)) {
      if (transaction.getType().equals(TransactionType.CREDIT)) {
        account.setBalance(account.getBalance() - transaction.getAmount());
      } else {
        account.setBalance(account.getBalance() + transaction.getAmount());
      }
      accountRepository.save(account);
      transaction.setStatus(TxStatus.REVERT);
      return transactionRepository.save(transaction);
    } else {
      throw new NotEnoughBalanceException("Can not revert transaction:");
    }
  }

  public void setRevertTransactionValidation(
      IValidationStrategy revertTransactionValidation) {
    this.revertTransactionValidation = revertTransactionValidation;
  }
}
