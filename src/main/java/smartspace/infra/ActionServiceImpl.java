package smartspace.infra;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import smartspace.dao.EnhancedActionDao;
import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ActionEntity;
import smartspace.data.ElementEntity;
import smartspace.data.UserRole;
import smartspace.layout.ActionBoundary;

@Service
public class ActionServiceImpl implements ActionService {
	@Value("${smartspace.name}")
	private String smartspaceName;
	private EnhancedActionDao actionDao;
	private EnhancedUserDao<String> userDao;
	private EnhancedElementDao<String> elementDao;

	@Autowired
	public ActionServiceImpl(EnhancedActionDao actionDao, EnhancedUserDao<String> userDao,
			EnhancedElementDao<String> elementDao) {
		this.actionDao = actionDao;
		this.userDao = userDao;
		this.elementDao = elementDao;
	}

	@Transactional
	@Override
	public List<ActionEntity> newAction(ActionBoundary[] allBoundaries, String adminSmartspace, String adminEmail) {
		List<ActionEntity> actionEntites = new LinkedList<ActionEntity>();

//		if (!(userDao.readById(adminEmail + "=" + adminSmartspace)
//				.orElseThrow(() -> new RuntimeException("user doesn't exist"))).getRole().equals(UserRole.ADMIN))
//			throw new RuntimeException("You are not an ADMIN!");

//		elementDao.readById(entity.getElementId() + "=" + entity.getElementSmartspace())
//				.orElseThrow(() -> new RuntimeException("Element doesn't exist"));

		for (ActionBoundary actionBoundary : allBoundaries) {
			ActionEntity actionEntity = actionBoundary.convertToEntity();
			if (valiadate(actionEntity)) {
				actionEntity.setCreationTimestamp(new Date());

				if (actionEntity.getActionSmartspace().equals(smartspaceName))
					throw new RuntimeException("Illigal Import!");
				else {
					actionEntites.add(this.actionDao.importAction(actionEntity));
				}
			} else
				throw new RuntimeException("invalid action");
		}
		return actionEntites;
	}

	private boolean valiadate(ActionEntity entity) {
		return entity.getPlayerEmail() != null && !entity.getPlayerEmail().trim().isEmpty()
				&& entity.getPlayerSmartspace() != null && !entity.getPlayerSmartspace().trim().isEmpty()
				&& entity.getActionSmartspace() != null && !entity.getActionSmartspace().trim().isEmpty()
				&& entity.getActionId() != null && !entity.getActionId().trim().isEmpty()
				&& entity.getElementSmartspace() != null && !entity.getElementSmartspace().trim().isEmpty()
				&& entity.getElementId() != null && !entity.getElementId().trim().isEmpty()
				&& entity.getActionType() != null && !entity.getActionType().trim().isEmpty();
	}

	@Override
	public List<ActionEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail) {
		if (!(userDao.readById(adminEmail + "=" + adminSmartspace)
				.orElseThrow(() -> new RuntimeException("user doesn't exist")).getRole().equals(UserRole.ADMIN)))
			throw new RuntimeException("You are not an ADMIN!");

		return this.actionDao.readAll("key", size, page);
	}
}
