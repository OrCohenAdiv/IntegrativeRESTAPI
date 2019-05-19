package smartspace.infra;

import smartspace.data.UserEntity;

public interface RESTUserService {

	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail);

	public UserEntity loginUser(UserEntity loginUser, String userSmartspace, String userEmail);

}
