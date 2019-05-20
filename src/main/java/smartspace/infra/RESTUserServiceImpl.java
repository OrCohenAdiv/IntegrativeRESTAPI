package smartspace.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.layout.data.NewUserForm;

@Service
public class RESTUserServiceImpl implements RESTUserService {

	private EnhancedUserDao<String> userDao;
	private String smartspaceName;
	
	@Autowired
	public RESTUserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}

	@Value("${smartspace.name}")
	public void setRESTUserSmartspaceName(String smartspaceName) {
		this.smartspaceName = smartspaceName;
	}
	
	@Override
	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail) {

		String key = userSmartspace + "=" + userEmail;

		if (!UserEntity.getRole().equals(UserRole.MANAGER)) {
			throw new RuntimeException("you are not a MANAGER");
		}
		userDao.update(UserEntity);
	}
	
	@Override
	public UserEntity loginUser(UserEntity loginUser, String userSmartspace, String userEmail) {
		return this.userDao.readById(userSmartspace + "=" + userEmail)
				.orElseThrow(() -> new RuntimeException("User not found!"));
	}

	@Override
	public UserEntity createANewUser(NewUserForm createNewUser) {
		return new UserEntity(
				this.smartspaceName,
				createNewUser.getEmail(), 
				createNewUser.getUsername(), 
				createNewUser.getAvatar(), 
				createNewUser.getRole(), 
				0);
	}
}

