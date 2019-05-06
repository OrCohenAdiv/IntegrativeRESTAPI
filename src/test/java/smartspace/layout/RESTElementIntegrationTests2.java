package smartspace.layout;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ElementEntity;
import smartspace.data.Location;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.infra.ElementService;
import smartspace.layout.data.ElementCreatorBoundary;
import smartspace.layout.data.ElementKeyBoundary;
import smartspace.layout.data.ElementLocationBoundary;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.acrive=default"})
public class RESTElementIntegrationTests2 {
	
	@Value("${smartspace.name}")
	private String smartspaceName; 
	
	private EnhancedElementDao<String> elementDao;
	private EnhancedUserDao<String> userDao;

	//private ElementService elementService;
	private String baseUrl;
	private int port;
	private RestTemplate restTemplate;
	
	private UserEntity userEntityAdmin;
	private UserEntity userEntityManager;

	@Autowired
	public void setElementDao(EnhancedElementDao<String> elementDao) {
		this.elementDao = elementDao;
	}
	
	@Autowired
	public void setUserDao(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}
	
//	@Autowired
//	public void setElementService(ElementService elementService) {
//		this.elementService = elementService;
//	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.restTemplate = new RestTemplate();
	}
		
	@PostConstruct
	public void init(){
		this.userEntityAdmin = new UserEntity("mysmartspace", "admin.creating.element@de.mo",
				"myAdminName", "myAvatar", UserRole.ADMIN, 1332);
		this.userEntityAdmin = this.userDao.create(userEntityAdmin);
		
		this.userEntityManager = new UserEntity("mysmartspace", "manager.creating.element@de.mo",
				"myManagerName", "myAvatar", UserRole.MANAGER, 13);
		this.userEntityManager = this.userDao.create(userEntityManager);
		
		this.baseUrl = "http://localhost:" + port + "/smartspace/admin/elements/{adminSmartspace}/{adminEmail}";
	}
	
	@After
	public void teardown() {
		this.elementDao.deleteAll();
		this.userDao.deleteAll();
	}
	
////////////////// POST ////////////////////////
	
	@Test 
	public void testCreateAndPostByAdminUserRole() throws Exception {
		// GIVEN the database is empty 
		
		//WHEN the admin user create and POST a new element 
		
		Map<String, Object> details = new HashMap<>();
		details.put("x", "10");
		details.put("y", 10.0);

		ElementBoundary newElemenetBoundary = new ElementBoundary();
		
		ElementLocationBoundary latlng = new ElementLocationBoundary();
		latlng.setLat(32.115);
		latlng.setLng(84.817);
		
		ElementCreatorBoundary newElementCreator = new ElementCreatorBoundary();
		newElementCreator.setEmail(this.userEntityAdmin.getUserEmail());
		newElementCreator.setSmartspace(this.userEntityAdmin.getUserSmartspace());
		
		Map<String, Object> elementProperties = new HashMap<>();
		elementProperties.put("key1", 1);
		elementProperties.put("key2", "2");
		elementProperties.put("key3", "it can be anything");
		
		ElementKeyBoundary elementKey = new ElementKeyBoundary();
		elementKey.setId("50");
		elementKey.setSmartspace(this.userEntityAdmin.getUserSmartspace());
		
		newElemenetBoundary.setKey(elementKey);
		newElemenetBoundary.setName("demoElement");
		newElemenetBoundary.setLatlng(latlng);
		newElemenetBoundary.setExpired(false);
		newElemenetBoundary.setElementType("myType");
		newElemenetBoundary.setElementProperties(elementProperties);
		newElemenetBoundary.setCreator(newElementCreator);
		newElemenetBoundary.setCreationTimestamp(new Date());
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				newElemenetBoundary, 
				ElementBoundary.class, 
				this.userEntityAdmin.getUserSmartspace(),
				this.userEntityAdmin.getUserEmail());
		
		//THEN the database contains a single element
		assertThat(this.elementDao.readAll()).hasSize(1);
	}
	
	
	@Test(expected=Exception.class)
	public void testCreateAndPostByManagerUserRole() throws Exception {
		// GIVEN the database is empty 

		// WHEN the manager create and try to POST a new element
		
		Map<String, Object> details = new HashMap<>();
		details.put("x", "100");
		details.put("y", 100.15);

		ElementBoundary newElemenetBoundary = new ElementBoundary();
		
		ElementLocationBoundary latlng = new ElementLocationBoundary();
		latlng.setLat(32.115);
		latlng.setLng(84.817);
		
		ElementCreatorBoundary newElementCreator = new ElementCreatorBoundary();
		newElementCreator.setEmail(this.userEntityManager.getUserEmail());
		newElementCreator.setSmartspace(this.userEntityManager.getUserSmartspace());
		
		Map<String, Object> elementProperties = new HashMap<>();
		elementProperties.put("key1", 1);
		elementProperties.put("key2", "2");
		elementProperties.put("key3", "it can be anything");
		
		ElementKeyBoundary elementKey = new ElementKeyBoundary();
		elementKey.setId("50");
		elementKey.setSmartspace(this.userEntityManager.getUserSmartspace());
		
		newElemenetBoundary.setKey(elementKey);
		newElemenetBoundary.setName("demoElement");
		newElemenetBoundary.setLatlng(latlng);
		newElemenetBoundary.setExpired(false);
		newElemenetBoundary.setElementType("myType");
		newElemenetBoundary.setElementProperties(elementProperties);
		newElemenetBoundary.setCreator(newElementCreator);
		newElemenetBoundary.setCreationTimestamp(new Date());
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				newElemenetBoundary, 
				ElementBoundary.class, 
				this.userEntityManager.getUserSmartspace(),
				this.userEntityManager.getUserEmail());
		
		// THEN the test end with exception
		
	}
	
	
	@Test
	public void testCreateByManagerAndPostByAdminUserRole() throws Exception {
		// GIVEN the database is empty 
		
		// WHEN the manager create the element and the admin POST the element
		
		Map<String, Object> details = new HashMap<>();
		details.put("x", "100");
		details.put("y", 100.15);

		ElementBoundary newElemenetBoundary = new ElementBoundary();
		
		ElementLocationBoundary latlng = new ElementLocationBoundary();
		latlng.setLat(32.115);
		latlng.setLng(84.817);
		
		ElementCreatorBoundary newElementCreator = new ElementCreatorBoundary();
		newElementCreator.setEmail(this.userEntityManager.getUserEmail());
		newElementCreator.setSmartspace(this.userEntityManager.getUserSmartspace());
		
		Map<String, Object> elementProperties = new HashMap<>();
		elementProperties.put("key1", 1);
		elementProperties.put("key2", "2");
		elementProperties.put("key3", "it can be anything");
		
		ElementKeyBoundary elementKey = new ElementKeyBoundary();
		elementKey.setId("50");
		elementKey.setSmartspace(this.userEntityManager.getUserSmartspace());
		
		newElemenetBoundary.setKey(elementKey);
		newElemenetBoundary.setName("demoElement");
		newElemenetBoundary.setLatlng(latlng);
		newElemenetBoundary.setExpired(false);
		newElemenetBoundary.setElementType("myType");
		newElemenetBoundary.setElementProperties(elementProperties);
		newElemenetBoundary.setCreator(newElementCreator);
		newElemenetBoundary.setCreationTimestamp(new Date());
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				newElemenetBoundary, 
				ElementBoundary.class, 
				this.userEntityAdmin.getUserSmartspace(),
				this.userEntityAdmin.getUserEmail());
		
		//THEN the database contains a single element
		assertThat(this.elementDao.readAll()).hasSize(1);
	}
	
	
	@Test(expected=Exception.class)
	public void testCreateByAdminAndPostByManagerUserRole() throws Exception {
		// GIVEN the database is empty 
		
		// WHEN the admin create the element and the manager try to POST the element
		
		Map<String, Object> details = new HashMap<>();
		details.put("x", "100");
		details.put("y", 100.15);

		ElementBoundary newElemenetBoundary = new ElementBoundary();
		
		ElementLocationBoundary latlng = new ElementLocationBoundary();
		latlng.setLat(32.115);
		latlng.setLng(84.817);
		
		ElementCreatorBoundary newElementCreator = new ElementCreatorBoundary();
		newElementCreator.setEmail(this.userEntityAdmin.getUserEmail());
		newElementCreator.setSmartspace(this.userEntityAdmin.getUserSmartspace());
		
		Map<String, Object> elementProperties = new HashMap<>();
		elementProperties.put("key1", 1);
		elementProperties.put("key2", "2");
		elementProperties.put("key3", "it can be anything");
		
		ElementKeyBoundary elementKey = new ElementKeyBoundary();
		elementKey.setId("50");
		elementKey.setSmartspace(this.userEntityAdmin.getUserSmartspace());
		
		newElemenetBoundary.setKey(elementKey);
		newElemenetBoundary.setName("demoElement");
		newElemenetBoundary.setLatlng(latlng);
		newElemenetBoundary.setExpired(false);
		newElemenetBoundary.setElementType("myType");
		newElemenetBoundary.setElementProperties(elementProperties);
		newElemenetBoundary.setCreator(newElementCreator);
		newElemenetBoundary.setCreationTimestamp(new Date());
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				newElemenetBoundary, 
				ElementBoundary.class, 
				this.userEntityManager.getUserSmartspace(),
				this.userEntityManager.getUserEmail());
		
		// THEN the test end with exception

	}
	
	
	@Test(expected=Exception.class)
	public void testCreateAndPostByAdminWithMySmartspace() throws Exception {
		// GIVEN the database is empty
		
		// WHEN the admin create the element and the manager try to POST the element
		
		Map<String, Object> details = new HashMap<>();
		details.put("x", "100");
		details.put("y", 100.15);

		ElementBoundary newElemenetBoundary = new ElementBoundary();
		
		ElementLocationBoundary latlng = new ElementLocationBoundary();
		latlng.setLat(32.115);
		latlng.setLng(84.817);
		
		ElementCreatorBoundary newElementCreator = new ElementCreatorBoundary();
		newElementCreator.setEmail(this.userEntityAdmin.getUserEmail());
		newElementCreator.setSmartspace(this.userEntityAdmin.getUserSmartspace());
		
		Map<String, Object> elementProperties = new HashMap<>();
		elementProperties.put("key1", 1);
		elementProperties.put("key2", "2");
		elementProperties.put("key3", "it can be anything");
		
		ElementKeyBoundary elementKey = new ElementKeyBoundary();
		elementKey.setId("50");
		elementKey.setSmartspace(this.smartspaceName);
		
		newElemenetBoundary.setKey(elementKey);
		newElemenetBoundary.setName("demoElement");
		newElemenetBoundary.setLatlng(latlng);
		newElemenetBoundary.setExpired(false);
		newElemenetBoundary.setElementType("myType");
		newElemenetBoundary.setElementProperties(elementProperties);
		newElemenetBoundary.setCreator(newElementCreator);
		newElemenetBoundary.setCreationTimestamp(new Date());
		
		this.restTemplate.postForObject(
				this.baseUrl, 
				newElemenetBoundary, 
				ElementBoundary.class, 
				this.userEntityManager.getUserSmartspace(),
				this.userEntityManager.getUserEmail());
		
		// THEN the test end with exception

	}

	// do array of users 
	
	
//	@Test
//	public void testGetAllElementsUsingPagination() throws Exception{
//		// GIVEN the database contains 3 messages
//		int size = 3;
//		Location location = new Location();
//		location.setX(15.15);
//		location.setY(52.25); 
//		
//		Map<String, Object> elementProperties = new HashMap<>();
//		elementProperties.put("key1", 1);
//		elementProperties.put("key2", "2");
//		elementProperties.put("key3", "it can be anything");
//		
//		IntStream.range(1, size + 1)
//			.mapToObj(i -> new ElementEntity("demo" + i, "MyType", location, 
//					new Date(), this.userEntityAdmin.getUserEmail(),
//					this.userEntityAdmin.getUserSmartspace(), false, elementProperties))
//			.forEach(this.elementDao::create);
//		
//		// WHEN I GET messages of size 10 and page 0
//		ElementBoundary[] response = this.restTemplate.getForObject(
//				this.baseUrl + "?size={size}&page={page}", ElementBoundary[].class, 10, 0);
//		
//		// THEN I receive 3 messages
//		assertThat(response).hasSize(size);
//	}
	
}

