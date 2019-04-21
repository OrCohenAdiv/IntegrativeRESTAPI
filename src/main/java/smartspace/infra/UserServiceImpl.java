package smartspace.infra;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;


public class UserServiceImpl implements UserService {
	
private EnhancedUserDao<String> userDao;
	
	@Autowired	
	public UserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserEntity newUser(UserEntity entity) {
		// validate code
		
		if(entity.getRole()!= UserRole.ADMIN)
			throw new RuntimeException("you are not allowed to create messages");
		
		if (valiadate(entity)) {
			return this.userDao.create(entity);
		}else {
			throw new RuntimeException("invalid message");
		}
	}

	private boolean valiadate(UserEntity entity) {
		return entity.getAvatar() != null &&
				!entity.getAvatar().trim().isEmpty() &&
				entity.getUserName() != null &&
				!entity.getUserName().trim().isEmpty() &&
				entity.getRole() != null;
	}

	@Override
	public List<UserEntity> getUsingPagination(int size, int page) {
		return this.userDao
				.readAll("key", size, page);
	}

}



