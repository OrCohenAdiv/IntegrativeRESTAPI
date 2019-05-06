package smartspace.layout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
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
		this.restTemplate
			.postForObject(
					this.baseUrl, 
					newUser, 
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
		this.restTemplate
			.postForObject(
					this.baseUrl, 
					newUser, 
					UserBoundary.class, 
					userPlayerTest.getUserEmail(),userPlayerTest.getUserSmartspace());//TODO: change to admin email and smartspace
	
		
		// THEN the test end with exception
		
	}

	
	@Test
	public void testGetAllUsersUsingPagination() throws Exception{
		// GIVEN the database contains 3 messages
		int size = 5;
		IntStream.range(1, size)
			.mapToObj(i->new UserEntity("smartSpaceTest2" + i, "Stam@Kooki", "kooki", "TheAvatar", UserRole.ADMIN, 100))
			.forEach(this.userDao::create);
		
		// WHEN I GET users of size 10 and page 0
		UserBoundary[] response = 
		this.restTemplate
			.getForObject(
					this.baseUrl + "?size={size}&page={page}" ,  
					UserBoundary[].class, userAdminTest.getUserEmail() , userAdminTest.getUserSmartspace(),
					10, 0);
		
		// THEN I receive 3 users
		assertThat(response)
			.hasSize(size);
	}
	/*
	@Test
	public void testGetAllMessagesUsingPaginationAndValidateContent() throws Exception{
		// GIVEN the database contains 3 messages
		int size = 3;
		java.util.List<UserBoundary> all = 
		IntStream.range(1, size + 1)
			.mapToObj(i->new UserEntity("smartSpaceTest2" + i, "Stam@Kooki", "kooki", "TheAvatar", UserRole.ADMIN, 100))
			.map(this.userDao::create)
			.map(UserBoundary::new)
			.collect(Collectors.toList());
		
		// WHEN I GET messages of size 10 and page 0
		UserBoundary[] response = 
		this.restTemplate
			.getForObject(
					this.baseUrl + "?size={size}&page={page}", 
					UserBoundary[].class, userAdminTest.getUserEmail() , userAdminTest.getUserSmartspace(),
					10, 0);
		
		// THEN I receive the exact 3 messages written to the databse
		assertThat(response)
			.usingElementComparatorOnFields("key")
			.containsExactlyElementsOf(all);
	}
	/*
	@Test
	public void testGetAllMessagesUsingPaginationAndValidateContentWithAllAttributeValidation() throws Exception{
		// GIVEN the database contains 4 messages
		int size = 4;
		Map<String, Object> details = new HashMap<>();
		details.put("y", 10.0);
		details.put("x", "10");
		int code = 2;
		
		java.util.List<MessageBoundary> all = 
		IntStream.range(1, size + 1)
			.mapToObj(i->new MessageEntity("demo" + i))
			.peek(msg->msg.setAuthor(new Name(msg.getName(), "Tester")))
			.peek(msg->msg.setSeverity((Math.random() > 0.5)?SeverityEnum.INFO:SeverityEnum.ERROR))
			.peek(msg->msg.setDetails(details))
			.map(msg->this.messageService.newMessage(msg, code))
			.map(MessageBoundary::new)
			.collect(Collectors.toList());
		
		
		// WHEN I GET messages of size 10 and page 0
		MessageBoundary[] response = 
		this.restTemplate
			.getForObject(
					this.baseUrl + "?size={size}&page={page}", 
					MessageBoundary[].class, 
					10, 0);
		
		// THEN I receive the exact messages written to the database
		assertThat(response)
			.usingElementComparatorOnFields("key", "details", "author", "severity", "timestamp")
			.containsExactlyElementsOf(all);
	}

	@Test
	public void testGetAllMessagesUsingPaginationOfSecondPage() throws Exception{
		// GIVEN then database contains 11 messages
		List<MessageEntity> all = 
		IntStream.range(0,11)
			.mapToObj(i->new MessageEntity("message" + i))
			.map(this.messageDao::create)
			.collect(Collectors.toList());
		
//		MessageBoundary last = new MessageBoundary( 
//		  all
//			.stream()
//			.sorted((e1,e2)->e2.getKey().compareTo(e1.getKey()))
//			.findFirst()
//			.orElseThrow(()->new RuntimeException("no messages after sorting")));
		
		MessageBoundary last =
			all
			.stream()
			.skip(10)
			.limit(1)
			.map(MessageBoundary::new)
			.findFirst()
			.orElseThrow(()->new RuntimeException("no messages after skipping"));
		
		// WHEN I GET messages of size 10 and page 1
		MessageBoundary[] result = this.restTemplate
			.getForObject(
					this.baseUrl + "?page={page}&size={size}", 
					MessageBoundary[].class, 
					1, 10);
		
		// THEN the result contains a single message (last message)
		assertThat(result)
			.usingElementComparator((b1,b2)->b1.toString().compareTo(b2.toString()))
			.containsExactly(last);
	}

	@Test
	public void testGetAllMessagesUsingPaginationOfSecondNonExistingPage() throws Exception{
		// GIVEN the database contains 10 messages
		IntStream
			.range(0, 10)
			.forEach(i->this.messageDao.create(new MessageEntity("message" + i)));
		
		// WHEN I GET messages of size 10 and page 1
		String[] result = 
		  this.restTemplate
			.getForObject(
					this.baseUrl + "?size={size}&page={pp}", 
					String[].class, 
					10, 1);
		
		// THEN the result is empty
		assertThat(result)
			.isEmpty();
		
	}
	
	@Test
	public void testUpdateMessage() throws Exception{
		// GIVEN the database contains a message
		MessageEntity message = new MessageEntity("test");
		message.setDetails(new HashMap<>());
		message = this.messageDao
			.create(message);
		
		// WHEN I update the message details
		Map<String, Object> newDetails = new HashMap<>();
		newDetails.put("x", 10.0);
		newDetails.put("y", "new details");
		newDetails.put("expired", true);
		
		MessageBoundary boundry = new MessageBoundary();
		boundry.setDetails(newDetails);
		this.restTemplate
			.put(this.baseUrl + "/{key}", 
					boundry, 
					message.getKey());
		
		// THEN the database contains updated details
		assertThat(this.messageDao.readById(message.getKey()))
			.isNotNull()
			.isPresent()
			.get()
			.extracting("key", "details")
			.containsExactly(message.getKey(), newDetails);
		
	}
	
	@Test
	public void testDeleteByKey() throws Exception{
		// GIVEN the database contains a single message
		Long key = 
		this.messageDao
			.create(new MessageEntity("test"))
			.getKey();
		
		// WHEN I delete using the message key
		this.restTemplate
			.delete(this.baseUrl + "/{key}", key);
		
		// THEN the database is empty
		assertThat(this.messageDao
				.readAll())
			.isEmpty();
	}
	
	@Test
	public void testDeleteByKeyWhileDatabseIsNotEmptyAtTheEnd() throws Exception{
		// GIVEN the database contains 101 messages
		List<MessageEntity> all101Messages = 
		IntStream.range(1, 102)
			.mapToObj(i->new MessageEntity("message #" + i))
			.map(this.messageDao::create)
			.collect(Collectors.toList());
		
		MessageEntity third = all101Messages.get(2);
		
		Long thridKey = 
				third
				.getKey();
		
		// WHEN I delete the 3rd message using the message key
		this.restTemplate
			.delete(this.baseUrl + "/{key}", thridKey);
		
		// THEN the database contains 100 messages 
		// AND the database does not contain the deleted message
		assertThat(this.messageDao
				.readAll())
			.hasSize(100)
			.usingElementComparatorOnFields("key")
			.doesNotContain(third);
	}
	
}
*/
}
