package smartspace.layout;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import smartspace.data.ElementEntity;
import smartspace.infra.ElementService;
import smartspace.infra.RESTElementService;

public class RESTElementController {
	
	private RESTElementService restElementService; // RESTElementService

	@Autowired
	public RESTElementController(RESTElementService resrElementService) {
		this.restElementService = restElementService;
	}

//	@RequestMapping(path = "/smartspace/admin/elements/{adminSmartspace}/{adminEmail}",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE, 
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ElementBoundary[] newElement(@RequestBody ElementBoundary[] elements,
//			@PathVariable("adminSmartspace") String adminSmartspace,
//			@PathVariable("adminEmail") String adminEmail) {
//		ElementEntity [] elementArr = Stream.of(elements)
//				.map(ElementBoundary::convertToEntity)
//				.collect(Collectors.toList())
//				.toArray(new ElementEntity[0]);
//
//		return this.elementService.newElement(elements, adminSmartspace, adminEmail)
//				.stream()
//				.map(ElementBoundary::new)
//				.collect(Collectors.toList())
//						.toArray(new ElementBoundary[0]);
//	}

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
	
	@RequestMapping(path = "/smartspace/element/login/{managerSmartspace}/{managerEmail}/{elementSmartspace}/{elementId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void Update(@RequestBody ElementBoundary elementBoundary,
			@PathVariable("managerSmartspace") String managerSmartspace, @PathVariable("managerEmail") String managerEmail,@PathVariable("elementSmartspace") String elementSmartspace, @PathVariable("elementId") String elementId) {
		restElementService.updateElement(elementBoundary.convertToEntity(), managerSmartspace, managerEmail, elementSmartspace, elementId);

	}

}
