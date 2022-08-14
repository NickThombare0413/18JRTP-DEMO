package in.ashokit.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.binding.User;
import in.ashokit.constants.AppConstants;
import in.ashokit.rest.RegistrationRestController;
import in.ashokit.service.RegistrationService;

@WebMvcTest(value = RegistrationRestController.class)
public class RegistrationRestControllerTest {

	@MockBean
	private RegistrationService registrationService;

	@Autowired
	private MockMvc mockMVC;

	@Test
	public void checkEmailTest1() throws Exception {
		when(registrationService.uniqueEmail("nickthombare@gmail.com")).thenReturn(true);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/emailcheck/nickthombare@gmail.com");
		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String string = response.getContentAsString();
		System.out.println(string);
		assertEquals(AppConstants.UNIQUE, string);

	}
	@Test
	public void checkEmailTest2() throws Exception {
		when(registrationService.uniqueEmail("abc@gmail.com")).thenReturn(false);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/emailcheck/abc@gmail.com");
		MvcResult result = mockMVC.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String string = response.getContentAsString();
		System.out.println(string);
		assertEquals(AppConstants.DUPLICATE, string);

	}
	     @Test
	   public void getCountriesTest() throws Exception
	   {
		    Map<Integer, String> map=new HashMap<Integer, String>();
		    map.put(1, "india");
		    map.put(2, "US");
		    map.put(3, "UK");
		    
		    
		  when(registrationService.getCountries()).thenReturn(map);
		  
		    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/countries");
			MvcResult result = mockMVC.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			int status = response.getStatus();
			assertEquals(200, status);

		   
	   }
	     @Test
		   public void getStatesTest() throws Exception
		   {
			    Map<Integer, String> map=new HashMap<Integer, String>();
			    map.put(1, "Maharashtra");
			    map.put(2, "AP");
			    map.put(3, "Telengana");
			    
			    
			  when(registrationService.getStates(1)).thenReturn(map);
			  
			    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/states/1");
				MvcResult result = mockMVC.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				int status = response.getStatus();
				assertEquals(200, status);

			   
		   }
	     
	     @Test
		   public void getCitiesTest() throws Exception
		   {
			    Map<Integer, String> map=new HashMap<Integer, String>();
			    map.put(1, "Chandrapur");
			    map.put(2, "Nagpur");
			    map.put(3, "Pune");
			    
			    
			  when(registrationService.getCities(1)).thenReturn(map);
			  
			    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cities/1");
				MvcResult result = mockMVC.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				int status = response.getStatus();
				assertEquals(200, status);

			   
		   }
	     
	     @Test
	     public void saveUserTest1() throws Exception
	     {
	    	    User user=new User();
	    	    user.setUserFname("niketan");
	    	    user.setUserEmail("nickthombare@gmail.com");
	    	    user.setUserPhno(888862L);
	    	    
	    	    when(registrationService.registerUser(user)).thenReturn(true);
	    	    
	    	    ObjectMapper mapper=new ObjectMapper();
	    	    String userJson = mapper.writeValueAsString(user);
	    	    
		         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveuser")
				.contentType("application/json").content(userJson);
    
				MvcResult result = mockMVC.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				String status = response.getContentAsString();
				assertEquals(AppConstants.SUCCESS, status);
	     }
	     
	     
	     @Test
	     public void saveUserTest2() throws Exception
	     {
	    	    User user=new User();
	    	    user.setUserFname("niketan");
	    	    user.setUserEmail("nickthombare@gmail.com");
	    	    user.setUserPhno(888862L);
	    	    
	    	    when(registrationService.registerUser(user)).thenReturn(false);
	    	    
	    	    ObjectMapper mapper=new ObjectMapper();
	    	    String jsonData = mapper.writeValueAsString(user);
	    	    
		         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveuser")
				.contentType("application/json").content(jsonData);
    
				MvcResult result = mockMVC.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				String status = response.getContentAsString();
				assertEquals(AppConstants.FAIL, status);
	     }
	
}
