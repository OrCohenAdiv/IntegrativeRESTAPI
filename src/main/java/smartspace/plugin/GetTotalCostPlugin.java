package smartspace.plugin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;

@Component
public class GetTotalCostPlugin implements Plugin {
	
	private ObjectMapper jackson;
    private EnhancedElementDao<String> elementDao;
    private int roomPrice;
    private int roomServicePrice;
    private int totalPrice;
	
	@Autowired
	public GetTotalCostPlugin(EnhancedElementDao<String> elementDao) {
		super();
		this.elementDao = elementDao;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		
		int size = 10;
		try {
			
			TotalCostInput totalCostInput =
					this.jackson.readValue(
							this.jackson.writeValueAsString(actionEntity.getMoreAttributes()), 
							TotalCostInput.class);

			List<ElementEntity> elementList = 
					 this.elementDao
					.readElementsByKeyAndLocation(
							actionEntity.getElementSmartspace(),
							actionEntity.getElementId(),
							totalCostInput.getRoomNumber(), size);
			
			System.err.println(elementList);

			roomPrice = (int)elementList.get(0).getMoreAttributes().get("roomPrice");
			roomServicePrice = (int)elementList.get(0).getMoreAttributes().get("roomServicePrice");
			totalPrice = roomPrice + roomServicePrice;
			
			actionEntity.getMoreAttributes().put("TotalPrice", totalPrice);
			elementList.get(0).getMoreAttributes().put("TotalPrice", totalPrice);
	
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
