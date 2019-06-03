package smartspace.plugin;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;

@Component
public class CheckOutPlugin implements Plugin {

	private ObjectMapper jackson;
    private EnhancedElementDao<String> elementDao;

	@Autowired
	public CheckOutPlugin() {
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
			
			CheckOutInput checkInInput = 
					this.jackson.readValue(
							this.jackson.writeValueAsString(actionEntity.getMoreAttributes()), 
							CheckOutInput.class);
				
			if(checkInInput.getCheckOut() != null) {
				actionEntity.getMoreAttributes().put("Checked Out:", "SUCCESSFULLY");
			}
			else {
				actionEntity.getMoreAttributes().put("Checked Out At:", new Date());
			}
						
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

