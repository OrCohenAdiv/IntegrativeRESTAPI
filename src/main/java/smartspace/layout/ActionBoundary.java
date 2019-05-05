package smartspace.layout;

import java.util.Date;
import java.util.Map;
import smartspace.data.ActionEntity;
import smartspace.layout.data.ActionKeyBoundary;
import smartspace.layout.data.ElementKeyBoundary;
import smartspace.layout.data.UserKeyBoundary;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ActionBoundary {
	
	private ActionKeyBoundary actionKey;
	private String type;
	private Date created;	
	private ElementKeyBoundary element;
	private UserKeyBoundary player;
	private Map<String,Object> properties;
	
	public ActionBoundary() {
	}
	
	public ActionBoundary(ActionEntity entity){
		
		if(actionKey==null) {
			this.actionKey = new ActionKeyBoundary();			
		}
		if(entity.getKey() != null) {
			this.actionKey.setId(entity.getActionId());
			this.actionKey.setSmartspace(entity.getActionSmartspace());
		}
		
		if(element==null) {
			this.element = new ElementKeyBoundary();			
		}
		if(entity.getElementId() != null && entity.getElementSmartspace() != null) {
			this.element.setId(entity.getElementId());
			this.element.setSmartspace(entity.getElementSmartspace());
		}
		
		if(player==null) {
			this.player = new UserKeyBoundary();			
		}
		if(entity.getPlayerEmail() != null && entity.getPlayerSmartspace() != null) {
			this.player.setEmail(entity.getPlayerEmail());
			this.player.setSmartspace(entity.getPlayerSmartspace());
		}
	}
	
	
	public ActionKeyBoundary getActionKey() {
		return actionKey;
	}

	public void setActionKey(ActionKeyBoundary actionKey) {
		this.actionKey = actionKey;
	}

	public ElementKeyBoundary getElement() {
		return element;
	}

	public void setElement(ElementKeyBoundary element) {
		this.element = element;
	}

	public UserKeyBoundary getPlayer() {
		return player;
	}

	public void setPlayer(UserKeyBoundary player) {
		this.player = player;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Map<String,Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String,Object> properties) {
		this.properties = properties;
	}
	
	
	public ActionEntity convertToEntity(){
		
		ActionEntity entity =  new ActionEntity();
		
		entity.setKey("=");
		if(this.actionKey.getId() != null && this.actionKey.getSmartspace() != null) {
			entity.setActionId(this.actionKey.getId());
			entity.setActionSmartspace(this.actionKey.getSmartspace());
			entity.setKey(this.actionKey.getSmartspace() + "=" + this.actionKey.getId());
		}
		if(this.element.getId() != null && this.element.getSmartspace() != null) {
			entity.setElementId(this.element.getId());
			entity.setElementSmartspace(this.element.getSmartspace());
		}
		if(this.player.getEmail() != null && this.player.getSmartspace() != null) {
			entity.setPlayerEmail(this.player.getEmail());
			entity.setPlayerSmartspace(this.player.getSmartspace());
		}	
		entity.setCreationTimestamp(this.created);
		entity.setMoreAttributes(this.properties); 
		entity.setActionType(this.type);

		return entity;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}




