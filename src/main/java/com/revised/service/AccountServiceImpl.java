package com.revised.service;

import com.revised.exception.AccountAlreadyExistsException;
import com.revised.exception.ResourceNotFoundException;
import com.revised.model.Account;
import com.revised.model.Customer;
import com.revised.model.Transaction;
import com.revised.model.TransactionType;
import com.revised.repository.AccountRepository;
import com.revised.repository.CustomerRepository;
import com.revised.validation.AccountExistsValidator;
import com.revised.validation.CustomerExistValidation;
import com.revised.validation.strategy.IValidationStrategy;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * AccountService carries business logic for operations allowed on Account resource.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

  /** Dependencies * */

  /** Logger * */
  private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
  public static final String CUSTOMER_VALIDATION_TRUE = "Customer validation: true";

  /** Account repository for Dao access * */
  @Autowired private AccountRepository accountRepository;

  /** Customer repository for Dao access * */
  @Autowired private CustomerRepository customerRepository;

  /** Transaction service * */
  @Autowired private ITransactionService transactionService;

  /** Validation checking Existing Customer * */
  @Autowired private CustomerExistValidation customerExistValidation;

  /** Validation checking Existing Account * */
  @Autowired private AccountExistsValidator accountExistsValidator;

  /**
   * Validation Strategy to execute before creation of new account. It's Strategy is built in spring
   * config file. Please refer config file to check which validations are configured in this
   * strategy.
   *
   * <p>*
   */
  @Autowired
  @Qualifier("createAccount")
  private IValidationStrategy<Account, Customer> createAccountValidationStrategy;

  /**
   * Method to find all accounts of given customer.
   *
   * @param id Customer id
   * @return Returns list of Accounts
   * @throws ResourceNotFoundException
   */
  @Override
  public List<Account> findAllAccountsOfCustomer(Long id) {
    Customer customer = customerExistValidation.apply(id);
    logger.info(CUSTOMER_VALIDATION_TRUE);
    List<Account> accountList = customer.getAccountList();
    logger.info("Accounts List:", accountList.size());
    logger.debug("Accounts:{}", accountList);
    return accountList;
  }

  /**
   * Method to create a new account after validating each validation of configured strategy.
   *
   * @param account New Account Details
   * @param customerId Customer Id.
   * @return Returns newly created Account details.
   * @throws AccountAlreadyExistsException
   */
  @Override
  public Account createNewAccount(Account account, Long customerId)
       {

    Customer cus = customerExistValidation.apply(customerId);
    logger.info(CUSTOMER_VALIDATION_TRUE);
    if (createAccountValidationStrategy.isValid(account, cus)) {
      logger.info("Create Account constrains are met.");
      account.setCustomer(cus);
      account = accountRepository.save(account);
      if (account.getBalance() > 0) {
        Transaction initial = new Transaction();
        initial.setAccount(account);
        initial.setAmount(account.getBalance());
        initial.setType(TransactionType.INITIAL);
        transactionService.commitTransaction(initial, account.getId());
        logger.info("Transaction commited for initial balance:{}", account.getBalance());
      }
      logger.debug("Account Response:{}", account);
      return account;
    } else {
      logger.debug(
          "Account:{} Already exists for given customer:{}", account.getType(), customerId);
      throw new AccountAlreadyExistsException("Account already exists for customer:" + customerId);
    }
  }

  /**
   * Method to delete given account details.
   *
   * @param accountId Account Id
   * @param customerId Customer Id
   * @throws ResourceNotFoundException
   */
  @Override
  public void deleteAccount(Long accountId, Long customerId) {
    customerExistValidation.apply(customerId);
    logger.info(CUSTOMER_VALIDATION_TRUE);
    Account account = accountExistsValidator.apply(accountId);
    logger.info("Account validation: true");
    accountRepository.delete(account);
    logger.info("Account deleted:true");
  }

  @Override
  public void deleteAccount(Long accountId) {
    accountExistsValidator.apply(accountId);
    logger.info("Account validation: true");
    accountRepository.deleteById(accountId);
    logger.info("Account deleted:true");
  }

  /**
   * Method to find Account details of given account.
   *
   * @param id Account Id
   * @return Returns Account Details
   * @throws ResourceNotFoundException
   */
  @Override
  public Account findAccountById(Long id)  {
    return accountExistsValidator.apply(id);
  }

  /**
   * Method to find all available accounts.
   *
   * @return List of accounts.
   */
  @Override
  public List<Account> findAllAccounts() {
    return accountRepository.findAll();
  }

  public IValidationStrategy<Account, Customer> getCreateAccountValidationStrategy() {
    return createAccountValidationStrategy;
  }

  @Override
  public void setCreateAccountValidationStrategy(
      IValidationStrategy createAccountValidationStrategy) {
    this.createAccountValidationStrategy = createAccountValidationStrategy;
  }
}
