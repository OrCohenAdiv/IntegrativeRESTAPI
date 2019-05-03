package smartspace.dao.rdb;

import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.CrudRepository;
import smartspace.data.ActionEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ActionCrud extends 
//CrudRepository<ActionEntity, String> {
	PagingAndSortingRepository<ActionEntity, String> {
	
	public List<ActionEntity> findAllByNameLike(
			@Param("pattern") String pattern, 
			Pageable pageable);

	public List<ActionEntity> findAllByAvailableFromBetween(
			@Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate, 
			Pageable pageable);

}
