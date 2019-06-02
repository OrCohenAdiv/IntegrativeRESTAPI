package smartspace.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import smartspace.data.ActionEntity;

@Component
public class RoomServicePricePlugin implements Plugin {

	private ObjectMapper jackson;

	@Autowired
	public RoomServicePricePlugin() {
		super();
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		try {
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
