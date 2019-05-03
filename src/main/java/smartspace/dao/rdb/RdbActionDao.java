package smartspace.dao.rdb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import smartspace.dao.EnhancedActionDao;
import smartspace.data.ActionEntity;
import org.springframework.data.domain.Sort.Direction;

@Repository
public class RdbActionDao implements EnhancedActionDao {

	private ActionCrud actionCrud;
	private GenericIdGeneratorCrud genericIdGeneratorCrud;

	@Autowired	
	public RdbActionDao(ActionCrud actionCrud, GenericIdGeneratorCrud genericIdGeneratorCrud) {
		super();
		this.actionCrud = actionCrud;
		this.genericIdGeneratorCrud = genericIdGeneratorCrud;
	}

	@Override
	@Transactional
	public ActionEntity create(ActionEntity actionEntity) {
		GenericIdGenerator nextID = this.genericIdGeneratorCrud.save(new GenericIdGenerator());
		actionEntity.setKey(nextID.getId() + "=" + actionEntity.getActionSmartspace());
		this.genericIdGeneratorCrud.delete(nextID);
		if (!this.actionCrud.existsById(actionEntity.getKey())) {
			ActionEntity rv = this.actionCrud.save(actionEntity);
			return rv;
		} else {
			throw new RuntimeException("Action Entity already exist with key:" + actionEntity.getKey());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActionEntity> readAll() {
		// SQL: SELECT
		List<ActionEntity> rv = new ArrayList<>();
		this.actionCrud.findAll().forEach(rv::add);
		return rv;
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.actionCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ActionEntity> readAll(int size, int page) {
		return this.actionCrud.findAll(PageRequest.of(page, size)).getContent(); 
	}

	@Override
	public List<ActionEntity> readAll(String sortBy, int size, int page) {
		return this.actionCrud.findAll(PageRequest.of(
				page, size, Direction.ASC, sortBy)).getContent();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ActionEntity> readMessageWithSmartspaceContaining(
			String smartspace, int size, int page) {
		return this.actionCrud.findAllByNameLike(
				"%" + smartspace + "%",
				PageRequest.of(page, size));
	}
}

