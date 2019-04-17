package smartspace.dao.rdb;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import smartspace.data.ElementEntity;

public interface ElementCrud extends
//CrudRepository<ElementEntity, String>
		PagingAndSortingRepository<ElementEntity, String> {
	
	public List<ElementEntity> findAllByNameLike(
			@Param("pattern") String pattern,
			Pageable pageable);
}
