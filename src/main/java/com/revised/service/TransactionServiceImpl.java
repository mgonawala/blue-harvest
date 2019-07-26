package com.revised.service;

import com.revised.exception.NotEnoughBalanceException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.model.Transaction;
import com.revised.model.TxStatus;
import com.revised.repository.AccountRepository;
import com.revised.repository.TransactionRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public List<Transaction> getAllTransactionOfAccount(Long id) {
    return Optional.of(transactionRepository.findAllByAccount_Id(id))
        .map(acc -> acc)
        .orElseThrow(() -> new ResourceNotFoundException("Account Id:" + id));
  }

  @Override
  public Transaction commitTransaction(Transaction transaction, Long accountId) {
    Account account = accountRepository.findById(accountId).map(account1 -> account1
    ).orElseThrow(() -> new ResourceNotFoundException("Account" + accountId));

    double balance = account.getBalance();
    switch (transaction.getType()) {
      case DEBIT: {
        if (balance - transaction.getAmount() >= 0) {
          transaction.setAccount(account);
          transaction = transactionRepository.save(transaction);
          account.setBalance(balance - transaction.getAmount());
          account = accountRepository.save(account);
        } else {
          throw new NotEnoughBalanceException("Not Enough Balance");
        }
        break;
      }
      case CREDIT: {
        transaction.setAccount(account);
        transaction = transactionRepository.save(transaction);
        account.setBalance(balance + transaction.getAmount());
        account = accountRepository.save(account);
        break;
      }
      case INITIAL: {
        transaction.setAccount(account);
        transaction = transactionRepository.save(transaction);
        break;
      }
      default:
    }
    return transaction;
  }

  @Override
  public Transaction revertTransaction(Long transactionId, Long accountId) {
    Optional<Transaction> transaction = transactionRepository.findById(transactionId);
    if (transaction.isPresent()) {
      Optional<Account> account = accountRepository.findById(accountId);
      if (account.isPresent()) {
        double balance = account.get().getBalance();
        switch (transaction.get().getType()) {
          case CREDIT: {
            if (balance - transaction.get().getAmount() >= 0) {

              account.get().setBalance(balance + transaction.get().getAmount());
              accountRepository.save(account.get());
              transaction.get().setStatus(TxStatus.REVERT);
              return transactionRepository.save(transaction.get());
            } else {
              throw new NotEnoughBalanceException("Not Enough Balance. Can't revert Transaction");
            }
          }
          case DEBIT: {
            account.get().setBalance(balance + transaction.get().getAmount());
            accountRepository.save(account.get());
            transaction.get().setStatus(TxStatus.REVERT);
            return transactionRepository.save(transaction.get());
          }
          default:
            throw new NotEnoughBalanceException("Can't revert transaction");
        }
      } else {
        throw new ResourceNotFoundException("Account ID:" + accountId);
      }
    } else {
      throw new ResourceNotFoundException("Transaction ID:" + transactionId);
    }
  }
}
