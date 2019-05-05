package smartspace.layout;

import java.util.Date;
import java.util.Map;

import smartspace.data.ElementEntity;
import smartspace.data.Location;

public class ElementBoundary {
	private ElementKeyBoundary key;
	private String name;
	private String elementType;
	private Date created;
	private boolean expired;
	private ElementCreatorBondary creator;
	private ElementLocationBondary latlng;
	private Map<String, Object> elementProperties;

	public ElementBoundary() {
	}

	public ElementBoundary(ElementEntity entity) {
		if(key==null) {
			this.key=new ElementKeyBoundary();
		}
		if(entity.getKey()!=null) {
			String[] args = entity.getKey().split("=");
			this.key.setId(args[0]);
			this.key.setSmartspace(args[1]);
		}
		this.elementType = entity.getType();
		this.name = entity.getName();
		this.created = entity.getCreationTimestamp();
		this.expired = entity.isExpired();
		if(creator==null) {
			this.creator = new ElementCreatorBondary();
		}
		this.creator.setEmail(entity.getCreatorEmail());
		this.creator.setSmartspcae(entity.getCreatorSmartspace());
		if(latlng==null) {
			this.latlng = new ElementLocationBondary();
		}
		this.latlng.setLat(entity.getLocation().getX());
		this.latlng.setLng(entity.getLocation().getY());
		this.elementProperties = entity.getMoreAttributes();
	}

	public ElementEntity convertToEntity() {
		ElementEntity entity = new ElementEntity();
		entity.setKey("=");
		if(this.key.getId()!=null && this.key.getSmartspace()!=null) {
			entity.setKey(this.key.getSmartspace()+"="+this.key.getId());
		}
		entity.setType(this.elementType);
		entity.setName(this.name);
		entity.setExpired(this.expired);
		entity.setCreationTimestamp(this.created);
		entity.setCreatorEmail(this.creator.getEmail());
		entity.setCreatorSmartspace(this.creator.getSmartspcae());
		entity.setLocation(new Location(this.latlng.getLat(),this.latlng.getLng()));
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

	public ElementCreatorBondary getCreator() {
		return creator;
	}

	public void setCreator(ElementCreatorBondary creator) {
		this.creator = creator;
	}

	public ElementLocationBondary getLatlng() {
		return latlng;
	}

	public void setLatlng(ElementLocationBondary latlng) {
		this.latlng = latlng;
	}

	public Map<String, Object> getElementProperties() {
		return elementProperties;
	}

	public void setElementProperties(Map<String, Object> elementProperties) {
		this.elementProperties = elementProperties;
	}

}

class ElementKeyBoundary{
	private String id;
	private String smartspace;
	
	public ElementKeyBoundary() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSmartspace() {
		return smartspace;
	}

	public void setSmartspace(String smartspace) {
		this.smartspace = smartspace;
	}
}

class ElementCreatorBondary{
	private String email;
	private String smartspcae;
	
	public ElementCreatorBondary() {
	}

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

class ElementLocationBondary{
	private double lat;
	private double lng;
	
	public ElementLocationBondary() {
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
}
