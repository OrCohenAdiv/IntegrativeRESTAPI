package smartspace.infra;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.ActionEntity;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.layout.UserBoundary;

@Service
public class UserServiceImpl implements UserService {

	private EnhancedUserDao<String> userDao;
	private String smartspaceName;

	@Autowired
	public UserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}

	private boolean validate(UserEntity entity) {
		return entity.getAvatar() != null && !entity.getAvatar().trim().isEmpty() && entity.getUserName() != null
				&& !entity.getUserName().trim().isEmpty() && entity.getRole() != null;
	}

	@Override
	@Transactional
	public List<UserEntity> newUser(UserEntity[] allEntities, String adminSmartspace, String adminEmail) {
		List<UserEntity> userEntites = new LinkedList<>();

		String key = adminSmartspace + "=" + adminEmail;

		UserEntity adminUserEntity = userDao.readById(key)
				.orElseThrow(() -> new RuntimeException("user doesn't exist"));

		if (!adminUserEntity.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("you are not an ADMIN");
		}
		for (UserEntity userEntity : allEntities) {

			if (validate(userEntity)) {
				if (userEntity.getUserSmartspace().equals(smartspaceName))
					throw new RuntimeException("Illigal Import!");
				else
					userEntites.add(this.userDao.importUser(userEntity));
			} else {
				throw new RuntimeException("Invalid User");
			}

		}
		return userEntites;
	}

	@Override
	public List<UserEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail) {

		String key = adminSmartspace + "=" + adminEmail;

		UserEntity entity = userDao.readById(key).orElseThrow(() -> new RuntimeException("no such user exists"));
		;

		if (entity.getRole() != UserRole.ADMIN)
			throw new RuntimeException("only the admin is allowed to create users");

		return this.userDao.readAll("key", size, page);
	}
	
	
	@Value("${smartspace.name}")
	public void setSmartspaceName(String smartspaceName) {
		this.smartspaceName = smartspaceName;
	}
	
	//ADDED amir
	public UserEntity loginUser(UserEntity loginUser,
			String userSmartspace,String userEmail) {
		return this.userDao.readById(userSmartspace+"="+userEmail)
				.orElseThrow(()->new RuntimeException("User not found!"));
	}

}
