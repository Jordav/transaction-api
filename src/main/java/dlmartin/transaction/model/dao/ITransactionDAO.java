package dlmartin.transaction.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dlmartin.transaction.model.entities.Transaction;

public interface ITransactionDAO extends CrudRepository<Transaction, String>{

	 public List<Transaction> findAllByOrderByAmountDesc();
	 public List<Transaction> findAllByOrderByAmountAsc();
	 public List<Transaction> findByAccountIban(String accountIban);
	 public List<Transaction> findByAccountIbanOrderByAmountDesc(String accountIban);
	 public List<Transaction> findByAccountIbanOrderByAmountAsc(String accountIban);
}
