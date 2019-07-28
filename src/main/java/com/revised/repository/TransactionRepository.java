package com.revised.repository;

import com.revised.model.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Transaction model.
 *
 * @author <a href="mailto:mohini.gonawala90@gmail.com">Mohini Gonawala</a>
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByAccount_Id(Long accountId);
}
