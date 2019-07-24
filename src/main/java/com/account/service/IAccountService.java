package com.account.service;

import com.account.dto.AccountDTO;
import com.account.dto.CreateAccountRequest;
import com.account.dto.UpdateAccountRequest;
import java.util.List;

public interface IAccountService {

  AccountDTO createNewAccount(CreateAccountRequest createAccountRequest);

  List<AccountDTO> findAllAccounts();

  AccountDTO findById(Long id);

  AccountDTO updateAccountInfo(UpdateAccountRequest updateAccountRequest, Long id);
}
