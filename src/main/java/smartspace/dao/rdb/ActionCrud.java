package smartspace.dao.rdb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.CrudRepository;
import smartspace.data.ActionEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface ActionCrud extends PagingAndSortingRepository<ActionEntity, String> {
//CrudRepository<ActionEntity, String> {
 	
	public List<ActionEntity> findAllByActionSmartspaceLike (
			@Param("pattern") String pattern, 
			Pageable pageable);
}

