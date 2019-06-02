package smartspace.plugin;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import smartspace.data.ActionEntity;

@Component
public class CheckOutPlugin implements Plugin {

	private ObjectMapper jackson;

	@Autowired
	public CheckOutPlugin() {
		super();
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		try {
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

