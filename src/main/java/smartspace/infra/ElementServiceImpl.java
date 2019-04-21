package smartspace.infra;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ElementEntity;

@Service
public class ElementServiceImpl implements ElementService {
	private EnhancedElementDao<String> elemntDao;

	@Autowired
	public ElementServiceImpl(EnhancedElementDao<String> elementDao) {
		this.elemntDao = elementDao;
	}

	@Override
	public ElementEntity newElement(ElementEntity entity,
			String adminSmartspace, String adminEmail) {
		// TODO: check if user is admin

		if (valiadate(entity)) {
			entity.setCreationTimestamp(new Date());
			return this.elemntDao.create(entity);
		} else {
			throw new RuntimeException("invalid message");
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
	public List<ElementEntity> getUsingPagination(int size, int page) {
		return this.elemntDao.readAll("key", size, page);
	}

}
