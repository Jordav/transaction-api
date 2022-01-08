package dlmartin.transaction.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.openapitools.api.TransactionApi;
import org.openapitools.model.InlineObject;
import org.openapitools.model.InlineResponse200;
import org.openapitools.model.InlineResponse2001;
import org.openapitools.model.InlineResponse2002;
import org.openapitools.model.InlineResponse200Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import dlmartin.transaction.model.entities.Transaction;
import dlmartin.transaction.model.exceptions.AlreadyExistException;
import dlmartin.transaction.model.exceptions.DataNotValidException;
import dlmartin.transaction.model.exceptions.NotFoundException;
import dlmartin.transaction.services.TransactionSrv;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-08T16:48:59.197493700+01:00[Europe/Madrid]")
@Controller
@RequestMapping("${openapi.transMng.base-path:}")
public class TransactionApiController implements TransactionApi {

    private final NativeWebRequest request;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionApiController.class);
    
    @Autowired
    private TransactionSrv transactionSrv;

    @Override
	public ResponseEntity<InlineResponse200> transactionGet(@Valid String accountIban, @Valid String sort) {
    	LOGGER.debug("transactionGet begins with accountIban " + accountIban + ", sort " + sort);
		
		try {
			
			List<Transaction> transactions = transactionSrv.findTransactions(accountIban, sort); 
			InlineResponse200 response = new InlineResponse200();
			response.setTransactions(transaction2InlineResponse(transactions));
			return new ResponseEntity<InlineResponse200>(response, HttpStatus.OK);
		} catch (DataNotValidException ex) {
			LOGGER.debug("Data not valid", ex);
			return new ResponseEntity<InlineResponse200>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception ex) {
			LOGGER.error("Unexpected API error", ex);
			return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<InlineResponse2001> transactionPost(@Valid InlineObject inlineObject) {
		LOGGER.debug("transactionPost begins with inlineObject " + inlineObject);
		
		try {
			
			String transactionRef = transactionSrv.createTransaction(inlineObject.getReference(), inlineObject.getAccountIban(),
					inlineObject.getDate(), inlineObject.getAmount(), inlineObject.getFee(), inlineObject.getDescription()); 
			InlineResponse2001 response = new InlineResponse2001();
			response.setReference(transactionRef);
			return new ResponseEntity<InlineResponse2001>(response, HttpStatus.OK);
		} catch (DataNotValidException ex) {
			LOGGER.debug("Data not valid", ex);
			return new ResponseEntity<InlineResponse2001>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (AlreadyExistException ex) {
			LOGGER.debug("Transaction already exists", ex);
			return new ResponseEntity<InlineResponse2001>(HttpStatus.CONFLICT);
		} catch (Exception ex) {
			LOGGER.error("Unexpected API error", ex);
			return new ResponseEntity<InlineResponse2001>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<InlineResponse2002> transactionReferenceStatusGet(String reference, @Valid String channel) {
		LOGGER.debug("transactionReferenceStatusGet begins with reference " + reference + ", channel " + channel);
		
		try {
			
			Transaction transaction = transactionSrv.getTransaction(reference); 
			InlineResponse2002 response = new InlineResponse2002();
			response.setReference(transaction.getReference());
			response.setAmount(transaction.getAmount(channel));
			response.setFee(transaction.getFee(channel));
			response.setStatus(transaction.getStatus(channel));
			
			return new ResponseEntity<InlineResponse2002>(response, HttpStatus.OK);
		} catch (DataNotValidException ex) {
			LOGGER.debug("Data not valid", ex);
			return new ResponseEntity<InlineResponse2002>(HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (NotFoundException ex) {
			LOGGER.debug("Safebox not found", ex);
			return new ResponseEntity<InlineResponse2002>(HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("Unexpected API error", ex);
			return new ResponseEntity<InlineResponse2002>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
    
    private static List<InlineResponse200Transactions> transaction2InlineResponse(List<Transaction> transactionList) {
    	LOGGER.debug("transaction2InlineResponse begins with transactionList " + transactionList);
    	
    	List<InlineResponse200Transactions> result = new ArrayList<InlineResponse200Transactions>();
    	
    	transactionList.forEach((transaction) -> {
    		InlineResponse200Transactions inlineTrans = new InlineResponse200Transactions();
    		inlineTrans.setAccountIban(transaction.getAccountIban());
    		inlineTrans.setAmount(transaction.getAmount());
    		inlineTrans.setDate(transaction.getDate().toString());
    		inlineTrans.setDescription(transaction.getDescription());
    		inlineTrans.setFee(transaction.getFee());
    		inlineTrans.setReference(transaction.getReference());
    		
    		result.add(inlineTrans);
    	}); 
    	
    	return result;
    }

}
