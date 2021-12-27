package com.leovegas.wallet.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.leovegas.wallet.AbstractTest;
import com.leovegas.wallet.model.Account;
import com.leovegas.wallet.model.User;
import com.leovegas.wallet.utility.Constants;
import com.leovegas.wallet.vo.TransactionRequestVO;
import com.leovegas.wallet.vo.TransactionResponseVO;
import com.leovegas.wallet.vo.UserVO;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest extends AbstractTest{

	@LocalServerPort
	private Integer port;

	private String baseUrl = "http://localhost:";

	private static RestTemplate restTemplate = null;

	@BeforeAll
	void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		baseUrl = baseUrl+ randomServerPort;	 
	}


	@BeforeEach
	void setUp(){
		restTemplate = new RestTemplate();	

	}

	
	@Order(1)
	@Test
	public void registerUser() throws URISyntaxException {

		URI uri = new URI(baseUrl.concat("/wallet/user/registeruser/"));

		User user = buildUser("user_o1", "Adam", "Gilly", "testsssom@email.com",4000.0f);
		final  HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
		assertAll( () -> assertNotNull(result),
				() -> assertEquals(201 , result.getStatusCodeValue()));

	}

	@Order(2)
	@Test
	public void retrieveInsertedUser() throws Exception{

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/wallet/user/user_o1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content("")).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		UserVO userVO = super.mapFromJson(content, UserVO.class);
		assertAll( 
				() -> assertEquals(userVO.getUser().getAccount().getBalance(), 4000.0f),
				() -> assertEquals(200, mvcResult.getResponse().getStatus()));

	}

	@Order(3)
	@Test
	public void withdraw() throws URISyntaxException {

		
		URI uri = new URI(baseUrl.concat("/wallet/transactions/withdraw/"));

		final  HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<TransactionRequestVO> request = new HttpEntity<>(new TransactionRequestVO("user_o1",1000,4000), headers);

		ResponseEntity<TransactionResponseVO> result = restTemplate.exchange(uri, HttpMethod.PUT, request, TransactionResponseVO.class);
		assertAll( () -> assertEquals(201, result.getStatusCodeValue()),
				() -> assertEquals(result.getBody().getAccount().getBalance() , 0.0f));

	}


	@Order(5)
	@Test
	public void addMoney() throws URISyntaxException {

		URI uri = new URI(baseUrl.concat("/wallet/transactions/credit/"));

		final  HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<TransactionRequestVO> request = new HttpEntity<>(new TransactionRequestVO("user_o1",1001,3000), headers);


		ResponseEntity<TransactionResponseVO> result = restTemplate.exchange(uri, HttpMethod.PUT, request, TransactionResponseVO.class);

		assertAll( () -> assertEquals(201, result.getStatusCodeValue() ),
				() -> assertEquals(result.getBody().getAccount().getBalance() , 3000.0f));

	}

	@Order(5)
	@Test
	public void getUsers() throws URISyntaxException {

		URI uri = new URI(baseUrl.concat("/wallet/users"));

		final  HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);


		ResponseEntity<UserVO> result = restTemplate.getForEntity(uri, UserVO.class);

		assertAll(
				() -> assertTrue(result.getBody().getUserList().size() > 0),
				() -> assertEquals(200, result.getStatusCodeValue()),
				() -> assertTrue(result.getBody().getUserList().get(0).getAccount().getTransactions().size() ==2));


	}

	@Order(6)
	@Test
	public void checkWithdrawLogic() throws URISyntaxException {

		URI uri = new URI(baseUrl.concat("/wallet/transactions/withdraw/"));

		final  HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);



		final HttpEntity<TransactionRequestVO> request = new HttpEntity<>(new TransactionRequestVO("user_o1",1000,5000), headers);

		try {
			ResponseEntity<TransactionResponseVO> result = restTemplate.exchange(uri, HttpMethod.PUT, request, TransactionResponseVO.class);
			
			System.out.println(result.getBody());
			assertAll( () -> assertEquals(201, result.getStatusCodeValue()),
					() -> assertEquals(result.getBody().getAccount().getBalance() , 0.0f));
		} catch (Exception e) {
			assertTrue(e.getMessage().contains(Constants.FUNDS_NOT_ENOUGH));
		}


	}
	

	private User buildUser(final String userid, final String firstName, final String lastName, final String email,float balance) {
		Account acc = new Account();
		acc.setBalance(balance);
		User user = new User();
		user.setUserID(userid);
		user.setfName(firstName);
		user.setlName(lastName);
		user.setEmail(email);;
		user.setAccount(acc);
		return user;
	}






}
