package smartspace.layout;

import java.util.Date;
import java.util.Map;
import smartspace.data.ActionEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionBoundry {
	
	private ActionBoundryKey actionKey;
	private String type;
	private Date created;	
	private ElementKeyBondary element;
	private UserKeyBoundry player;
	private Map<String,Object> properties;
	
	public ActionBoundry() {
	}
	
	public ActionBoundry(ActionEntity entity){
		
//		this.setType(entity.getActionType());
//		this.setCreated(entity.getCreationTimestamp());
//		this.setProperties(entity.getMoreAttributes());
//		
//		this.actionKey.setId(entity.getActionId());	
//		this.actionKey.setSmartspace(entity.getActionSmartspace());	
//
//		this.element.setId(entity.getElementId());
//		this.element.setSmartspace(entity.getElementSmartspace());
//
//		this.player.setPlayerEmail(entity.getPlayerEmail());
//		this.player.setPlayerSmartspace(entity.getPlayerSmartspace());
//	}
		if(actionKey==null) {
			this.actionKey = new ActionBoundryKey();			
		}
		if(entity.getKey() != null) {
			this.actionKey.setId(entity.getActionId());
			this.actionKey.setSmartspace(entity.getActionSmartspace());
		}
		
		if(element==null) {
			this.element = new ElementKeyBondary();			
		}
		if(entity.getElementId() != null && entity.getElementSmartspace() != null) {
			this.element.setId(entity.getElementId());
			this.element.setSmartspace(entity.getElementSmartspace());
		}
		
		if(player==null) {
			this.player = new UserKeyBoundry();			
		}
		if(entity.getPlayerEmail() != null && entity.getPlayerSmartspace() != null) {
			this.player.setPlayerEmail(entity.getPlayerEmail());
			this.player.setPlayerSmartspace(entity.getPlayerSmartspace());
		}
	}
	
	
	public ActionBoundryKey getActionKey() {
		return actionKey;
	}

	public void setActionKey(ActionBoundryKey actionKey) {
		this.actionKey = actionKey;
	}

	public ElementKeyBondary getElement() {
		return element;
	}

	public void setElement(ElementKeyBondary element) {
		this.element = element;
	}

	public UserKeyBoundry getPlayer() {
		return player;
	}

	public void setPlayer(UserKeyBoundry player) {
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
		
//		entity.setActionId(this.actionKey.getId());
//		entity.setActionSmartspace(this.actionKey.getSmartspace());
//		
//		String key = entity.getActionSmartspace() + "=" + entity.getActionId();
//		entity.setKey(key);
//
//		entity.setElementId(this.element.getId());
//		entity.setElementSmartspace(this.element.getSmartspace());
//		
//		entity.setPlayerEmail(this.player.getPlayerEmail());
//		entity.setPlayerSmartspace(this.player.getPlayerSmartspace());
//		
//		entity.setCreationTimestamp(this.created);
//		entity.setMoreAttributes(this.properties); 
//		entity.setActionType(this.type);
		
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
		if(this.player.getPlayerEmail() != null && this.player.getPlayerSmartspace() != null) {
			entity.setPlayerEmail(this.player.getPlayerEmail());
			entity.setPlayerSmartspace(this.player.getPlayerSmartspace());
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


class ActionBoundryKey {
	private String smartspace;
	private String id;
	
	public ActionBoundryKey() {
	}
	
	public String getSmartspace() {
		return smartspace;
	}
	public void setSmartspace(String smartspace) {
		this.smartspace = smartspace;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}


class UserKeyBoundry {
	
	private String smartspace;
	private String email;
	
	public UserKeyBoundry() {
	}

	public String getPlayerSmartspace() {
		return smartspace;
	}

	public void setPlayerSmartspace(String playerSmartspace) {
		this.smartspace = playerSmartspace;
	}

	public String getPlayerEmail() {
		return email;
	}

	public void setPlayerEmail(String playerEmail) {
		this.email = playerEmail;
	}
}

