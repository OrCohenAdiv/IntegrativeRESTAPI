package smartspace.infra;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ElementEntity;
import smartspace.data.Location;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

@Service
public class RESTElementServiceImpl implements RESTElementService {

	private EnhancedElementDao<String> elementDao;
	private EnhancedUserDao<String> userDao;
	private String smartspaceName;
	
	@Value("${smartspace.name}")
	public void setRESTElementSmartspaceName(String smartspaceName) {
		this.smartspaceName = smartspaceName;
	}
	
	@Autowired
	public void setElementDao(EnhancedElementDao<String> elementDao) {
		this.elementDao = elementDao;
	}

	@Override
	public void updateElement(ElementEntity elementEntity, String managerSmartspace, String managerEmail,
			String elementSmartspace, String elementId) {

		String key = managerSmartspace + "=" + managerEmail;
		UserEntity userEntity = userDao.readById(key).orElseThrow(() -> new RuntimeException("user doesn't exist"));

		if (!userEntity.getRole().equals(UserRole.MANAGER)) {
			throw new RuntimeException("you are not MANAGER");
		}

		elementDao.update(elementEntity);
	}

	@Override
	public ElementEntity createNewElement(ElementEntity elementEntity, String managerSmartspace, String managerEmail) {

		String key = managerSmartspace + "=" + managerEmail;
		ElementEntity newElementEntity = elementDao.readById(key)
				.orElseThrow(() -> new RuntimeException("element doesn't exist"));


		return elementDao.create(newElementEntity);

	}



	@Override
	public ElementEntity findById(String elementSmartspace, String elementId) {
		return this.elementDao
				.readById(elementSmartspace + "=" + elementId)
				.orElseThrow(() -> new RuntimeException("Element not found!"));
	}
	
	@Override
	public List<ElementEntity> findNearLocation(String userSmartspace,String userEmail,
			String search,double x,double y,double distance,int page,int size){
			return this.elementDao.readAllByDistanceFromLocation
				(new Location(x, y), distance, size, page);
		
	}
	
	//ADDED NOW
	
	@Override
	public Collection<ElementEntity> getElementsUsingPaginationOfName(String managerSmartspace, String managerEmail, UserRole role,
			String name, int size, int page) {

		if (role == UserRole.MANAGER) {
			return this.elementDao.readAllUsingName(name, size, page);
		} else if (role == UserRole.PLAYER) {
			return this.elementDao.readAllUsingNameNotExpired(name, size, page);
		} else {
			throw new RuntimeException(
					"The URl isn't match for manager or player. use another user or URL that match admin user");
		}
	}

	@Override
	public List<ElementEntity> getElementsUsingPaginationOfSpecifiedType(String managerSmartspace, String managerEmail, UserRole role,
			String type, int size, int page) {

		if (role == UserRole.MANAGER) {
			return this.elementDao.readAllUsingType(type, size, page);
		} else if (role == UserRole.PLAYER) {
			return this.elementDao.readAllUsingTypeNotExpired(type, size, page);
		} else {
			throw new RuntimeException(
					"The URl isn't match for manager or player. use another user or URL that match admin user");
		}
	}


}
