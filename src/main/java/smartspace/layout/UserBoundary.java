package smartspace.layout;

import smartspace.data.ElementEntity;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

public class UserBoundary {

	private UserKeyBondary key;
	private String userName;
	private String avatar;
	private UserRole role;
	private long points;

	public UserBoundary() {
	}

	public UserBoundary(UserEntity entity) {

		if (key == null) {
			this.key = new UserKeyBondary();
		}
		if (entity.getKey() != null) {
			String[] args = entity.getKey().split("=");
			this.key.setEmail(args[0]);
			this.key.setSmartspcae(args[1]);
		}
		this.userName = entity.getUserName();
		this.avatar = entity.getAvatar();
		this.role = entity.getRole();
		this.points = entity.getPoints();
	}

	public UserKeyBondary getKey() {
		return key;
	}

	public void setKey(UserKeyBondary key) {
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
		if (this.key.getEmail() != null && this.key.getSmartspcae() != null) {
			entity.setKey(this.key.getSmartspcae() + "=" + this.key.getEmail());
		}

		entity.setAvatar(this.avatar);

		entity.setPoints(this.points);

		entity.setRole(this.role);

		entity.setUserName(this.userName);

		return entity;
	}

}

class UserKeyBondary {

	private String email;
	private String smartspcae;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSmartspcae() {
		return smartspcae;
	}

	public void setSmartspcae(String smartspcae) {
		this.smartspcae = smartspcae;
	}
}
