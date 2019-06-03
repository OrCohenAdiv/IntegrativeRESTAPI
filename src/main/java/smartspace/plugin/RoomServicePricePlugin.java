package smartspace.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;

@Component
public class RoomServicePricePlugin implements Plugin {

	private ObjectMapper jackson;
    private EnhancedElementDao<String> elementDao;

	@Autowired
	public RoomServicePricePlugin() {
		super();
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		
		try {
			
			ElementEntity elementEntity = 
					 this.elementDao.readById(
							 actionEntity.getElementSmartspace() +"="+ actionEntity.getElementId())
					 .orElseThrow(() -> new RuntimeException("element does not exist"));
			
			//make sure the element is room	
			if(elementEntity.getType().toLowerCase().contains("room")) {
				throw new RuntimeException("I'm sorry but this is NOT a room!");
			}
			
			//make sure the element is available 
			if(elementEntity.isExpired()) {
				throw new RuntimeException("You must CHECKED IN the room\nbefore attempting to perform any actions registered on it");
			}

			RoomServicePriceInput roomServicePriceInput = 
					this.jackson.readValue(
							this.jackson.writeValueAsString(actionEntity.getMoreAttributes()), 
							RoomServicePriceInput.class);
						
			if(roomServicePriceInput.getRoomServicePrice() > 0) {
				actionEntity.getMoreAttributes().put("room service was:", "SUCCESSFULLY SAVED");
			}
			
			else {
				actionEntity.getMoreAttributes().put("room service is:", 0);
			}
			
			actionEntity.getMoreAttributes().put("roomServicePrice", roomServicePriceInput.getRoomServicePrice());
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
