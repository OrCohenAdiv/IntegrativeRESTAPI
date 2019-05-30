package smartspace.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import smartspace.infra.RESTActionServiceImpl;

@RestController
public class RESTActionController {

	private RESTActionServiceImpl restActionServiceImpl;

	@Autowired
	public RESTActionController(RESTActionServiceImpl restActionServiceImpl) {
		this.restActionServiceImpl = restActionServiceImpl;
	}

	@RequestMapping(path = "/smartspace/actions",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundary invokeAction(
			@RequestBody ActionBoundary action) {
		return new ActionBoundary(
				restActionServiceImpl.invokeAction(action.convertToEntity()));
		// (action.getPlayer().getSmartspace(),action.getPlayer().getEmail(),
		// null,action.convertToEntity());
	}
	

	
	/*
	FUNCTIONS TO DO !!!!! 
	
	Room:
		CheckIn
		CheckOut
		Room service reservation
		Room cleaning reservation
		Cost (Total) = price
	
	Dining room:
		Reserve a table
	*/
	
	@RequestMapping(
			path="/smartspace/ReserveTableInDiningRoom",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundary ReserveTableInDiningRoom (
			@RequestBody ActionBoundary reserveTable) {
		return new ActionBoundary(
				this.restActionServiceImpl
				.invokeAction(reserveTable.convertToEntity()));
	}
	
//	@ExceptionHandler
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ErrorMessage handleException (IllegalNameException e){
//		String message = e.getMessage();
//		if (message == null) {
//			message = "The name you have provided is invalid";
//		}
//		return new ErrorMessage(message);
//	}
}


