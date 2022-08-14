package in.ashokit.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import in.ashokit.binding.User;
import in.ashokit.entities.CityEntity;
import in.ashokit.entities.CountryEntity;
import in.ashokit.entities.StateEntity;
import in.ashokit.entities.UserEntity;
import in.ashokit.props.AppProperties;
import in.ashokit.repositories.CityRepository;
import in.ashokit.repositories.CountryRepository;
import in.ashokit.repositories.StateRepository;
import in.ashokit.repositories.UserRepository;
import in.ashokit.service.RegistrationServiceImpl;
import in.ashokit.util.EmailUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

	@MockBean
	private UserRepository userRepo;

	@MockBean
	private CountryRepository countryRepo;

	@MockBean
	private StateRepository stateRepo;

	@MockBean
	private CityRepository cityRepo;

	@MockBean
	private EmailUtils emailUtils;

	@MockBean
	private AppProperties appProps;

	@InjectMocks
	private RegistrationServiceImpl service;

	@Test
	public void uniqueEmailTest1() {

		when(userRepo.findByUserEmail("nickthombare@gmail.com")).thenReturn(new UserEntity());
		Boolean uniqueEmail = service.uniqueEmail("nickthombare@gmail.com");
		assertFalse(uniqueEmail);

	}

	@Test
	public void uniqueEmailTest2() {

		when(userRepo.findByUserEmail("abc@gmail.com")).thenReturn(null);
		Boolean uniqueEmail = service.uniqueEmail("abc@gmail.com");
		assertTrue(uniqueEmail);

	}

	@Test
	public void getCountriesTest() {

		CountryEntity country = new CountryEntity();
		country.setCountryId(100);
		country.setCountryName("India");

		List<CountryEntity> countryList = new ArrayList<CountryEntity>();
		countryList.add(country);

		when(countryRepo.findAll()).thenReturn(countryList);
		Map<Integer, String> countries = service.getCountries();
		assertThat(countries);

	}
	@Test
	public void getStateTest() {

		StateEntity state = new StateEntity();
		state.setStateId(1);
		state.setStateName("maharashtra");

		List<StateEntity> stateList = new ArrayList<StateEntity>();
		stateList.add(state);

		when(stateRepo.findAll()).thenReturn(stateList);
		Map<Integer, String> states = service.getStates(1);
		assertThat(states);

	}
	
	@Test
	public void getcitiesTest() {

		CityEntity city = new CityEntity();
		city.setCityId(1);
		city.setCityName("Chandrapur");

		List<CityEntity> cityList = new ArrayList<CityEntity>();
		cityList.add(city);

		when(cityRepo.findAll()).thenReturn(cityList);
		Map<Integer, String> cities = service.getCities(1);
		assertTrue(cities.containsKey(1));

	}
	@Test
	public void registerUserTest() {
		UserEntity user=new UserEntity();
		user.setUserId(1);
		user.setUserFname("niketan");
		
		User user1=new User();
		user1.setUserId(2);
		user1.setUserFname("SHubham");
		
		doNothing().when(service).sendRegEmail(user1);
		when(userRepo.save(user)).thenReturn(user);
		
		doNothing().when(service).sendRegEmail(user1);
		boolean registerUser = service.registerUser(user1);
		
		assertTrue(registerUser);
		
	}
	
}
