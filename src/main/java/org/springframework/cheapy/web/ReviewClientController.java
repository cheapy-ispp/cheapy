package org.springframework.cheapy.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.ReviewClient;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.ReviewClientService;
import org.springframework.cheapy.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReviewClientController {

	private static final String	VIEWS_REVIEWS_CREATE_OR_UPDATE_FORM	= "reviewsClient/createOrUpdateReviewForm";
	private final ReviewClientService reviewService;
	private final UserService	userService;
	private final ClientService clientService;
	
	public ReviewClientController(ReviewClientService reviewService, UserService userService, ClientService clientService) {
		super();
		this.clientService = clientService;
		this.reviewService = reviewService;
		this.userService = userService;
	}
	
	private boolean checkIdentity(final int reviewId) {
		boolean res = false;
		User user = this.userService.getCurrentUser();
		ReviewClient review = this.reviewService.findReviewById(reviewId);
		User reviewsAuthor = review.getEscritor();
		if (user.equals(reviewsAuthor)) {
			res = true;
		}
		return res;
	}
	
	private boolean checkClient(final String client) {
		User user = this.userService.getCurrentUser();
		Client bar = this.clientService.findByUsername(client);
		return (bar == null||user==null)? false: true;
	}
	@GetMapping("/reviewsClient/new/{idClient}")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("idClient") final String idClient) {
		if(!checkClient(idClient)) {
			return "error";
		}
		
		ReviewClient reviewClient = new ReviewClient();

		model.put("reviewClient", reviewClient);
		return ReviewClientController.VIEWS_REVIEWS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/reviewsClient/new/{idClient}")
	public String processCreationForm(@PathVariable("idClient") final String idClient ,@Valid final ReviewClient reviewClient ,final BindingResult result) {
		if (result.hasErrors()) {
			return ReviewClientController.VIEWS_REVIEWS_CREATE_OR_UPDATE_FORM;
		} else {
			User escritor = this.userService.getCurrentUser();
			reviewClient.setEscritor(escritor);
			Client bar = this.clientService.findByUsername(idClient);
			reviewClient.setBar(bar);
		
			this.reviewService.saveReview(reviewClient);
			return "redirect:/reviewsClient/" + reviewClient.getId();
		}
	}
	
	@GetMapping("/reviewsClient/{reviewId}")
	public String processShowForm(@PathVariable("reviewId") final int reviewId, final Map<String, Object> model) {
		ReviewClient review = this.reviewService.findReviewById(reviewId);

		model.put("review", review);

		return "reviewsClient/reviewsShow";

	}
	@GetMapping("/reviewsClientList/{idClient}/{page}")
	public String processFindForm(@PathVariable("page") final int page, @PathVariable("idClient") final String idClient, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 6);
		Pageable nextPage = PageRequest.of(page+1, 6);
		Client client = this.clientService.findByUsername(idClient);

		List<ReviewClient> reviewsLs = this.reviewService.findAllReviewsByBar(elements,client);
		Integer next = this.reviewService.findAllReviewsByBar(nextPage,client).size();
		
		List<Object[]> datos = new ArrayList<Object[]>();
        for(ReviewClient re: reviewsLs) {
            Object[] r = {re, re.getMedia()};
            datos.add(r);
        }
		model.put("datos", datos);
		model.put("nextPage", next);
		model.put("client", idClient);
		model.put("restaurant", client.getName());

		return "reviewsClient/reviewsList";

	}
	
	@GetMapping("/myClientReviews")
	public String processFindMyReviewsForm(final Map<String, Object> model) {
		
		Pageable elements = PageRequest.of(0, 6);
		Pageable nextPage = PageRequest.of(0+1, 6);
		Client client = this.clientService.getCurrentClient();

		List<ReviewClient> reviewsLs = this.reviewService.findAllReviewsByBar(elements,client);
		Integer next = this.reviewService.findAllReviewsByBar(nextPage,client).size();
		model.put("page", 0);
		model.put("nextPage", next);
		model.put("reviewsLs", reviewsLs);
		model.put("client", client.getUsuar().getUsername());
		model.put("restaurant", client.getName());

		return processFindForm(0, client.getUsuar().getUsername(), model);

	}
	
	@GetMapping("/reviewsClient/{reviewId}/edit")
	public String updateReviewInit(@PathVariable("reviewId") final int reviewId, final ModelMap model) {
		if (!this.checkIdentity(reviewId)) {
			return "error";
		}
		ReviewClient reviewClient = this.reviewService.findReviewById(reviewId);
		
		model.addAttribute("reviewClient", reviewClient);
		return ReviewClientController.VIEWS_REVIEWS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/reviewsClient/{reviewId}/edit")
	public String updateReviewPost(@Valid final ReviewClient reviewEdit, final BindingResult result, final ModelMap model) {
		if (!this.checkIdentity(reviewEdit.getId())) {
			return "error";
		}
		if (result.hasErrors()) {
			model.addAttribute("reviewClient", reviewEdit);
			return ReviewClientController.VIEWS_REVIEWS_CREATE_OR_UPDATE_FORM;

		} else {
			Client bar = this.reviewService.findReviewById(reviewEdit.getId()).getBar();
			User escritor = this.userService.getCurrentUser();
			reviewEdit.setEscritor(escritor);
			reviewEdit.setBar(bar);

			this.reviewService.saveReview(reviewEdit);
			return "redirect:/reviewsClient/" + reviewEdit.getId();
		}

	}
	
	
}
