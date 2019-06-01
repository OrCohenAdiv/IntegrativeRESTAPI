package smartspace.plugin;

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
		"dateOfDiningTableReservation":"2019-05-30T16:52:35.170+0000",
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
		"dateOfDiningTableReservation":"2019-07-30T19:30:35.170+0000"
	}
}
*/

@Component
public class ReserveTableInDiningRoomPlugin implements Plugin {

	//private EnhancedActionDao actionDao;
	private ObjectMapper jackson;

	@Autowired
	public ReserveTableInDiningRoomPlugin(EnhancedActionDao actionDao) {
		super();
		//this.actionDao = actionDao;
		this.jackson = new ObjectMapper();
	}
	
	@Override
	public ActionEntity process(ActionEntity action) {
		
		try {
			ReserveTableInDiningRoomInput reserveTableInDiningRoomInput = 
					this.jackson.readValue(
							this.jackson.writeValueAsString(action.getMoreAttributes()), 
							ReserveTableInDiningRoomInput.class);
						
			String result = "TABLE WAS BOOKED!";
			action.getMoreAttributes().put("result", result);
			return action;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}



