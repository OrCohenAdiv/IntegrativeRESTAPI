package smartspace.layout;

import javax.annotation.PostConstruct;

import org.junit.After;
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
import smartspace.infra.ActionService;
import smartspace.infra.ElementService;
import smartspace.infra.UserService;

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
	private ActionService actionService;
	private UserService userService;
	private ElementService elementService;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.restTemplate = new RestTemplate();
	}

	@PostConstruct
	public void init() {
		this.baseUrl = "http://localhost:" + port + "/smartspace/admin/users/{adminSmartspace}/{adminEmail}";
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

	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}

}
