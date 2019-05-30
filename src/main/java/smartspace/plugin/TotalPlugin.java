package smartspace.plugin;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor.Key;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import smartspace.dao.EnhancedElementDao;
import smartspace.dao.memory.MemoryElementDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;
import smartspace.data.Location;

@Component
public class TotalPlugin implements Plugin {
	
	private ObjectMapper jackson;
	private Location roomNumber;
	private int sum;
    private EnhancedElementDao<String> dao;
	private String elementId;
	private String elementSmartspace;
	private Key k;
	private int totalPrice;
	
	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
		
	}
	
	

	@Override
	public ActionEntity process(ActionEntity action) {
		
		try {
			elementId = action.getElementId();
			ElementEntity element = dao.readById(elementId).orElseThrow(() -> new RuntimeException("element doesn't exist"));
			elementSmartspace = action.getElementSmartspace();
			TotalCostInput totalCostInput = this.jackson.readValue(
					this.jackson.writeValueAsString(action.getMoreAttributes()),
					TotalCostInput.class);
			Location number = totalCostInput.getNumber();
			

			if (number == this.roomNumber) {
				totalPrice = (int) action.getMoreAttributes().get("price");
				totalPrice += sum;
			}
			action.getMoreAttributes().put("price",totalPrice );
			
			return action;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
