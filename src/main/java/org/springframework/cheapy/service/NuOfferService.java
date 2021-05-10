
package org.springframework.cheapy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.repository.NuOfferRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NuOfferService {

	private NuOfferRepository nuOfferRepository;


	@Autowired
	public NuOfferService(final NuOfferRepository nuOfferRepository) {
		this.nuOfferRepository = nuOfferRepository;
	}

	@Transactional
	public NuOffer findNuOfferById(final int id) {
		return this.nuOfferRepository.findNuOfferById(id);
	}

	@Transactional
	public List<NuOffer> findAllNuOffer(final Pageable page) {
		return this.nuOfferRepository.findAllNuOffer(page);
	}

	@Transactional
	public void saveNuOffer(final NuOffer nuOffer) throws DataAccessException {
		this.nuOfferRepository.save(nuOffer);
	}

	@Transactional
	public void saveUpdateNuOffer(final NuOffer nuOfferNew, final NuOffer nuOfferOld) throws DataAccessException {
		this.nuOfferRepository.save(nuOfferNew);
	}

	public List<NuOffer> findActiveNuOffer(final Pageable page) {
		return this.nuOfferRepository.findActiveNuOffer(StatusOffer.active, page);
	}

	public List<NuOffer> findNuOfferByUserId(final int id) {
		return this.nuOfferRepository.findByUserId(id);
	}
	
	public List<NuOffer> findNuOfferActByUserId(final int id, Pageable pag) {
		return this.nuOfferRepository.findNuOfferActByUserId(id, pag);
	}
	
	public List<NuOffer> findNuOfferActOclByUserId(final int id) {
		return this.nuOfferRepository.findNuOfferActOclByUserId(id);
	}

	public List<NuOffer> findNuOfferByClientName(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.nuOfferRepository.findNuOfferByClientName(nameEdit, p);
	}

	public List<NuOffer> findNuOfferByClientFood(final String name, final Pageable p) {
		String nameEdit = "%" + name + "%";
		return this.nuOfferRepository.findNuOfferByClientFood(nameEdit, p);
	}

	public List<NuOffer> findNuOfferByClientPlace(final Municipio mun, final Pageable p) {
		return this.nuOfferRepository.findNuOfferByClientPlace(mun, p);
	}
	
	public List<NuOffer> findNuOfferByDate(final LocalDateTime start, final Pageable p) {
		return this.nuOfferRepository.findNuOfferByDate(start, p);
	}
}
