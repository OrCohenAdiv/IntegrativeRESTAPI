package smartspace.layout.data;

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

	
}
