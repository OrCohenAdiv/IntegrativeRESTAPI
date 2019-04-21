package smartspace.layout;


import smartspace.data.UserEntity;
import smartspace.data.UserRole;



public class UserBoundary {
	
	private String key;
	private String userName;
	private String avatar;
	private UserRole role;
	private long points;
	
	public UserBoundary() {
	}

	public UserBoundary(UserEntity entity) {
		
		this.key = entity.getKey().toString();
		
		this.userName = entity.getUserName();
		
		this.avatar = entity.getAvatar();
		
		this.role = entity.getRole();
		
		this.points = entity.getPoints();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public UserEntity convertToEntity() {
		
		UserEntity entity = new UserEntity();
		
		entity.setAvatar(this.avatar);
		
		entity.setKey(this.key);
		
		entity.setPoints(this.points);
		
		entity.setRole(this.role);
		
		entity.setUserName(this.userName);
		
		return entity;
	}
	

}
