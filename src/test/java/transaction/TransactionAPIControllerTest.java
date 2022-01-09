package transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dlmartin.transaction.controllers.TransactionApiController;
import dlmartin.transaction.model.entities.Transaction;
import dlmartin.transaction.model.exceptions.AlreadyExistException;
import dlmartin.transaction.model.exceptions.DataNotValidException;
import dlmartin.transaction.model.exceptions.NotFoundException;
import dlmartin.transaction.services.TransactionSrv;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TransactionApiController.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public class TransactionAPIControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionSrv transactionSrv;
	
	private static final MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
	
	private static final String VALID_TRANSACTION_REFERENCE = "12345A";
	private static final String TRANSACTION_REFERENCE = "12345A";
	private static final String TRANSACTION_IBAN = "ES9820385778983000760236";
	private static final String TRANSACTION_DATE = "2019-07-16T16:55:42.000Z";
	private static final LocalDateTime TRANSACTION_LOCAL_DATE = LocalDateTime.parse(TRANSACTION_DATE, DateTimeFormatter.ISO_DATE_TIME);
	private static final Float TRANSACTION_AMOUNT = 193.38f;
	private static final Float TRANSACTION_FEE = 3.18f;
	private static final String TRANSACTION_DESCRIPTION = "Restaurant payment"; 
	
	private static final String IBAN_PARAM_NAME = "account_iban";
	private static final String ORDER_PARAM_NAME = "sort";
	private static final String CREATE_TRANSACTION_PAYLOAD = "{\"reference\":\"12345A\", \"account_iban\":\"ES9820385778983000760236\", \"date\":\"2019-07-16T16:55:42.000Z\", \"amount\":193.38, \"fee\":3.18, \"description\":\"Restaurant payment\"}";
	
	private static Transaction transaction;
	private static List<Transaction> transactionList;
	
	@BeforeEach
	public void init() {
		transaction = new Transaction();
		transaction.setReference(TRANSACTION_REFERENCE);
		transaction.setAccountIban(TRANSACTION_IBAN);
		transaction.setAmount(TRANSACTION_AMOUNT);
		transaction.setDate(TRANSACTION_LOCAL_DATE);
		transaction.setDescription(TRANSACTION_DESCRIPTION);
		transaction.setFee(TRANSACTION_FEE);
		
		transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction);
	}
	
	@Test
	public void transactionGet_OK() throws Exception {
		
		Mockito.when(transactionSrv.findTransactions(Mockito.any(), Mockito.any())).thenReturn(transactionList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionGet_UNPROCESSABLE_ENTITY() throws Exception{
		
		Mockito.when(transactionSrv.findTransactions(Mockito.any(), Mockito.any())).thenThrow(new DataNotValidException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
		
	}
	
	@Test
	public void transactionGet_INTERNAL_SERVER_ERROR() throws Exception {
		
		Mockito.when(transactionSrv.findTransactions(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionReferenceStatusGet_OK() throws Exception {
		
		Mockito.when(transactionSrv.getTransaction(Mockito.any())).thenReturn(transaction);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/" + TRANSACTION_REFERENCE + "/status/")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionReferenceStatusGet_UNPROCESSABLE_ENTITY() throws Exception{
		
		Mockito.when(transactionSrv.getTransaction(Mockito.any())).thenThrow(new DataNotValidException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/" + TRANSACTION_REFERENCE + "/status/")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
		
	}
	
	@Test
	public void transactionReferenceStatusGet_NOT_FOUND() throws Exception{
		
		Mockito.when(transactionSrv.getTransaction(Mockito.any())).thenThrow(new NotFoundException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/" + TRANSACTION_REFERENCE + "/status/")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
		
	}
	
	@Test
	public void transactionReferenceStatusGet_INTERNAL_SERVER_ERROR() throws Exception{
		
		Mockito.when(transactionSrv.getTransaction(Mockito.any())).thenThrow(new RuntimeException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transaction/" + TRANSACTION_REFERENCE + "/status/")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
		
	}
	
	@Test
	public void transactionPost_OK() throws Exception{
		
		Mockito.when(transactionSrv.createTransaction(TRANSACTION_REFERENCE, TRANSACTION_IBAN, TRANSACTION_DATE, TRANSACTION_AMOUNT, TRANSACTION_FEE, TRANSACTION_DESCRIPTION)).thenReturn(TRANSACTION_REFERENCE);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transaction/")
				.content(CREATE_TRANSACTION_PAYLOAD)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionPost_UNPROCESSABLE_ENTITY() throws Exception {
		
		Mockito.when(transactionSrv.createTransaction(Mockito.any(), Mockito.any(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new DataNotValidException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transaction/")
				.content(CREATE_TRANSACTION_PAYLOAD)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionPost_CONFLICT()  throws Exception  {
		
		Mockito.when(transactionSrv.createTransaction(Mockito.any(), Mockito.any(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new AlreadyExistException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transaction/")
				.content(CREATE_TRANSACTION_PAYLOAD)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void transactionPost_INTERNAL_SERVER_ERROR() throws Exception {
		
		Mockito.when(transactionSrv.createTransaction(Mockito.any(), Mockito.any(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transaction/")
				.content(CREATE_TRANSACTION_PAYLOAD)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
		
	}
}
