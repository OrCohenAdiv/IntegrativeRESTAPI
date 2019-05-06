package smartspace.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.infra.UserService;
import smartspace.infra.UserServiceImpl;
import smartspace.layout.data.UserKeyBoundary;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties= {"spring.profiles.active=default"})
public class RESTUserIntegrationTests {
	private String baseUrl;
	private int port;
	private RestTemplate restTemplate;
	private EnhancedUserDao<String> userDao;
	private UserService userService;
	//TODO: create 2 users
	private UserEntity userAdminTest;
	private UserEntity userPlayerTest;
	
	@Autowired
	public void setElementDao(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}
	
	@Autowired
	public void setElementService(UserService userService) {
		this.userService = userService;
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.restTemplate = new RestTemplate();
	}
	
	@PostConstruct
	public void init() {
		this.baseUrl = "http://localhost:" + port + "/smartspace/admin/users/{adminSmartspace}/{adminEmail}";
		//TODO: create actual user and add it to db
		this.userAdminTest = new UserEntity("smartSpaceTest", "Or@Kooki", "kooki", "TheAvatar", UserRole.ADMIN, 100);
		this.userDao.create(userAdminTest);

		this.userPlayerTest = new UserEntity("smartSpaceTest", "Dani@Kooki", "kooki", "TheAvatar", UserRole.PLAYER, 100);
		this.userDao.create(userPlayerTest);
	}
	
	@After
	public void teardown() {
		this.userDao.deleteAll();
	}
	
		
	@Test
	public void testPostNewUser() throws Exception{
		// GIVEN the database has an admin user and non admin user
		
		// WHEN I POST new user
		UserBoundary newUser = new UserBoundary(userAdminTest);
		UserKeyBoundary ukb = new UserKeyBoundary();
		ukb.setEmail("Or@Test");
		ukb.setSmartspace("DaniTest.smartspace");
		newUser.setKey(ukb);
		newUser.setRole(UserRole.ADMIN);
		newUser.setAvatar("TheAvatar");
		newUser.setPoints(100);
		newUser.setUserName("TestTest");
		
		UserBoundary[] arr = {newUser};
		
		this.restTemplate
			.postForObject(
					this.baseUrl, 
					arr, 
					UserBoundary.class, 
					userAdminTest.getUserEmail(),userAdminTest.getUserSmartspace());//TODO: change to admin email and smartspace
		
		// THEN the database contains a single message
		assertThat(this.userDao
			.readAll())
			.hasSize(3);
	}
	
	@Test(expected=Exception.class)
	public void testPostNewUserWithBadCode() throws Exception{
		// GIVEN the database has an admin user and non admin user
		
		UserBoundary newUser = new UserBoundary(userPlayerTest);
		UserKeyBoundary ukb = new UserKeyBoundary();
		ukb.setEmail("Or@Test");
		ukb.setSmartspace("DaniTest.smartspace");
		newUser.setKey(ukb);
		newUser.setRole(UserRole.PLAYER);
		newUser.setAvatar("TheAvatar");
		newUser.setPoints(100);
		newUser.setUserName("TestTest");
		UserBoundary[] arr = {newUser};
		this.restTemplate
			.postForObject(
					this.baseUrl, 
					arr, 
					UserBoundary.class, 
					userPlayerTest.getUserEmail(),userPlayerTest.getUserSmartspace());//TODO: change to admin email and smartspace
	
		
		// THEN the test end with exception
		
	}

	

}
