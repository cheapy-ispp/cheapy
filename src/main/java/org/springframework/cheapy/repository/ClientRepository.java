
package org.springframework.cheapy.repository;

import java.util.List;

import org.springframework.cheapy.model.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {

	@Query("SELECT client FROM Client client WHERE username =:username")
	@Transactional(readOnly = true)
	Client findByUsername(String username);

	@Query("SELECT client FROM Client client")
	@Transactional(readOnly = true)
	List<Client> findAllClient(Pageable page);
	
	@Query("SELECT client FROM Client client where client.usuar is not null")
	@Transactional(readOnly = true)
	List<Client> findAllNonDeletedClients(Pageable page);
}
