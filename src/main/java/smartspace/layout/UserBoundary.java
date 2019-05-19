package smartspace.layout;

import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.layout.data.UserKeyBoundary;

public class UserBoundary {

	private UserKeyBoundary key;
	private String userName;
	private String avatar;
	private UserRole role;
	private long points;

	public UserBoundary() {
	}

	public UserBoundary(UserEntity entity) {

		if (key == null) {
			this.key = new UserKeyBoundary();
		}
		if (entity.getKey() != null) {
			String[] args = entity.getKey().split("=");
			this.key.setSmartspace(args[0]);
			this.key.setEmail(args[1]);
		}
		this.userName = entity.getUserName();
		this.avatar = entity.getAvatar();
		this.role = entity.getRole();
		this.points = entity.getPoints();
	}

	public UserKeyBoundary getKey() {
		return key;
	}

	public void setKey(UserKeyBoundary key) {
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

//		entity.setKey("=");
		if (this.key.getEmail() != null && this.key.getSmartspace() != null) {
			entity.setKey(this.key.getSmartspace() + "=" + this.key.getEmail());
		}

		entity.setAvatar(this.avatar);

		entity.setPoints(this.points);

		entity.setRole(this.role);

		entity.setUserName(this.userName);

		return entity;
	}

}

