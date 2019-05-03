package smartspace.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedUserDao;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

@Service
public class UserServiceImpl implements UserService {
	
private EnhancedUserDao<String> userDao;
	
	@Autowired	
	public UserServiceImpl(EnhancedUserDao<String> userDao) {
		this.userDao = userDao;
	}
	
	private boolean valiadate(UserEntity entity) {
		return entity.getAvatar() != null &&
				!entity.getAvatar().trim().isEmpty() &&
				entity.getUserName() != null &&
				!entity.getUserName().trim().isEmpty() &&
				entity.getRole() != null;
	}

	@Override
	public UserEntity newUser(UserEntity entity, String adminEmail, String adminSmartspace) {
		// validate code
		
		String key = adminEmail + "=" + adminSmartspace;
		
		UserEntity userInSmartspace = userDao.readById(key).orElseThrow(() -> new RuntimeException("not user to update"));;
		
		if(userInSmartspace.getRole()!= UserRole.ADMIN)
			throw new RuntimeException("you are not allowed to create users");
				
		if (valiadate(entity)) {
			return this.userDao.create(entity);
		} else {
			throw new RuntimeException("invalid message");
		}
	}

	@Override
	public List<UserEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail){
		
		String key = adminEmail + "=" + adminSmartspace;
		
		UserEntity entity = userDao.readById(key).orElseThrow(() -> new RuntimeException("not user to update"));;
		
		if(entity.getRole()!= UserRole.ADMIN)
			throw new RuntimeException("you are not allowed to create messages");
		
		return this.userDao
				.readAll("key", size, page);
	}

}



