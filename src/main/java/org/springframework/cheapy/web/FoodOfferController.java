
package org.springframework.cheapy.web;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FoodOfferController {

	private static final String VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM = "foodOffers/createOrUpdateFoodOfferForm";

	private final FoodOfferService foodOfferService;
	private final ClientService clientService;


	public FoodOfferController(final FoodOfferService foodOfferService, final ClientService clientService) {
		this.foodOfferService = foodOfferService;
		this.clientService = clientService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/foodOffers/new")
	public String initCreationForm(Map<String, Object> model) {
		FoodOffer foodOffer = new FoodOffer();
		model.put("foodOffer", foodOffer);
		return VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/foodOffers/new")
	public String processCreationForm(@Valid FoodOffer foodOffer, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_FOOD_OFFER_CREATE_OR_UPDATE_FORM;
		}
		else {
			Client client = this.clientService.getCurrentClient();
			foodOffer.setClient(client);
			foodOffer.setType(StatusOffer.hidden);
			this.foodOfferService.saveFoodOffer(foodOffer);
			return "redirect:/foodOffers/" + foodOffer.getId();
		}
	}
	
	@GetMapping(value = "/foodOffers/{foodOfferId}/activate")
	public String activateFoodOffer(@PathVariable("foodOfferId") final int foodOfferId, ModelMap modelMap) {
		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		Client client = this.clientService.getCurrentClient();
		if(foodOffer.getClient().equals(client)) {
			foodOffer.setType(StatusOffer.active);
			foodOffer.setCode("FO-"+foodOfferId);
			this.foodOfferService.saveFoodOffer(foodOffer);
		} else {
			modelMap.addAttribute("message", "You don't have access to this food offer");
		}
		return "redirect:/foodOffers/";
	}
	@GetMapping("/offers/food/{foodOfferId}")
	public String processShowForm(@PathVariable("foodOfferId") int foodOfferId, Map<String, Object> model) {

		FoodOffer foodOffer=this.foodOfferService.findFoodOfferById(foodOfferId);
		
		model.put("foodOffer", foodOffer);
		
		return "foodOffers/foodOffersShow";

	}
}
