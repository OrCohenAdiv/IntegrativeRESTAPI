package smartspace.dao.rdb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import smartspace.dao.ActionDao;
import smartspace.data.ActionEntity;

@Repository
public class RdbActionDao implements ActionDao {

	private ActionCrud actionCrud;
	private GenericIdGeneratorCrud genericIdGeneratorCrud;

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
	public void deleteAll() {
		this.actionCrud.deleteAll();

	}
}
