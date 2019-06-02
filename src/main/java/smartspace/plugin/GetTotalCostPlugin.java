package smartspace.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedActionDao;
import smartspace.dao.EnhancedElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;

@Component
public class GetTotalCostPlugin implements Plugin {
	
	private ObjectMapper jackson;
    private EnhancedElementDao<String> elementDao;
    private EnhancedActionDao actionDao;
    private int roomPrice;
    private int roomServicePrice;
    private int totalPrice;
	
	@Autowired
	public GetTotalCostPlugin(EnhancedElementDao<String> elementDao, EnhancedActionDao actionDao) {
		super();
		this.elementDao = elementDao;
		this.actionDao = actionDao;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		int size = 10;
		int page = 0;
		try {		
			ElementEntity elementEntity = 
					 this.elementDao.readById(
							 actionEntity.getElementSmartspace() +"="+ actionEntity.getElementId())
					 .orElseThrow(() -> new RuntimeException("element does not exist"));
									
			CostInput totalCostInput =
					this.jackson.readValue(
							this.jackson.writeValueAsString(elementEntity.getMoreAttributes()), 
							CostInput.class);
			
			roomPrice = totalCostInput.getRoomPrice();
			
//			List<ActionEntity> listActions = 
//					this.actionDao
//					.readAll(size, page)
//					.stream()
//					.findAny()
//					.filter(actionEntity.getElementId())
//					.equals(actionEntity.getElementId());
			
			
			//listActions = listActions.stream().filter(predicate);
			
			
			
			roomServicePrice = totalCostInput.getRoomServicePrice();
			
			totalPrice = 0;
			if(roomPrice >= 0) {
				totalPrice += roomPrice;
			}
			if(roomServicePrice >= 0) {
				totalPrice += roomServicePrice;
			}
			
			actionEntity.getMoreAttributes().put("TotalPrice", totalPrice);
			//elementList.get(0).getMoreAttributes().put("TotalPrice", totalPrice);
	
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
