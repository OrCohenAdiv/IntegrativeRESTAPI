package smartspace.infra;

import smartspace.data.UserEntity;
import smartspace.layout.data.NewUserForm;

public interface RESTUserService {

	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail);

	public UserEntity loginUser(UserEntity loginUser, String userSmartspace, String userEmail);

	public UserEntity createANewUser(NewUserForm createNewUser);
}
