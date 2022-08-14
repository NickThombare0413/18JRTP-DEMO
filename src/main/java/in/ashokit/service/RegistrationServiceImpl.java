package in.ashokit.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.User;
import in.ashokit.constants.AppConstants;
import in.ashokit.entities.CityEntity;
import in.ashokit.entities.CountryEntity;
import in.ashokit.entities.StateEntity;
import in.ashokit.entities.UserEntity;
import in.ashokit.exception.RegAppException;
import in.ashokit.props.AppProperties;
import in.ashokit.repositories.CityRepository;
import in.ashokit.repositories.CountryRepository;
import in.ashokit.repositories.StateRepository;
import in.ashokit.repositories.UserRepository;
import in.ashokit.util.EmailUtils;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private AppProperties appProps;

	@Override
	public Boolean uniqueEmail(String email) {
		UserEntity userEntity = userRepo.findByUserEmail(email);

		if (userEntity != null) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public Map<Integer, String> getCountries() {

		List<CountryEntity> countryList = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		for (CountryEntity countryEntity : countryList) {
			countryMap.put(countryEntity.getCountryId(), countryEntity.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateEntity> stateList = stateRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();
		for (StateEntity stateEntity : stateList) {
			stateMap.put(stateEntity.getStateId(), stateEntity.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityEntity> cityList = cityRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();
		for (CityEntity cityEntity : cityList) {
			cityMap.put(cityEntity.getCityId(), cityEntity.getCityName());
		}
		return cityMap;
	}

	@Override
	public boolean registerUser(User user) {
		user.setUserPwd(generateTempPwd());
		user.setUserAccStatus(AppConstants.LOCKED);

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		UserEntity entity = userRepo.save(userEntity);

		if (null != entity.getUserId()) {
			return sendRegEmail(user);
		}

		return false;
	}

	private String generateTempPwd() {
		String tempPwd = null;

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		tempPwd = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return tempPwd;

	}

	public boolean sendRegEmail(User user) {
		boolean emailSent = false;
		try {
			Map<String, String> messages = appProps.getMessages();
			String subject = messages.get(AppConstants.REG_EMAIL_SUBJECT);
			String bodyFilename = messages.get(AppConstants.REG_EMAIL_TEMPLTE_FILENAME);
			// String subject = "User Registration Success";
			String body = readMailBody(bodyFilename, user);
			emailUtils.sendEmail(subject, body, user.getUserEmail());
			emailSent = true;

		} catch (Exception e) {
			throw new RegAppException(e.getMessage());
		}

		return emailSent;
	}

	public String readMailBody(String filename, User user) {
		String mailBody = null;
		StringBuffer buffer = new StringBuffer();
		Path path = Paths.get(filename);
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> {
				buffer.append(line);
			});

			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.FNAME, user.getUserFname());
			mailBody = mailBody.replace(AppConstants.EMAIL, user.getUserEmail());
			mailBody = mailBody.replace(AppConstants.TEMP_PWD, user.getUserPwd());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mailBody;
	}

}
