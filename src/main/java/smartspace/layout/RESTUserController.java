package smartspace.layout;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import smartspace.infra.RESTUserService;


@RestController
public class RESTUserController {
	
	private RESTUserService restUserService;

	@Autowired
	public RESTUserController(RESTUserService restUserService) {
		this.restUserService = restUserService;
	}

	@RequestMapping(path = "/smartspace/users/login/{userSmartspace}/{userEmail}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void Update(@RequestBody UserBoundary user,
			@PathVariable("userSmartspace") String userSmartspace, @PathVariable("userEmail") String userEmail) {
		restUserService.updateUser(user.convertToEntity(), userSmartspace, userEmail);

	}

//	@RequestMapping(path = "/smartspace/admin/users/{adminSmartspace}/{adminEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public UserBoundary[] getUsingPagination(
//			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
//			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
//			@PathVariable("adminSmartspace") String adminSmartspace, @PathVariable("adminEmail") String adminEmail)
//
//	{
//		return this.userService.getUsingPagination(size, page, adminSmartspace, adminEmail).stream()
//				.map(UserBoundary::new).collect(Collectors.toList()).toArray(new UserBoundary[0]);
//	}
	
	@RequestMapping(
			path = "/smartspace/users/login/{userSmartspace}/{userEmail}",
			method = RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public UserBoundary loginUser(UserBoundary user,
			@PathVariable("userSmartspace") String userSmartspace,
			@PathVariable("userEmail") String userEmail) {
		return new UserBoundary(
				this.restUserService.loginUser(
						user.convertToEntity(), userSmartspace, userEmail));
		
	}

}


