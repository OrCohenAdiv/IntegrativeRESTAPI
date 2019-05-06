package smartspace.infra;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;
import smartspace.data.UserEntity;
import smartspace.data.UserRole;
import smartspace.layout.ElementBoundary;

@Service
public class ElementServiceImpl implements ElementService {
	@Value("${smartspace.name}")
	private String smartspaceName;
	private EnhancedElementDao<String> elementDao;
	private EnhancedUserDao<String> userDao;

	@Autowired
	public ElementServiceImpl(EnhancedElementDao<String> elementDao, EnhancedUserDao<String> userDao) {
		this.elementDao = elementDao;
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public List<ElementEntity> newElement(ElementBoundary[] allBoundaries, String adminSmartspace, String adminEmail) {
		List<ElementEntity> elementEntites = new LinkedList<>();
		String key = adminSmartspace + "=" + adminEmail;
		UserEntity userEntity = userDao.readById(key).orElseThrow(() -> new RuntimeException("user don't exist"));

		if (!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("you are not ADMIN");
		}
		for (ElementBoundary elementBoundary : allBoundaries) {
			ElementEntity elementEntity = elementBoundary.convertToEntity();

			if (valiadate(elementEntity)) {
				elementEntity.setCreationTimestamp(new Date());
				if (elementEntity.getElementSmartspace().equals(smartspaceName))
					throw new RuntimeException("Illigal Import!");
				else {
					elementEntites.add(this.elementDao.importElement(elementEntity));
				}
			} else {
				throw new RuntimeException("invalid element");
			}
		}
		return elementEntites;
	}

	private boolean valiadate(ElementEntity entity) {
		return entity.getCreatorEmail() != null && !entity.getCreatorEmail().trim().isEmpty()
				&& !entity.getCreatorSmartspace().trim().isEmpty() && entity.getCreatorSmartspace() != null
				&& entity.getName() != null && !entity.getName().trim().isEmpty() && !entity.getType().trim().isEmpty()
				&& entity.getType() != null;
	}

	@Override
	public List<ElementEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail) {

		String key = adminSmartspace + "=" + adminEmail;
		UserEntity userEntity = userDao.readById(key).orElseThrow(() -> new RuntimeException("user don't exist"));

		if (!userEntity.getRole().equals(UserRole.ADMIN)) {
			throw new RuntimeException("you are not ADMIN");
		}

		return this.elementDao.readAll("key", size, page);
	}

}
