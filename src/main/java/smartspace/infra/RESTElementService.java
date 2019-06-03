package smartspace.infra;

import java.util.Collection;
import java.util.List;

import smartspace.data.ElementEntity;
import smartspace.data.UserRole;

public interface RESTElementService {
	
	public void updateElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail, String elementSmartspace, String elementId);
	
	public ElementEntity createNewElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail);
		
	public ElementEntity findById(String managerSmartspace, String managerEmail, String elementSmartspace, String elementId);
	
	public List<ElementEntity> findNearLocation(String userSmartspace,String userEmail,
			String search,double x,double y,double distance,int page,int size);
	
	//ADDED NOW
	
	public Collection<ElementEntity> getElementsUsingPaginationOfName(String managerSmartspace, String managerEmail, UserRole role,
			String name, int size, int page);

	public List<ElementEntity> getElementsUsingPaginationOfSpecifiedType(String managerSmartspace, String managerEmail, UserRole role,
			String type, int size, int page);
}

