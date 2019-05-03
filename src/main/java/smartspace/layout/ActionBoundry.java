package smartspace.layout;

import java.util.Date;
import java.util.Map;

import smartspace.data.ActionEntity;
import smartspace.data.Location;

public class ActionBoundry {
	private ActionKeyBoundry actionKey;
	private String actionType;
	private Date creationTimestamp;	
	private ElementKeyBondary element;
	private UserKeyBoundry player;
	private Map<String,Object> moreAttributes;
	
	public ActionBoundry() {
	}
	
	public ActionBoundry(ActionEntity entity){
		
		this.setActionType(entity.getActionType());
		this.setCreationTimestamp(entity.getCreationTimestamp());
		this.setMoreAttributes(entity.getMoreAttributes());
				
		if(actionKey==null) {
			this.actionKey = new ActionKeyBoundry();			
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
		if(this.player.getPlayerEmail() != null && this.player.getPlayerSmartspace() != null) {
			entity.setPlayerEmail(this.player.getPlayerEmail());
			entity.setPlayerSmartspace(this.player.getPlayerSmartspace());
		}	
		entity.setCreationTimestamp(this.creationTimestamp);
		entity.setMoreAttributes(this.moreAttributes); 
		entity.setActionType(this.actionType);
		return entity;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Map<String,Object> getMoreAttributes() {
		return moreAttributes;
	}

	public void setMoreAttributes(Map<String,Object> moreAttributes) {
		this.moreAttributes = moreAttributes;
	}
}


class ActionKeyBoundry {
	private String smartspace;
	private String id;
	
	public ActionKeyBoundry() {
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
	
	private String playerSmartspace;
	private String playerEmail;
	
	public UserKeyBoundry() {
	}

	public String getPlayerSmartspace() {
		return playerSmartspace;
	}

	public void setPlayerSmartspace(String playerSmartspace) {
		this.playerSmartspace = playerSmartspace;
	}

	public String getPlayerEmail() {
		return playerEmail;
	}

	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}
}

