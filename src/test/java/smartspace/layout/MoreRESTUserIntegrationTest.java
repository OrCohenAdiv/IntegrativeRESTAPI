package smartspace.layout;

import static org.assertj.core.api.Assertions.assertThat;


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
import smartspace.layout.data.NewUserForm;
import smartspace.layout.data.UserKeyBoundary;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.acrive=default"})
public class MoreRESTUserIntegrationTest {

		private String baseUrl;
		private int port;
		private RestTemplate restTemplate;
		private EnhancedUserDao<String> userDao;

		
		@Autowired
		public void setUserDao(EnhancedUserDao<String> userDao) {
			this.userDao = userDao;
		}
		
		@LocalServerPort
		public void setPort(int port) {
			this.port = port;
			this.restTemplate = new RestTemplate();
		}
		
		@PostConstruct
		public void init() {
			this.baseUrl = "http://localhost:" + port;
		}
		
		@After
		public void tearDown() {
			this.userDao
				.deleteAll();
		}
		
		@Test
		public void testPostCreateNewUserWithNewUserForm() throws Exception{
			// GIVEN the database is empty
			
			// WHEN I POST new user
			NewUserForm newUserForm = new NewUserForm();
			newUserForm.setAvatar("newAvatar");
			newUserForm.setEmail("test@test.com");
			newUserForm.setRole(UserRole.PLAYER);
			newUserForm.setUsername("TEST");
			this.restTemplate
				.postForObject(
						this.baseUrl + "/smartspace/users", 
						newUserForm, 
						UserBoundary.class);
			
			// THEN the database contains a single user
			assertThat(this.userDao
				.readAll())
				.hasSize(1);
		}
		
		@Test
		public void testLoginValidUser() throws Exception{
			// GIVEN the database contains 3 users
			UserEntity testUser = new UserEntity("test@test.com", "2019b.tomc", "or", "newAvatar", UserRole.PLAYER, 40);
			this.userDao.create(testUser);

			
			// WHEN I GET users of size 10 and page 0
			UserBoundary response = 
			this.restTemplate
				.getForObject(
						this.baseUrl + "/smartspace/users/login/{userSmartspace}/{userEmail}", 
						UserBoundary.class, 
						"2019b.tomc", "test@test.com", 10, 0);
			
			// THEN I receive 3 users
			assertThat(response.convertToEntity()).isEqualToComparingFieldByField(testUser);
		}
		@Test
		public void testGetAllUsersUsingPaginationAsAdmin() throws Exception{
			// GIVEN the database contains 3 users
			int size = 3;
			IntStream.range(1, size + 1)
				.mapToObj(i->new UserEntity("emailTest@gmailTest.com"+i,
						"2019B.test",
						"userName"+i,
						"avatar.png",
						UserRole.PLAYER,
						0l))
				.peek(i-> i.setKey(i+"#2019B.test"))
				.forEach(this.userDao::importUser);
			
			// WHEN I GET users of size 10 and page 0
			UserBoundary[] response = 
			this.restTemplate
				.getForObject(
						this.baseUrl + "/smartspace/admin/users/{adminSmartspace}/{adminEmail}?size={size}&page={page}", 
						UserBoundary[].class, 
						"2019B.test", "admin", 10, 0);
			
			// THEN I receive 3 users +1 Admin
			assertThat(response)
				.hasSize(size+1);
		}
		
		@Test
		public void testUpdateUserWithoutTheirPoints() throws Exception{
			// GIVEN the database contains a user
			UserEntity testUserEntity = new UserEntity("test@test.com", "2019b.tomc", "or", "newAvatar", UserRole.PLAYER, 40);
			this.userDao.create(testUserEntity);
			
			
			UserBoundary testUserBoundary = new UserBoundary();
			testUserBoundary.setAvatar("newAvatar");
			testUserBoundary.setKey(new UserKeyBoundary());
			testUserBoundary.setPoints(49);
			testUserBoundary.setRole(UserRole.PLAYER);
			testUserBoundary.setUserName("TEST");

			this.restTemplate
				.put(this.baseUrl + "/smartspace/users/login/{userSmartspace}/{userEmail}", 
						testUserBoundary, 
						"test@test.com", "test@test.com");
			
			// THEN the database contains updated details
			assertThat(this.userDao.readById(testUserEntity.getKey()))
				.isNotNull()
				.isPresent()
				.get()
				.extracting("key")
				.containsExactly(testUserEntity.getKey());
			
		}

}
