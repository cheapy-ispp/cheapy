
package org.springframework.cheapy.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.ReviewClient;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.repository.ReviewClientRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewClientService {

	private ReviewClientRepository repo;


	@Autowired
	public ReviewClientService(final ReviewClientRepository repo) {
		super();
		this.repo = repo;
	}
	@Transactional
	public void saveReview(final ReviewClient entity) {
		this.repo.save(entity);
	}
	@Transactional
	public List<ReviewClient> findByClient(final String idClient) {
		return this.repo.findByBar(idClient);
	}
	
	@Transactional
	public void deleteReviewClient(ReviewClient rc) {
		this.repo.delete(rc);
	}
	
	@Transactional
	public void deleteReviewsByUser(final User user) {
		List<ReviewClient> reviews = this.repo.findByEscritor(user);
		if (reviews.size() > 0) {
			this.repo.deleteAll(reviews);
		}
	}

	public ReviewClient findReviewById(final int reviewId) {
		return this.repo.findReviewClientById(reviewId);
	}
	public List<ReviewClient> findAllReviewsByBar(final Pageable p, final Client client) {

		return this.repo.findAllReviewClientByBar(p, client);
	}
}
