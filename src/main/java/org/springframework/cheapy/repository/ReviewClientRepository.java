
package org.springframework.cheapy.repository;

import java.util.List;

import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.ReviewClient;
import org.springframework.cheapy.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewClientRepository extends PagingAndSortingRepository<ReviewClient, Integer> {

	List<ReviewClient> findByBar(String bar);

	@Query("SELECT r FROM ReviewClient r WHERE r.bar =:client ORDER BY r.media DESC")
	@Transactional(readOnly = true)
	List<ReviewClient> findAllReviewClientByBar(Pageable p,@Param("client") Client client);

	List<ReviewClient> findAllReviewClientByBar(Client client);

	ReviewClient findReviewClientById(int id);

	List<ReviewClient> findByEscritor(User user);

}
