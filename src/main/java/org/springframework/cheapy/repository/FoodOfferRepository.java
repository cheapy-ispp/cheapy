
package org.springframework.cheapy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FoodOfferRepository extends PagingAndSortingRepository<FoodOffer, Integer> {

	@Query("SELECT foodOffer FROM FoodOffer foodOffer")
	@Transactional(readOnly = true)
	List<FoodOffer> findAllFoodOffer(Pageable p);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE id =:id")
	@Transactional(readOnly = true)
	FoodOffer findByIdFO(@Param("id") Integer id);

	//void save(FoodOffer foodOffer);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.status =:status AND foodOffer.client.expiration > CURRENT_DATE()")
	@Transactional(readOnly = true)
	List<FoodOffer> findActiveFoodOffer(StatusOffer status, Pageable p);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.id =:id")
	@Transactional(readOnly = true)
	List<FoodOffer> findByUserId(@Param("id") Integer id);
	
	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.id =:id AND foodOffer.status = 'active'")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferActByUserId(@Param("id") Integer id, Pageable pag);
	
	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.id =:id AND foodOffer.status!= 'inactive'")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferActOclByUserId(@Param("id") Integer id);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.name LIKE :name AND foodOffer.status= 'active' AND foodOffer.client.expiration > CURRENT_DATE()")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferByClientName(String name, Pageable p);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.food LIKE :name AND foodOffer.status= 'active' AND foodOffer.client.expiration > CURRENT_DATE()")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferByClientFood(String name, Pageable p);

	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.client.municipio =:municipio AND foodOffer.status= 'active' AND foodOffer.client.expiration > CURRENT_DATE()")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferByClientPlace(Municipio municipio, Pageable p);
	
	@Query("SELECT foodOffer FROM FoodOffer foodOffer WHERE foodOffer.status= 'active' AND :start BETWEEN foodOffer.start AND foodOffer.end")
	@Transactional(readOnly = true)
	List<FoodOffer> findFoodOfferByDate(final LocalDateTime start, Pageable p);

}
