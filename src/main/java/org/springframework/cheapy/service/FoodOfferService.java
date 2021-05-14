
package org.springframework.cheapy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.repository.FoodOfferRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FoodOfferService {

	private FoodOfferRepository foodOfferRepository;


	@Autowired
	public FoodOfferService(final FoodOfferRepository foodOfferRepository) {
		this.foodOfferRepository = foodOfferRepository;
	}

	public FoodOffer findFoodOfferById(final int id) {
		return this.foodOfferRepository.findByIdFO(id);
	}
	
	public List<FoodOffer> findAllFoodOffer(final Pageable p) {
		return this.foodOfferRepository.findAllFoodOffer(p);
	}

	public void saveFoodOffer(final FoodOffer foodOffer) throws DataAccessException {
		this.foodOfferRepository.save(foodOffer);
	}

	public List<FoodOffer> findActiveFoodOffer(final Pageable p) {
		return this.foodOfferRepository.findActiveFoodOffer(StatusOffer.active, p);
	}

	public List<FoodOffer> findFoodOfferByUserId(final int id) {
		return this.foodOfferRepository.findByUserId(id);
	}
	
	public List<FoodOffer> findFoodOfferActByUserId(final int id, Pageable pag) {
		return this.foodOfferRepository.findFoodOfferActByUserId(id, pag);
	}
	
	public List<FoodOffer> findFoodOfferActOclByUserId(final int id) {
		return this.foodOfferRepository.findFoodOfferActOclByUserId(id);
	}

	public List<FoodOffer> findFoodOfferByClientName(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.foodOfferRepository.findFoodOfferByClientName(nameEdit, p);
	}

	public List<FoodOffer> findFoodOfferByClientFood(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.foodOfferRepository.findFoodOfferByClientFood(nameEdit, p);
	}

	public List<FoodOffer> findFoodOfferByClientPlace(final Municipio municip, final Pageable p) {
		return this.foodOfferRepository.findFoodOfferByClientPlace(municip, p);
	}
	
	public List<FoodOffer> findFoodOfferByDate(final LocalDateTime start, final Pageable p) {
		return this.foodOfferRepository.findFoodOfferByDate(start, p);
	}
}
