package smartspace.dao.rdb;

import smartspace.data.UserEntity;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserCrud extends CrudRepository<UserEntity, String> {

//	List<UserEntity> findAllByNameLike(String string, PageRequest of);

}
