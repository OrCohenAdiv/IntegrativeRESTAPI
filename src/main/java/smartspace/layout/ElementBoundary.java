package smartspace.layout;

import java.util.Date;
import java.util.Map;

import smartspace.data.ElementEntity;
import smartspace.data.Location;
import smartspace.layout.data.ElementCreatorBoundary;
import smartspace.layout.data.ElementKeyBoundary;
import smartspace.layout.data.ElementLocationBoundary;

public class ElementBoundary {
	private ElementKeyBoundary key;
	private String name;
	private String elementType;
	private Date created;
	private boolean expired;
	private ElementCreatorBoundary creator;
	private ElementLocationBoundary latlng;
	private Map<String, Object> elementProperties;

	public ElementBoundary() {
	}

	public ElementBoundary(ElementEntity entity) {
		if (key == null) {
			this.key = new ElementKeyBoundary();
		}
		if (entity.getKey() != null) {
			String[] args = entity.getKey().split("=");
			this.key.setSmartspace(args[0]);
			this.key.setId(args[1]);
		}
		this.elementType = entity.getType();
		this.name = entity.getName();
		this.created = entity.getCreationTimestamp();
		this.expired = entity.isExpired();
		if (creator == null) {
			this.creator = new ElementCreatorBoundary();
		}
		this.creator.setEmail(entity.getCreatorEmail());
		this.creator.setSmartspace(entity.getCreatorSmartspace());
		if (latlng == null) {
			this.latlng = new ElementLocationBoundary();
		}
		this.latlng.setLat(entity.getLocation().getX());
		this.latlng.setLng(entity.getLocation().getY());
		this.elementProperties = entity.getMoreAttributes();
	}

	public ElementEntity convertToEntity() {
		ElementEntity entity = new ElementEntity();
//		entity.setKey("=");
		if (this.key.getId() != null && this.key.getSmartspace() != null) {
			entity.setKey(this.key.getId() + "=" + this.key.getSmartspace());
		}
		entity.setType(this.elementType);
		entity.setName(this.name);
		entity.setExpired(this.expired);
		entity.setCreationTimestamp(this.created);
		entity.setCreatorEmail(this.creator.getEmail());
		entity.setCreatorSmartspace(this.creator.getSmartspace());
		entity.setLocation(new Location(this.latlng.getLat(), this.latlng.getLng()));
		entity.setMoreAttributes(this.elementProperties);
		return entity;
	}

	public ElementKeyBoundary getKey() {
		return key;
	}

	public void setKey(ElementKeyBoundary key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String type) {
		this.elementType = type;
	}

	public Date getCreationTimestamp() {
		return created;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.created = creationTimestamp;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public ElementCreatorBoundary getCreator() {
		return creator;
	}

	public void setCreator(ElementCreatorBoundary creator) {
		this.creator = creator;
	}

	public ElementLocationBoundary getLatlng() {
		return latlng;
	}

	public void setLatlng(ElementLocationBoundary latlng) {
		this.latlng = latlng;
	}

	public Map<String, Object> getElementProperties() {
		return elementProperties;
	}

	public void setElementProperties(Map<String, Object> elementProperties) {
		this.elementProperties = elementProperties;
	}

}
