package smartspace.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public void updateUser(UserEntity user, String userSmartspace, String userEmail,UserRole role) {
		if(user.getRole() == UserRole.PLAYER && role != UserRole.PLAYER) {
			user.setPoints(0);
		}
		this.userDao.update(user);
	}

	public UserEntity convertToUserEntity(NewUserForm newUser) {
		return new UserEntity(
				this.smartspaceName,
				newUser.getEmail(),
				newUser.getUsername(),
				newUser.getAvatar(),
				newUser.getRole(),
				0);
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

