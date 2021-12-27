package com.leovegas.wallet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebAppConfiguration
//@ContextConfiguration(classes = {MiscAppCfg.class})
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PaymentWalletApplication.class)
public abstract class AbstractTest {
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	protected MockMvc mockMvc;

	@LocalServerPort
	protected int randomServerPort;
	
	
    protected <T> T mapFromJson(String json, Class<T> clazz)
    	      throws JsonParseException, JsonMappingException, IOException {
    	      
    	      ObjectMapper objectMapper = new ObjectMapper();
    	      return objectMapper.readValue(json, clazz);
    	   }
	

}
