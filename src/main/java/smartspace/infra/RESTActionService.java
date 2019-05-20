package smartspace.infra;

import smartspace.data.ActionEntity;

public interface RESTActionService {

	ActionEntity invokeAction(ActionEntity newAction);


}
