package smartspace.layout;

import java.util.Date;
import java.util.Map;

import smartspace.data.ElementEntity;
import smartspace.data.Location;

public class ElementBoundary {
	private String key;
	private String name;
	private String type;
	private Date creationTimestamp;
	private boolean expired;
	private String creator;
	private String latlng;
	private Map<String, Object> elementProperties;

	public ElementBoundary() {
	}

	public ElementBoundary(ElementEntity entity) {
		this.key = entity.getKey();
		this.name = entity.getName();
		this.type = entity.getType();
		this.creationTimestamp = entity.getCreationTimestamp();
		this.expired = entity.isExpired();
		this.creator = entity.getCreatorEmail() + "#" + entity.getCreatorSmartspace();

		if (entity.getLocation() != null) {
			this.latlng = entity.getLocation().getX() + "#" + entity.getLocation().getY();
		}
		this.elementProperties = entity.getMoreAttributes();
	}

	public ElementEntity convertToEntity() {
		ElementEntity entity = new ElementEntity();

		entity.setKey(this.key);
		entity.setType(this.type);
		entity.setName(this.name);
		entity.setExpired(this.expired);
		entity.setCreationTimestamp(this.creationTimestamp);
		entity.setCreatorEmail(null);
		entity.setCreatorSmartspace(null);
		if (this.creator != null) {
			String[] splitedCreator = this.creator.split("#");
			if (splitedCreator.length == 2) {
				entity.setCreatorEmail(splitedCreator[0]);
				entity.setCreatorSmartspace(splitedCreator[1]);
			}
		}

		entity.setLocation(null);
		if (this.latlng != null) {
			String[] splitedLocation = this.latlng.split("#");
			if (splitedLocation.length == 2) {
				entity.setLocation(new Location(
								Double.parseDouble(splitedLocation[0]),
								Double.parseDouble(splitedLocation[1])));
			}
		}
		entity.setMoreAttributes(this.elementProperties);

		return entity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public Map<String, Object> getElementProperties() {
		return elementProperties;
	}

	public void setElementProperties(Map<String, Object> elementProperties) {
		this.elementProperties = elementProperties;
	}
	
	

}
