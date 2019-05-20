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

import smartspace.infra.RESTElementService;
import smartspace.layout.ElementBoundary;

@RestController
public class RESTElementController {
	
	private RESTElementService restElementService; 

	@Autowired
	public RESTElementController(RESTElementService restElementService) {
		this.restElementService = restElementService;
	}

	@RequestMapping(path = "/smartspace/elements/{managerSmartspace}/{managerEmail}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary newElement(@RequestBody ElementBoundary element,
			@PathVariable("managerSmartspace") String managerSmartspace,
			@PathVariable("managerEmail") String managerEmail) {
		
		return new ElementBoundary(this.restElementService.createNewElement(element.convertToEntity(), managerSmartspace, managerEmail));
		
		//return this.elementService.newElement(element, adminSmartspace, adminEmail)
				
	}
	
	@RequestMapping(path = "/smartspace/elements/{userSmartspace}/{userEmail}",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] getSpecifiedNameElement(
			@RequestParam(name = "search", required = false, defaultValue = "name") String search,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@PathVariable("userSmartspace") String userSmartspace,
			@PathVariable("userEmail") String userEmail) {
		
		return this.restElementService.getUsingPagination(size, page, userSmartspace, userEmail).stream()
				.map(ElementBoundary::new)
				.collect(Collectors.toList())
				.toArray(new ElementBoundary[0]);
		
	}
	
	
	@RequestMapping(path = "/smartspace/elements/{userSmartspace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] getSpecifiedTypeElement(
			@RequestParam(name = "search", required = false, defaultValue = "type") String search,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@PathVariable("userSmartspace") String userSmartspace,
			@PathVariable("userEmail") String userEmail) {
		
		return this.restElementService.getUsingPagination(size, page, userSmartspace, userEmail).stream()
				.map(ElementBoundary::new)
				.collect(Collectors.toList())
				.toArray(new ElementBoundary[0]);
		
	}

//	@RequestMapping(path = "/smartspace/admin/elements/{adminSmartspace}/{adminEmail}",
//			method = RequestMethod.GET,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ElementBoundary[] getUsingPagination(
//			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
//			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
//			@PathVariable("adminSmartspace") String adminSmartspace, @PathVariable("adminEmail") String adminEmail) {
//		return this.elementService.getUsingPagination(size, page, adminSmartspace, adminEmail)
//				.stream()
//				.map(ElementBoundary::new)
//				.collect(Collectors.toList())
//				.toArray(new ElementBoundary[0]);
//	}
	
	@RequestMapping(path = "/smartspace/element/login/{managerSmartspace}/{managerEmail}/{elementSmartspace}/{elementId}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public void Update(@RequestBody ElementBoundary elementBoundary,
			@PathVariable("managerSmartspace") String managerSmartspace, 
			@PathVariable("managerEmail") String managerEmail,
			@PathVariable("elementSmartspace") String elementSmartspace, 
			@PathVariable("elementId") String elementId) {
		restElementService.updateElement(elementBoundary.convertToEntity(), managerSmartspace, managerEmail, elementSmartspace, elementId);

	}

	@RequestMapping(path = "/smartspace/elements/{userSmartspace}/{userEmail}/{elementSmartspace}/{elementId}",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary retrieveSpecificElement(
			@PathVariable("userSmartspace") String userSmartspace, 
			@PathVariable("userEmail") String userEmail,
			@PathVariable("elementSmartspace") String elementSmartspace, 
			@PathVariable("elementId") String elementId) {
		
		return new ElementBoundary(this.restElementService.findById(elementSmartspace, elementId));
			
	}
	
	@RequestMapping(path = "/smartspace/elements/{userSmartspace}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ElementBoundary[] retrieveAllElgetementsByNearByLocation(
			@PathVariable("userSmartspace") String userSmartspace, 
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name = "search", required = false, defaultValue = "location") String search,
			@RequestParam(name = "distance", required = false, defaultValue = "1") double distance,
			@RequestParam(name = "x", required = false, defaultValue = "0") double x,
			@RequestParam(name = "y", required = false, defaultValue = "0") double y,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page			) {
		
			return this.restElementService.findNearLocation
					(userSmartspace, userEmail, search, x, y, distance, page, size)
					.stream()
					.map(ElementBoundary::new)
					.collect(Collectors.toList())
					.toArray(new ElementBoundary[0]);
	}
		
}
