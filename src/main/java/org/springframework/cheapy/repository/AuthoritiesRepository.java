package org.springframework.cheapy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Authorities;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.dao.DataAccessException;

public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

	
	@Query("SELECT authority FROM Authorities authority WHERE authority.username =:username")
	@Transactional(readOnly = true)
	Authorities findAuthorityByUsername(String username);
//	@Autowired
//	void save(Authorities authorities);
	
	
}
