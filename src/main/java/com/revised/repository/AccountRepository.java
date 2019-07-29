package com.revised.repository;

import com.revised.model.Account;
import com.revised.model.AccountType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Account model.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByCustomer_Id(Long id);

  Optional<Account> findByCustomer_IdAndType(Long id, AccountType accountType);
}
