package transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dlmartin.transaction.model.dao.ITransactionDAO;
import dlmartin.transaction.model.entities.Transaction;
import dlmartin.transaction.model.exceptions.AlreadyExistException;
import dlmartin.transaction.model.exceptions.DataNotValidException;
import dlmartin.transaction.services.TransactionSrv;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TransactionSrv.class)
@AutoConfigureMockMvc
public class TransactionSrvTest {

	@Autowired
	private TransactionSrv transactionSrv;
	
	@MockBean
	private ITransactionDAO transactionDao;
	
	private static final String TRANSACTION_REFERENCE = "12345A";
	private static final String TRANSACTION_IBAN = "ES9820385778983000760236";
	private static final String TRANSACTION_DATE = "2019-07-16T16:55:42.000Z";
	private static final LocalDateTime TRANSACTION_LOCAL_DATE = LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME);
	private static final Float TRANSACTION_AMOUNT = 193.38f;
	private static final Float TRANSACTION_FEE = 3.18f;
	private static final String TRANSACTION_DESCRIPTION = "Restaurant payment";
	
	private static final Float TRANSACTION_AMOUNT_INVALID = null;
	private static final String TRANSACTION_IBAN_INVALID = null;
	
	private static final String TRANSACTION_SEARCH_IBAN = "ES9820385778983000760236";
	private static final String TRANSACTION_SEARCH_ORDER_INVALID = "TEST";
	
	@Test
	public void createTransaction_OK()  throws AlreadyExistException, DataNotValidException {
		
		Transaction transaction = new Transaction();
		transaction.setReference(TRANSACTION_REFERENCE);
		transaction.setAccountIban(TRANSACTION_IBAN);
		transaction.setAmount(TRANSACTION_AMOUNT);
		transaction.setDate(TRANSACTION_LOCAL_DATE);
		transaction.setDescription(TRANSACTION_DESCRIPTION);
		transaction.setFee(TRANSACTION_FEE);
		
		Optional<Transaction> opt = Optional.empty();
		
		Mockito.when(transactionDao.findById(TRANSACTION_REFERENCE)).thenReturn(opt);		
		Mockito.when(transactionDao.save(Mockito.any())).thenReturn(transaction);
		
		String transactionReference = transactionSrv.createTransaction(TRANSACTION_REFERENCE, 
				TRANSACTION_IBAN, TRANSACTION_DATE, TRANSACTION_AMOUNT, TRANSACTION_FEE, TRANSACTION_DESCRIPTION);
		
		assertEquals(transaction.getReference(), transactionReference);
	}
	
	@Test
	public void createTransaction_AlreadyExistException()  {
		
		Assertions.assertThrows(AlreadyExistException.class, () -> {
			Transaction transaction = new Transaction();
			transaction.setReference(TRANSACTION_REFERENCE);
			transaction.setAccountIban(TRANSACTION_IBAN);
			transaction.setAmount(TRANSACTION_AMOUNT);
			transaction.setDate(LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME));
			transaction.setDescription(TRANSACTION_DESCRIPTION);
			transaction.setFee(TRANSACTION_FEE);
			Optional<Transaction> opt = Optional.of(transaction);
			
			Mockito.when(transactionDao.findById(TRANSACTION_REFERENCE)).thenReturn(opt);		
						
			transactionSrv.createTransaction(TRANSACTION_REFERENCE, 
				TRANSACTION_IBAN, TRANSACTION_DATE, TRANSACTION_AMOUNT, TRANSACTION_FEE, TRANSACTION_DESCRIPTION);
		  });
	}
	
	@Test
	public void createTrasnaction_DataNotValidException_By_IBAN()  {
		
		Assertions.assertThrows(DataNotValidException.class, () -> {
		    transactionSrv.createTransaction(TRANSACTION_REFERENCE, 
					TRANSACTION_IBAN_INVALID, TRANSACTION_DATE, TRANSACTION_AMOUNT, TRANSACTION_FEE, TRANSACTION_DESCRIPTION);
		  });
	}
	
	@Test
	public void createTransaction_DataNotValidException_By_Amount()  {
		
		Assertions.assertThrows(DataNotValidException.class, () -> {
		    transactionSrv.createTransaction(TRANSACTION_REFERENCE, 
					TRANSACTION_IBAN, TRANSACTION_DATE, TRANSACTION_AMOUNT_INVALID, TRANSACTION_FEE, TRANSACTION_DESCRIPTION);
		  });
	}
	
	@Test
	public void findTransactions_with_iban_without_order()
			throws DataNotValidException{
		
		Transaction transaction = new Transaction();
		transaction.setReference(TRANSACTION_REFERENCE);
		transaction.setAccountIban(TRANSACTION_IBAN);
		transaction.setAmount(TRANSACTION_AMOUNT);
		transaction.setDate(LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME));
		transaction.setDescription(TRANSACTION_DESCRIPTION);
		transaction.setFee(TRANSACTION_FEE);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
		
		Mockito.when(transactionDao.findByAccountIban(TRANSACTION_SEARCH_IBAN)).thenReturn(transactionList);
		
		List<Transaction> resultList = transactionSrv.findTransactions(TRANSACTION_SEARCH_IBAN, null);
		
		assertEquals(transactionList.get(0), resultList.get(0));
	}
	
	@Test
	public void findTransactions_with_iban_with_asc_order()
			throws DataNotValidException{
		
		Transaction transaction = new Transaction();
		transaction.setReference(TRANSACTION_REFERENCE);
		transaction.setAccountIban(TRANSACTION_IBAN);
		transaction.setAmount(TRANSACTION_AMOUNT);
		transaction.setDate(LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME));
		transaction.setDescription(TRANSACTION_DESCRIPTION);
		transaction.setFee(TRANSACTION_FEE);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
		
		Mockito.when(transactionDao.findByAccountIbanByOrderByAmountAsc(TRANSACTION_SEARCH_IBAN)).thenReturn(transactionList);
		
		List<Transaction> resultList = transactionSrv.findTransactions(TRANSACTION_SEARCH_IBAN, TransactionSrv.ASC_ORDER);
		
		assertEquals(transactionList.get(0), resultList.get(0));
	}
	
	@Test
	public void findTransactions_with_iban_with_desc_order()
			throws DataNotValidException{
		
		Transaction transaction = new Transaction();
		transaction.setReference(TRANSACTION_REFERENCE);
		transaction.setAccountIban(TRANSACTION_IBAN);
		transaction.setAmount(TRANSACTION_AMOUNT);
		transaction.setDate(LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME));
		transaction.setDescription(TRANSACTION_DESCRIPTION);
		transaction.setFee(TRANSACTION_FEE);
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
		
		Mockito.when(transactionDao.findByAccountIbanByOrderByAmountDesc(TRANSACTION_SEARCH_IBAN)).thenReturn(transactionList);
		
		List<Transaction> resultList = transactionSrv.findTransactions(TRANSACTION_SEARCH_IBAN, TransactionSrv.DESC_ORDER);
		
		assertEquals(transactionList.get(0), resultList.get(0));
	}
	
	@Test
	public void findTransactions_with_iban_with_invalid_order()
			throws DataNotValidException{
		
		Assertions.assertThrows(DataNotValidException.class, () -> {
			transactionSrv.findTransactions(TRANSACTION_SEARCH_IBAN, TRANSACTION_SEARCH_ORDER_INVALID);
		  });
	}
}
