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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * TransactionService carries business logic for operations allowed on Transaction resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);

  /** TransactionRepository for DAO access* */
  @Autowired private TransactionRepository transactionRepository;

  /** Account Repository for DAO access* */
  @Autowired private AccountRepository accountRepository;

  /** Validator to check valid account * */
  @Autowired private AccountExistsValidator accountExistsValidator;

  /** validator to check valid transaction * */
  @Autowired private TransactionExistValidator transactionExistValidator;

  /**
   * Validation Strategy to execute before reverting transaction. It's Strategy is built in spring
   * config file. Please refer config file to check which validations are configured in this
   * strategy.
   *
   * <p>*
   */
  @Autowired
  @Qualifier("revertTransaction")
  private IValidationStrategy revertTransactionValidation;

  /**
   * Validation Strategy to execute creating new transaction. It's Strategy is built in spring
   * config file. Please refer config file to check which validations are configured in this
   * strategy.
   *
   * <p>*
   */
  @Autowired
  @Qualifier("newTransaction")
  private IValidationStrategy newTransactionStrategy;

  /** OperationFactory to return specific Operation object. */
  @Autowired private OperationFactory operationFactory;

  /**
   * Method to find all transaction of given account.
   *
   * @param id Account Id
   * @return List transactions.
   */
  @Override
  public List<Transaction> getAllTransactionOfAccount(Long id) {
    accountExistsValidator.apply(id);
    return transactionRepository.findAllByAccount_Id(id);
  }

  /**
   * Method to commit new transaction.
   *
   * @param transaction Transaction details to commit
   * @param accountId Account id
   * @return Returns new Transaction details
   */
  @Override
  public Transaction commitTransaction(Transaction transaction, Long accountId) {
    Account account = accountExistsValidator.apply(accountId);
    logger.info("Account validation:true");
    if (newTransactionStrategy.isValid(account, transaction)) {
      Transaction result =
          operationFactory
              .getOperation(transaction.getType(), transactionRepository, accountRepository)
              .apply(transaction, account);
      logger.info("Transaction commited:{}", result.getId());
      logger.debug("Transaction result:{}", result);
      return result;
    } else {
      logger.error("Not enough balance in Account to carry out transaction");
      throw new NotEnoughBalanceException("Account balance is not enough:");
    }
  }

  /**
   * Method to revert given transaction detail.
   *
   * @param transactionId Transaction Id
   * @param accountId Account Id
   * @return Returns reverted transaction information.
   */
  @Override
  public Transaction revertTransaction(Long transactionId, Long accountId) {

    Account account = accountExistsValidator.apply(accountId);
    logger.info("Account valid:true");
    Transaction transaction = transactionExistValidator.apply(transactionId);
    logger.info("Transaction valid:true");
    if (revertTransactionValidation.isValid(account, transaction)) {
      logger.info("All conditions to carry revert transaction are met.");
      if (transaction.getType().equals(TransactionType.CREDIT)
          || transaction.getType().equals(TransactionType.INITIAL)) {
        account.setBalance(account.getBalance() - transaction.getAmount());
      } else {
        account.setBalance(account.getBalance() + transaction.getAmount());
      }
      accountRepository.save(account);
      transaction.setStatus(TxStatus.REVERT);
      Transaction result = transactionRepository.save(transaction);
      logger.info("Transaction reverted:{}", result.getId());
      logger.debug("Transaction result:{}", result);
      return result;
    } else {
      logger.error("Can not revert transaction:{}", transactionId);
      throw new NotEnoughBalanceException("Can not revert transaction:");
    }
  }

  public void setRevertTransactionValidation(IValidationStrategy revertTransactionValidation) {
    this.revertTransactionValidation = revertTransactionValidation;
  }
}
