package dlmartin.transaction.model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import dlmartin.transaction.services.TransactionSrv;

/**
 * Class that represents transaction object in the application's model
 * 
 * @author David Lopez Martin
 */
@Entity
@Table(name = "Transaction")
public class Transaction {

	/**
	 * Transaction reference
	 */
	@Id
	private String reference;
	
	/**
	 * Account IBAN asociated to 
	 */
	@Column(length = 24, nullable = false)
	private String accountIban;
	
	/**
	 * Date of transaction
	 */
	private LocalDateTime date;
	
	/**
	 * Amount of transaction
	 */
	@Column(name = "amount", nullable = false)
	private Float amount;
	
	/**
	 * Fee of transaction
	 */
	private Float fee;
	
	/**
	 * Description of transaction
	 */
	@Column(name = "description", length = 250, nullable = false)
	private String description;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccountIban() {
		return accountIban;
	}

	public void setAccountIban(String accountIban) {
		this.accountIban = accountIban;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Float getAmount() {
		return amount;
	}
	
	/**
	 * Retrieves amount value depending of channel parameter value
	 * 
	 * @param channel Channel that call the method 
	 * @return Amount value
	 */
	public Float getAmount(String channel) {
		switch (channel) {
		case TransactionSrv.CLIENT_CHANNEL:
		case TransactionSrv.ATM_CHANNEL:
			return this.getAmount() - this.getFee();
		case TransactionSrv.INTERNAL_CHANNEL:
			return this.getAmount();
		default:
			return this.getAmount();
		}
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getFee() {
		return fee;
	}
	
	/**
	 * Retrieves fee value depending of channel parameter value
	 * 
	 * @param channel Channel that call the method 
	 * @return Fee value
	 */
	public Float getFee(String channel) {
		switch (channel) {
		case TransactionSrv.CLIENT_CHANNEL:
		case TransactionSrv.ATM_CHANNEL:
			return null;
		case TransactionSrv.INTERNAL_CHANNEL:
			return this.getFee();
		default:
			return this.getFee();
		}
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Calculate status of transaction depending of channel parameter value
	 * and the transaction date
	 * 
	 * @param channel Channel that call the method 
	 * @return Status value
	 */
	public String getStatus(String channel) {
		
		if(this.getDate().toLocalDate().isBefore(LocalDate.now())) {
			return TransactionSrv.SETTLED_STATUS;					
		} else if (this.getDate().toLocalDate().isEqual(LocalDate.now())) {
			return TransactionSrv.PENDING_STATUS;
		} else if (this.getDate().toLocalDate().isAfter(LocalDate.now())) {
			
			switch (channel) {
			case TransactionSrv.CLIENT_CHANNEL:
			case TransactionSrv.INTERNAL_CHANNEL:
				return TransactionSrv.FUTURE_STATUS;
			case TransactionSrv.ATM_CHANNEL:
				return TransactionSrv.PENDING_STATUS;
			default:
				return TransactionSrv.FUTURE_STATUS;
			}
		}
		
		return TransactionSrv.INVALID_STATUS;
		
	}

	@Override
	public String toString() {
		return "Transaction [reference=" + reference + ", accountIban=" + accountIban + ", date=" + date + ", amount="
				+ amount + ", fee=" + fee + ", description=" + description + "]";
	}
	
	
}