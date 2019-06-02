package smartspace.plugin;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import smartspace.dao.EnhancedActionDao;
import smartspace.data.ActionEntity;

/*
////EXAMPLE FOR POSTMAN ///////////
{	
	"actionKey":{
		"id":"4397",
		"smartspace":"2019b.tomc"
	},
	"type":"ReserveTableInDiningRoom",
	"created":2019-01-01,
	"element":{
		"id":"50",
		"smartspace":"2019b.tomc"
	},
	"player":{
		"smartspace":"2019b.tomc",
		"email":"player.invoking.action@de.no"
	},
	"properties":{
		"dateOfDiningTableReservation":"2019-05-30,16:52",
		"notes":"No peanuts",		
	}
}

////EXAMPLE FOR POSTMAN ///////////
{
	"actionKey":{
		"id":"43",
		"smartspace":"2019b.tomc"
	},
	"type":"ReserveTableInDiningRoom",
	"created":"2019-01-01",
	"element":{
		"id":"50",
		"smartspace":"2019b.tomc"
	},
	"player":{
		"smartspace":"2019b.tomc",
		"email":"player.invoking.action@de.no"
	},
	"properties":{
		"dateOfDiningTableReservation":"2019-07-30,19:30:30"
	}
}
*/

@Component
public class ReserveTableInDiningRoomPlugin implements Plugin {

	private ObjectMapper jackson;

	@Autowired
	public ReserveTableInDiningRoomPlugin(EnhancedActionDao actionDao) {
		super();
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity actionEntity) {
		
		try {
			ReserveTableInDiningRoomInput reserveTableInDiningRoomInput = 
					this.jackson.readValue(
							this.jackson.writeValueAsString(actionEntity.getMoreAttributes()), 
							ReserveTableInDiningRoomInput.class);
						
			if(reserveTableInDiningRoomInput.getDateTableReserv() != null) {
				actionEntity.getMoreAttributes().put("The table was:", "SUCCESSFULLY SAVED");
			}
			else {
				actionEntity.getMoreAttributes().put("Table Reservation At", new Date());
			}
			
			return actionEntity;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}



