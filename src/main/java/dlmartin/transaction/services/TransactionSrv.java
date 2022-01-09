package dlmartin.transaction.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dlmartin.transaction.model.dao.ITransactionDAO;
import dlmartin.transaction.model.entities.Transaction;
import dlmartin.transaction.model.exceptions.AlreadyExistException;
import dlmartin.transaction.model.exceptions.DataNotValidException;
import dlmartin.transaction.model.exceptions.NotFoundException;

/**
 * Service's layer class that encapsulates the business logic of the transaction
 * in the application
 * 
 * @author David Lopez Martin
 */
@Component
public class TransactionSrv {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSrv.class);

	public static final String CLIENT_CHANNEL = "CLIENT";
	public static final String ATM_CHANNEL = "ATM";
	public static final String INTERNAL_CHANNEL = "INTERNAL";
	
	public static final String PENDING_STATUS = "PENDING";
	public static final String SETTLED_STATUS = "SETTLED";
	public static final String FUTURE_STATUS = "FUTURE";
	public static final String INVALID_STATUS = "INVALID";
	
	
	/**
	 * Ascending order for searches
	 */
	public static final String ASC_ORDER = "ASC";
	
	/**
	 * Descending order for searches
	 */
	public static final String DESC_ORDER = "DESC";
	
	
	/**
	 * Access to the data access layer
	 */
	@Autowired
	private ITransactionDAO transactionDao;
	
	/**
	 * Method responsible for creating a new transaction
	 * 
	 * @param reference The transaction unique reference number in our system. If not present, the system will generate one.
	 * @param accountIban The IBAN number of the account where the transaction has happened.
	 * @param date Date when the transaction took place
	 * @param amount If positive the transaction is a credit (add money) to the account. If negative it is a debit (deduct money from the account)
	 * @param fee Fee that will be deducted from the amount, regardless on the amount being positive or negative.
	 * @param description The description of the transaction
	 * @return The transaction reference
	 * @throws AlreadyExistException Transaction reference already exists
	 * @throws DataNotValidException Mandatory fields not exists
	 */
	public String createTransaction(String reference, String accountIban, String date, 
			Float amount, Float fee, String description) throws AlreadyExistException, DataNotValidException {

		LOGGER.debug("createTransaction begins with reference " + reference + ", accountIban " + accountIban + ", date: " + date +
				", amount: " + amount + ", fee " + fee + ", description: " + description);

		String result = null;
		
		// If reference is not present, the system will generate one.
		if(null == reference) {
			reference = UUID.randomUUID().toString();
		}		

		if (transactionDao.findById(reference).isEmpty()) {
			if (null != accountIban && null != amount) {
				Transaction transaction = new Transaction();
				transaction.setReference(reference);
				transaction.setAmount(amount);
				transaction.setAccountIban(accountIban);
				transaction.setDate(LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME));
				transaction.getFee();
				transaction.setDescription(description);
				transaction = transactionDao.save(transaction);
				result = transaction.getReference();
				
				LOGGER.debug("createTransaction ends with result " + result);
				return result;
			} else {
				throw new DataNotValidException();
			}
		} else {
			throw new AlreadyExistException();
		}
	}

	/**
	 * Method in charge of searching for transactions that meet the conditions specified in the parameters
	 * 
	 * @param accountIban The IBAN number of the account where the transaction
	 * @param order Sort by amount (ascending/descending)
	 * @return List of transactions
	 * @throws DataNotValidException order parameter with invalid value
	 */
	public List<Transaction> findTransactions(String accountIban, String order)
			throws DataNotValidException {

		LOGGER.debug("findTransactions begins with accountIban " + accountIban + ", order " + order);

		List<Transaction> result = null;
		
		// Checks if account iban is defined
		if(null != accountIban) {
			
			if (null == order) {
				result = transactionDao.findByAccountIban(accountIban);
			} else if (ASC_ORDER.equals(order)) {
				result = transactionDao.findByAccountIbanOrderByAmountAsc(accountIban);
			} else if (DESC_ORDER.equals(order)) {
				result = transactionDao.findByAccountIbanOrderByAmountDesc(accountIban);
			} else {
				throw new DataNotValidException();
			}
			
		} else {
			
			if(null == order) {
				result = new ArrayList<Transaction>();
				transactionDao.findAll().forEach(result::add);
			} else if(ASC_ORDER.equals(order)) {
				result = transactionDao.findAllByOrderByAmountAsc();
			} else if(DESC_ORDER.equals(order)) {
				result = transactionDao.findAllByOrderByAmountDesc();
			} else {
				throw new DataNotValidException();
			}
		}		
		
		LOGGER.debug("findTransactions ends with result " + result);
		return result;
	}
	
	/**
	 * Method that retrieves the transaction with the reference passed as parameters
	 * 
	 * @param reference Transaction reference number
	 * @return Transaction with the reference
	 * @throws DataNotValidException reference parameter with invalid value
	 * @throws NotFoundException Transaction with the reference passed as parameter not exists
	 */
	public Transaction getTransaction(String reference) 
			throws DataNotValidException, NotFoundException {
		
		LOGGER.debug("getTransaction begins with reference " + reference);
		
		if(null != reference) {
			Optional<Transaction> transactionOpt = transactionDao.findById(reference);
			
			if (!transactionOpt.isEmpty()) {
				return transactionOpt.get();
				
			} else {
				throw new NotFoundException();
			}
		} else {
			throw new DataNotValidException();
		}
		
	}
}
