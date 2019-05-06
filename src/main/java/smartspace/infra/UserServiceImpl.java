package smartspace.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

@Service
public class UserServiceImpl implements UserService {

	private EnhancedUserDao<String> userDao;
	@Value("${smartspace.name}")
	private String mySmartSpace;

	@Autowired
	public UserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}

	private boolean validate(UserEntity entity) {
		return entity.getAvatar() != null && !entity.getAvatar().trim().isEmpty() && entity.getUserName() != null
				&& !entity.getUserName().trim().isEmpty() && entity.getRole() != null;
	}

	@Override
	public UserEntity newUser(UserEntity entity, String adminEmail, String adminSmartspace) {
		// validate code

		String key = adminEmail + "=" + adminSmartspace;

		UserEntity userInSmartspace = userDao.readById(key)
				.orElseThrow(() -> new RuntimeException("no such user exists"));
		;

		if (userInSmartspace.getRole() != UserRole.ADMIN) {

			if (entity.getUserSmartspace().equals(mySmartSpace))
				throw new RuntimeException("Invalid! same smartspace");

			if (validate(entity)) {
				return this.userDao.create(entity);
			} else {
				throw new RuntimeException("user has invalid data");
			}
		} else {
			throw new RuntimeException("Not admin you are not allowed to create users");
		}
	}

	@Override
	public List<UserEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail) {

		String key = adminEmail + "=" + adminSmartspace;

		UserEntity entity = userDao.readById(key).orElseThrow(() -> new RuntimeException("no such user exists"));
		;

		if (entity.getRole() != UserRole.ADMIN)
			throw new RuntimeException("only the admin is allowed to create users");

		return this.userDao.readAll("key", size, page);
	}

}
