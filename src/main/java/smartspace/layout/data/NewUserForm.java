package smartspace.layout.data;

import smartspace.data.UserEntity;
import smartspace.data.UserRole;

public class NewUserForm {

	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	
	public NewUserForm(){
	}
	
	public NewUserForm(String email, String role, String username, String avatar){
		this.email = email;
		this.setRole(UserRole.valueOf(role));
		this.username = username;
		this.avatar = avatar;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserEntity convertToEntity() {
		UserEntity userEntity = new UserEntity();
		if (this.getEmail() != null) {
			userEntity.setUserEmail(this.getEmail());
		} else {
			throw new NullPointerException("Null email");
		}
		userEntity.setUserName(this.getUsername());
		userEntity.setAvatar(this.getAvatar());
		userEntity.setRole(this.getRole());
		userEntity.setPoints(0);
		return userEntity;
	}
}
