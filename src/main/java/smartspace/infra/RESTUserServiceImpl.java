package smartspace.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

@Service
public class RESTUserServiceImpl implements RESTUserService {

	private EnhancedUserDao<String> userDao;
	private String smartspaceName;
	
	@Autowired
	public RESTUserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}

	@Override
	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail) {

		String key = userSmartspace + "=" + userEmail;

		if (!UserEntity.getRole().equals(UserRole.MANAGER)) {
			throw new RuntimeException("you are not a MANAGER");
		}
		userDao.update(UserEntity);
	}

	public UserEntity loginUser(UserEntity loginUser, String userSmartspace, String userEmail) {
		return this.userDao.readById(userSmartspace + "=" + userEmail)
				.orElseThrow(() -> new RuntimeException("User not found!"));
	}

}
