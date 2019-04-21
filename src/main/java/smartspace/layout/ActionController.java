package smartspace.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import smartspace.infra.ActionService;

@RestController
public class ActionController {
	private ActionService actionService;

	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}
	
	@RequestMapping(path="/smartspace/admin/actions/{adminSmartspace}/{adminEmail}",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundry newAction (@RequestBody ActionBoundry action, 
			@PathVariable("adminSmartspace") String adminSmartspace,
			@PathVariable("adminEmail") String adminEmail) {
		
		return new ActionBoundry(
				this.actionService
					.newAction(action.convertToEntity(), adminSmartspace,adminEmail));
	}

	@RequestMapping(
			path="/smartspace/admin/actions/{adminSmartspace}/{adminEmail}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundry[] getUsingPagination (
			@RequestParam(name="size", required=false, defaultValue="10") int size,
			@RequestParam(name="page", required=false, defaultValue="0") int page,
			@PathVariable("adminSmartspace") String adminSmartspace,
			@PathVariable("adminEmail") String adminEmail) {
		return 
			this.actionService
			.getUsingPagination(size, page)
			.stream()
			.map(ActionBoundry::new)
			.collect(Collectors.toList())
			.toArray(new ActionBoundry[0]);
	}
}
