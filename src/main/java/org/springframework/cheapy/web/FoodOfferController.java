
package org.springframework.cheapy.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FoodOfferController {

	private static final String		VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM	= "offers/food/createOrUpdateFoodOfferForm";

	private final FoodOfferService	foodOfferService;
	private final ClientService		clientService;


	public FoodOfferController(final FoodOfferService foodOfferService, final ClientService clientService) {
		this.foodOfferService = foodOfferService;
		this.clientService = clientService;
	}

	private boolean checkIdentity(final int foodOfferId) {
		boolean res = false;
		Client client = this.clientService.getCurrentClient();
		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		Client clientOffer = foodOffer.getClient();
		if (client.equals(clientOffer)) {
			res = true;
		}
		return res;
	}

	private boolean checkOffer(final FoodOffer session, final FoodOffer offer) {
		boolean res = false;
		if (session.getId() == offer.getId() && session.getStatus().equals(offer.getStatus()) && (session.getCode() == null ? offer.getCode().equals("") : session.getCode().equals(offer.getCode())) && !session.getStatus().equals(StatusOffer.inactive)) {
			res = true;
		}
		return res;
	}

	private boolean checkDates(final FoodOffer foodOffer) {
		boolean res = false;
		if (foodOffer.getEnd() == null || foodOffer.getStart() == null || foodOffer.getEnd().isAfter(foodOffer.getStart())) {
			res = true;
		}
		return res;
	}

	@GetMapping("/offers/foodOfferList/{page}")
	public String processFindForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 5);
		Pageable nextPage = PageRequest.of(page + 1, 5);

		List<FoodOffer> foodOfferLs = this.foodOfferService.findActiveFoodOffer(elements);
		for (int i = 0; i < foodOfferLs.size(); i++) {
			FoodOffer fo = foodOfferLs.get(i);
			String aux = fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux + fo.getClient().getName().substring(1));

			foodOfferLs.set(i, fo);
		}
		Integer next = this.foodOfferService.findActiveFoodOffer(nextPage).size();

		Municipio[]municipios=Municipio.values();
		Arrays.sort(municipios);
		
		model.put("municipios", municipios);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nextPage", next);
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return "offers/food/foodOffersList";

	}

	@GetMapping("/offers/food/new")
	public String initCreationForm(final Map<String, Object> model) {
		FoodOffer foodOffer = new FoodOffer();
		model.put("foodOffer", foodOffer);
		return FoodOfferController.VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/offers/food/new")
	public String processCreationForm(@Valid final FoodOffer foodOffer, BindingResult result) {

		if (!this.checkDates(foodOffer)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");
		}

		if (foodOffer.getStart() == null || foodOffer.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");
		}
		
		if(foodOffer.getImage().isEmpty()) {
			foodOffer.setImage(null);
		}else if(result.hasFieldErrors("image")) {
			result.getModel().put("imageError", true);
			result.getModel().put("imageErrorMessage", "La URL instroducida no es valida");
		}
		
		if (result.hasErrors()) {
			return FoodOfferController.VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;
		}
		
		Client client = this.clientService.getCurrentClient();
		foodOffer.setClient(client);
		foodOffer.setStatus(StatusOffer.hidden);
		
		this.foodOfferService.saveFoodOffer(foodOffer);
		return "redirect:/offers/food/" + foodOffer.getId();

	}

	@GetMapping(value = "/offers/food/{foodOfferId}/activate")
	public String activateFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, final ModelMap modelMap) {
		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		Client client = this.clientService.getCurrentClient();
		if (foodOffer.getClient().equals(client) && !foodOffer.isInactive()) {
			foodOffer.setStatus(StatusOffer.active);
			foodOffer.setCode("FO-" + foodOfferId);
			this.foodOfferService.saveFoodOffer(foodOffer);
		}

		return "redirect:/offers/food/" + foodOfferId;

	}

	@GetMapping("/offers/food/{foodOfferId}")
	public String processShowForm(@PathVariable("foodOfferId") final int foodOfferId, final Map<String, Object> model) {

		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		if (foodOffer.getStatus().equals(StatusOffer.active) || foodOffer.getStatus().equals(StatusOffer.hidden) && this.checkIdentity(foodOfferId)) {
			model.put("foodOffer", foodOffer);
			model.put("newPrice", foodOffer.getNewPrice());
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/food/foodOffersShow";

		} else {
			return "error";
		}
	}

	@GetMapping(value = "/offers/food/{foodOfferId}/edit")
	public String updateFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(foodOfferId)) {
			return "error";
		}
		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		if (foodOffer.getStatus().equals(StatusOffer.inactive)) {
			return "error";
		}
		model.addAttribute("foodOffer", foodOffer);
		request.getSession().setAttribute("idFood", foodOfferId);
		return FoodOfferController.VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/offers/food/{foodOfferId}/edit")
	public String updateFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, @Valid final FoodOffer foodOfferEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(foodOfferId)) {
			return "error";
		}
		Integer id = (Integer) request.getSession().getAttribute("idFood");
		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(id);
		if (!this.checkOffer(foodOffer, foodOfferEdit)) {
			return "error";
		}

		if (foodOfferEdit.getStart() == null || foodOfferEdit.getStart().isBefore(LocalDateTime.now()) && !foodOfferEdit.getStart().equals(foodOffer.getStart())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura o la original");

		}

		if (!this.checkDates(foodOfferEdit)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}

		if (result.hasErrors()) {
			model.addAttribute("foodOffer", foodOfferEdit);
			return FoodOfferController.VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;

		}
		if(foodOfferEdit.getImage().isEmpty()) {
			foodOfferEdit.setImage(null);
		}
		
		BeanUtils.copyProperties(this.foodOfferService.findFoodOfferById(foodOfferEdit.getId()), foodOfferEdit, "start", "end", "food", "discount", "price","image");
		
		this.foodOfferService.saveFoodOffer(foodOfferEdit);
		return "redirect:/offers/food/" + foodOfferEdit.getId();

	}
	
	@GetMapping(value = "/offers/food/{foodOfferId}/delete/image")
	public String deleteImageFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, final ModelMap model) {

		if (!this.checkIdentity(foodOfferId)) {
			return "error";
		}

		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
			if(foodOffer.getImage() != null ) {
			foodOffer.setImage(null);
			
			}
			this.foodOfferService.saveFoodOffer(foodOffer);
		return "redirect:/offers/food/" + foodOffer.getId();
	}

	@GetMapping(value = "/offers/food/{foodOfferId}/disable")
	public String disableFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, final ModelMap model) {

		if (!this.checkIdentity(foodOfferId)) {
			return "error";
		}

		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		model.put("foodOffer", foodOffer);
		return "offers/food/foodOffersDisable";
	}

	@PostMapping(value = "/offers/food/{foodOfferId}/disable")
	public String disableFoodOfferForm(@PathVariable("foodOfferId") final int foodOfferId, final ModelMap model) {

		if (!this.checkIdentity(foodOfferId)) {
			return "error";
		}

		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);

		foodOffer.setStatus(StatusOffer.inactive);

		this.foodOfferService.saveFoodOffer(foodOffer);

		return "redirect:/myOffers";

	}
}
