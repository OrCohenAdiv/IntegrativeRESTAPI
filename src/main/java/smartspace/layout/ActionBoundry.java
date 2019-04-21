package smartspace.layout;

import java.util.Date;
import java.util.Map;

import smartspace.data.ActionEntity;

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
		if(key=null)
			this.actionKey = entity.getKey();
		this.elementSmartspace = entity.elementSmartspace;
		this.elementId = entity.elementId;
		this.playerSmartspace = entity.playerSmartspace;
		this.playerEmail = entity.playerEmail;
		this.actionType = entity.actionType;
		this.creationTimestamp = entity.getCreationTimestamp();
		this.moreAttributes = entity.getMoreAttributes();
	}



	public ActionEntity convertToEntity(){
		ActionEntity entity =  new ActionEntity();
		
		entity.setKey("=");
		
		
		return entity;
	}
	
}
