
package org.springframework.cheapy.repository;

import org.springframework.cheapy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends Repository<User, Integer> {

	@Query("SELECT u FROM User u WHERE username =:username")
	@Transactional(readOnly = true)
	User findByUsername(String username);
	
	@Query("SELECT (count(*) > 0) FROM User u WHERE username =:username")
	@Transactional(readOnly = true)
	Boolean duplicateUsername(String username);

	
	void save(User user);
	
}
