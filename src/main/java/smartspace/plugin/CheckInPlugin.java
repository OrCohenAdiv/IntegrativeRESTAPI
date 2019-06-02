package smartspace.plugin;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CheckInPlugin implements Plugin {

	private ObjectMapper jackson;
    private EnhancedElementDao<String> elementDao;
	
	@Autowired
	public CheckInPlugin() {
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
					
			CheckInInput checkInInput = 
					this.jackson.readValue(
							this.jackson.writeValueAsString(actionEntity.getMoreAttributes()), 
							CheckInInput.class);
						
			actionEntity.getMoreAttributes().put("Checked", " In");
			
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}










