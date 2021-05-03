package org.springframework.cheapy.repository;

import org.springframework.cheapy.model.Authorities;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

	
	@Query("SELECT authority FROM Authorities authority WHERE authority.username =:username")
	@Transactional(readOnly = true)
	Authorities findAuthorityByUsername(String username);
	
}
