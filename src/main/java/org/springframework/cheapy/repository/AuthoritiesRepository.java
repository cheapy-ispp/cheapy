package org.springframework.cheapy.repository;

import org.springframework.cheapy.model.Authorities;
import org.springframework.data.repository.CrudRepository;

public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

//	@Autowired
//	void save(Authorities authorities);
	
	
}
