package smartspace.infra;

import java.util.List;

import smartspace.data.ActionEntity;

public interface ActionService {

	public ActionEntity newAction(
			ActionEntity entity, 
			String adminSmartspace, 
			String adminEmail);

	public List<ActionEntity> getUsingPagination(
			int size,
			int page, 
			String adminSmartspace, 
			String adminEmail);
	
}
