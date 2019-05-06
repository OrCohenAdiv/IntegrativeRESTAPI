package smartspace.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

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

import smartspace.dao.EnhancedActionDao;
import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.dao.UserDao;
import smartspace.data.ElementEntity;
import smartspace.data.Location;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.infra.ActionService;
import smartspace.infra.ElementService;
import smartspace.infra.UserService;
import smartspace.layout.data.ActionKeyBoundary;
import smartspace.layout.data.ElementCreatorBoundary;
import smartspace.layout.data.ElementKeyBoundary;
import smartspace.layout.data.ElementLocationBoundary;
import smartspace.layout.data.UserKeyBoundary;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.profiles.active=default" })
public class RESTActionIntegrationTests {
	private String baseUrl;
	private int port;
	private RestTemplate restTemplate;
	private EnhancedActionDao enhancedActionDao;
	private EnhancedUserDao<String> enhancedUserDao;
	private EnhancedElementDao<String> enhancedElementDao;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.restTemplate = new RestTemplate();
	}

	@PostConstruct
	public void init() {
		this.baseUrl = "http://localhost:" + port + "/smartspace/admin/actions/{adminSmartspace}/{adminEmail}";
	}

	@After
	public void teardown() {
		this.enhancedActionDao.deleteAll();
		this.enhancedUserDao.deleteAll();
		this.enhancedElementDao.deleteAll();

	}

	@Autowired
	public void setEnhancedActionDao(EnhancedActionDao enhancedActionDao) {
		this.enhancedActionDao = enhancedActionDao;
	}

	@Autowired
	public void setEnhancedUserDao(EnhancedUserDao<String> enhancedUserDao) {
		this.enhancedUserDao = enhancedUserDao;
	}

	@Autowired
	public void setEnhancedElementDao(EnhancedElementDao<String> enhancedElementDao) {
		this.enhancedElementDao = enhancedElementDao;
	}

	@Test
	public void testImportActionWithExistingElements() throws Exception {
		UserEntity user = new UserEntity("2019b.tomc", "admin@admin.com", "Admin", ":-D", UserRole.ADMIN, 6);
		enhancedUserDao.create(user);
		ElementEntity elementEntity = new ElementEntity("2019b.tomc", null, new Location(5, 5), "Moshe", "Hotel", null,
				false, "creatorSmartSpace", "admin@admin.com", null);
		enhancedElementDao.create(elementEntity);
		
		ActionKeyBoundary actionKey = new ActionKeyBoundary();
		actionKey.setId("50");
		actionKey.setSmartspace("Moshe");
		ElementKeyBoundary element = new ElementKeyBoundary();
		element.setId("2");
		element.setSmartspace("2019b.tomc");
		UserKeyBoundary player = new UserKeyBoundary();
		player.setEmail("admin@smfng.com");
		player.setSmartspace("Moshe");
		
		
		ActionBoundary actionBoundary = new ActionBoundary();
		actionBoundary.setActionKey(actionKey);
		actionBoundary.setType("momo");
		actionBoundary.setElement(element);
		actionBoundary.setPlayer(player);
		
		ActionBoundary[] arr = {actionBoundary};
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				arr, 
				ElementBoundary[].class, 
				user.getUserSmartspace(),
				user.getUserEmail());
		
		//THEN the database contains a single element
		assertThat(this.enhancedActionDao.readAll()).hasSize(1);
	}
	
	@Test(expected=Exception.class)
	public void testImportActionWithExistingElementsAndUserNotAdmin() throws Exception {
		UserEntity user = new UserEntity("2019b.tomc", "admin2@admin.com", "Admin", ":-D", UserRole.MANAGER, 6);
		enhancedUserDao.create(user);
		ElementEntity elementEntity = new ElementEntity("2019b.tomc", null, new Location(5, 5), "Moshe", "Hotel", null,
				false, "creatorSmartSpace", "admin2@admin.com", null);
		enhancedElementDao.create(elementEntity);
		
		ActionKeyBoundary actionKey = new ActionKeyBoundary();
		actionKey.setId("50");
		actionKey.setSmartspace("Moshe");
		ElementKeyBoundary element = new ElementKeyBoundary();
		element.setId("2");
		element.setSmartspace("2019b.tomc");
		UserKeyBoundary player = new UserKeyBoundary();
		player.setEmail("admin@smfng.com");
		player.setSmartspace("Moshe");
		
		
		ActionBoundary actionBoundary = new ActionBoundary();
		actionBoundary.setActionKey(actionKey);
		actionBoundary.setType("momo");
		actionBoundary.setElement(element);
		actionBoundary.setPlayer(player);
		
		ActionBoundary[] arr = {actionBoundary};
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				arr, 
				ElementBoundary[].class, 
				user.getUserSmartspace(),
				user.getUserEmail());
		
		//THEN throws execption
	}

}
