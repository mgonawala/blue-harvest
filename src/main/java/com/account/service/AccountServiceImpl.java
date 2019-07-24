package com.account.service;

import com.account.dto.AccountDTO;
import com.account.dto.CreateAccountRequest;
import com.account.dto.UpdateAccountRequest;
import com.account.model.Account;
import com.account.repository.AccountRepository;
import com.account.util.ResponseParser;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

  final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  @Transactional
  public AccountDTO createNewAccount(CreateAccountRequest createAccountRequest) {
    Account save =
        accountRepository.save(ResponseParser.parseCreateAccountRequest(createAccountRequest));
    return ResponseParser.parseAccounts(save);
  }

  @Override
  @Transactional
  public List<AccountDTO> findAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return ResponseParser.parseAccounts(accounts);
  }

  @Override
  @Transactional
  public AccountDTO findById(Long id) {
    Optional<Account> byId = accountRepository.findById(id);
    if (byId.isPresent()) {
      return ResponseParser.parseAccounts(byId.get());
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  @Transactional
  public AccountDTO updateAccountInfo(UpdateAccountRequest updateAccountRequest, Long id) {
    Optional<Account> byId = accountRepository.findById(id);
    if (byId.isPresent()) {
      updateAccountRequest.getAccountType().ifPresent(byId.get()::setAccountType);
      updateAccountRequest.getAccountBalance().ifPresent(byId.get()::setAccountBalance);
      Account save = accountRepository.save(byId.get());
      return ResponseParser.parseAccounts(save);
    } else {
      throw new EntityNotFoundException();
    }
  }
}
