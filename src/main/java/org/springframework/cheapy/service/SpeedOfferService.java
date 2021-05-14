
package org.springframework.cheapy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.repository.SpeedOfferRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpeedOfferService {

	private SpeedOfferRepository speedOfferRepository;


	@Autowired
	public SpeedOfferService(final SpeedOfferRepository speedOfferRepository) {
		this.speedOfferRepository = speedOfferRepository;
	}

	@Transactional
	public SpeedOffer findSpeedOfferById(final int id) {
		return this.speedOfferRepository.findByIdSO(id);
	}

	@Transactional
	public List<SpeedOffer> findAllSpeedOffer(final Pageable page) { //
		return this.speedOfferRepository.findAllSpeedOffer(page);
	}

	@Transactional
	public void saveSpeedOffer(final SpeedOffer speedOffer) throws DataAccessException { //
		this.speedOfferRepository.save(speedOffer);
	}

	public List<SpeedOffer> findActiveSpeedOffer(final Pageable page) {
		return this.speedOfferRepository.findActiveSpeedOffer(StatusOffer.active, page);
	}

	public List<SpeedOffer> findSpeedOfferByUserId(final int id) {
		return this.speedOfferRepository.findByUserId(id);
	}
	
	public List<SpeedOffer> findSpeedOfferActByUserId(final int id, Pageable pag) {
		return this.speedOfferRepository.findSpeedOfferActByUserId(id, pag);
	}
	
	public List<SpeedOffer> findSpeedOfferActOclByUserId(final int id) {
		return this.speedOfferRepository.findSpeedOfferActOclByUserId(id);
	}

	public List<SpeedOffer> findSpeedOfferByClientName(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.speedOfferRepository.findSpeedOfferByClientName(nameEdit, p);
	}

	public List<SpeedOffer> findSpeedOfferByClientFood(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.speedOfferRepository.findSpeedOfferByClientFood(nameEdit, p);
	}

	public List<SpeedOffer> findSpeedOfferByClientPlace(final Municipio mun, final Pageable p) {
		return this.speedOfferRepository.findSpeedOfferByClientPlace(mun, p);
	}
	
	public List<SpeedOffer> findSpeedOfferByDate(final LocalDateTime start, final Pageable p) {
		return this.speedOfferRepository.findSpeedOfferByDate(start, p);
	}
}
