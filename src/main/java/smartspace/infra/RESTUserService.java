package smartspace.infra;


import smartspace.data.UserEntity;
import smartspace.layout.ElementBoundary;


public interface RESTUserService {
	
	public void updateUser(UserEntity UserEntity, String userSmartspace, String userEmail);
	
	

}
