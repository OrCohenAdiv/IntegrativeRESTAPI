package smartspace.infra;

import java.util.List;

import smartspace.data.UserEntity;


public interface UserService {
	
	public UserEntity newUser(UserEntity entity, String adminEmail, String adminSmartspace);
	
	public List<UserEntity> getUsingPagination (int size, int page, String adminSmartspace, String adminEmail);

}
