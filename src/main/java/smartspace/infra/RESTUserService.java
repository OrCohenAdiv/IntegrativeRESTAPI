package smartspace.infra;

import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.layout.data.NewUserForm;

public interface RESTUserService {

	public void updateUser(UserEntity user, String userSmartspace, String userEmail,UserRole role);

	public UserEntity loginUser(UserEntity loginUser, String userSmartspace, String userEmail);

	public UserEntity createANewUser(NewUserForm createNewUser);
}
