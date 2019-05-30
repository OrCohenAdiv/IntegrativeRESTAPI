package smartspace.infra;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

import smartspace.dao.EnhancedActionDao;
import smartspace.dao.EnhancedElementDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ActionEntity;
import smartspace.plugin.Plugin;


@Service
public class RESTActionServiceImpl implements RESTActionService {
	
	private EnhancedActionDao actionDao;
	private EnhancedUserDao<String> userDao;
	private EnhancedElementDao<String> elementDao;
	private String smartspaceName;
	private ApplicationContext ctx;
	
	@Autowired
	public RESTActionServiceImpl(EnhancedActionDao actionDao, ApplicationContext ctx) {
		this.actionDao = actionDao;
		this.ctx = ctx;
	}

	@Value("${smartspace.name}")
	public void setRESTActionSmartspaceName(String smartspaceName) {
		this.smartspaceName = smartspaceName;
	}
	
	@Override
	public ActionEntity invokeAction(ActionEntity newAction) {
		
		if (newAction.getActionId()  == null || 
				newAction.getActionSmartspace().trim().isEmpty() ||
				newAction.getMoreAttributes() == null) {
			throw new RuntimeException();
		}

		try {
			String funcName = newAction.getActionType();
//"ReserveTableInDiningRoom" ------>>>>>>> smartspace.plugin.ReserveTableInDiningRoomPlugin
			String className = 
					"smartspace.plugin." 
					+ funcName.toUpperCase().charAt(0) 
					+ funcName.substring(1, funcName.length())
					+ "Plugin";
			Class<?> theClass = Class.forName(className);
			Plugin plugin = (Plugin) this.ctx.getBean(theClass);
			
			newAction.setCreationTimestamp(new Date());
			newAction = plugin.process(newAction);
			
			this.actionDao.create(newAction);
			return newAction;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 
		
/*
OLD INVOKR FUNCTION 
 
	try {
		String actionType = newAction.getActionType();
		if (actionType != null && !actionType.trim().isEmpty()) {
			if (valiadate(newAction)) {
				newAction.setCreationTimestamp(new Date());
				return this.actionDao.create(newAction);
			} else {
				throw new RuntimeException("Action is invalid - Wrong element of user");
			}
		} else {
			throw new RuntimeException("Illegal action type");
		}
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
*/

	private boolean valiadate(ActionEntity entity) {
		if (entity.getPlayerEmail() != null && entity.getPlayerSmartspace() != null && entity.getElementId() != null
				&& entity.getElementSmartspace() != null && entity.getActionType() != null) {
			if (this.userDao.readById(entity.getPlayerEmail() + "=" + entity.getPlayerSmartspace()).isPresent()
					&& this.elementDao.readById(entity.getElementId() + "=" + entity.getElementSmartspace())
							.isPresent()) {
				return true;
			}
		}
		return false;
	}
	
}

	

