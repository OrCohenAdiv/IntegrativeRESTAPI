package smartspace.infra;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ElementEntity;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;

@Service
public class ElementServiceImpl implements ElementService {
	private EnhancedElementDao<String> elementDao;
	private EnhancedUserDao<String> userDao;

	@Autowired
	public ElementServiceImpl(EnhancedElementDao<String> elementDao, EnhancedUserDao<String> userDao) {
		this.elementDao = elementDao;
		this.userDao = userDao;
	}

	@Override
	public ElementEntity newElement(
			ElementEntity entity, String adminSmartspace, String adminEmail) {

		String key = adminEmail + "=" + adminSmartspace;
		UserEntity userEntity = userDao.readById(key)
				.orElseThrow(() -> new RuntimeException("user don't exist"));
		
		if(!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("you are not ADMIN");
		}
		
		if (valiadate(entity)) {
			entity.setCreationTimestamp(new Date());
			return this.elementDao.create(entity);
		} else {
			throw new RuntimeException("invalid element");
		}
	}

	private boolean valiadate(ElementEntity entity) {
		return entity.getCreatorEmail() != null &&
				!entity.getCreatorEmail().trim().isEmpty() &&
				!entity.getCreatorSmartspace().trim().isEmpty() &&
				entity.getCreatorSmartspace() != null &&
				entity.getName() != null &&
				!entity.getName().trim().isEmpty() &&
				!entity.getType().trim().isEmpty() &&
				entity.getType() != null;
	}

	@Override
	public List<ElementEntity> getUsingPagination(
			int size, int page, String adminSmartspace, String adminEmail) {
		
		String key = adminEmail + "=" + adminSmartspace;
		UserEntity userEntity = userDao.readById(key)
				.orElseThrow(() -> new RuntimeException("user don't exist"));
		
		if(!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("you are not ADMIN");
		}
		
		return this.elementDao.readAll("key", size, page);
	}

}
