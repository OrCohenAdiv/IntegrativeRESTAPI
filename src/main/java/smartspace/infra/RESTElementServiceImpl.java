package smartspace.infra;

import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ElementEntity;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

public class RESTElementServiceImpl implements RESTElementService {

	private EnhancedElementDao<String> elementDao;
	private EnhancedUserDao<String> userDao;
	private String smartspaceName;

	@Override
	public void updateElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail, String elementSmartspace, String elementId) {

		String key = managerSmartspace + "=" + managerEmail;
		UserEntity userEntity = userDao.readById(key).orElseThrow(() -> new RuntimeException("user doesn't exist"));

		if (!userEntity.getRole().equals(UserRole.MANAGER)) {
			throw new RuntimeException("you are not MANAGER");
		}

		elementDao.update(elementEntity);
	}

}
