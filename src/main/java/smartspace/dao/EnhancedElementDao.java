package smartspace.dao;

import java.util.List;

import smartspace.data.ElementEntity;
import smartspace.data.Location;

public interface EnhancedElementDao<Key> extends ElementDao<Key> {
	public List<ElementEntity> readAll(int size, int page);
	public List<ElementEntity> readAll(String sortBy, int size, int page);
	public List<ElementEntity> readElementWithSmartspaceContaining (String text, int size, int page);
	public ElementEntity importElement(ElementEntity elementEntity);
	
	public List<ElementEntity> readAllByDistanceFromLocation(Location l,double distance, int size, int page);
}
