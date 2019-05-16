package smartspace.infra;

import smartspace.data.ElementEntity;

public interface RESTElementService {
	
	public void updateElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail, String elementSmartspace, String elementId);

}
