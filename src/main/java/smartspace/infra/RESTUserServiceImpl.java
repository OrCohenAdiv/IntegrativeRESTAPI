package smartspace.infra;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

public class RESTUserServiceImpl implements RESTUserService {

	private EnhancedUserDao<String> userDao;
	private String smartspaceName;

	@Override
	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail) {

		String key = userSmartspace + "=" + userEmail;

		if (!UserEntity.getRole().equals(UserRole.MANAGER)) {
			throw new RuntimeException("you are not a MANAGER");
		}

		userDao.update(UserEntity);

	}

}
