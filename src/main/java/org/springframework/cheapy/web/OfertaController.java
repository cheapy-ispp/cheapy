
package org.springframework.cheapy.web;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.Offer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfertaController {

	private final ClientService		clientService;

	private final FoodOfferService	foodOfferService;
	private final NuOfferService	nuOfferService;
	private final SpeedOfferService	speedOfferService;
	private final TimeOfferService	timeOfferService;


	public OfertaController(final FoodOfferService foodOfferService, final NuOfferService nuOfferService, final SpeedOfferService speedOfferService, final TimeOfferService timeOfferService, final ClientService clientService) {
		this.clientService = clientService;
		this.foodOfferService = foodOfferService;
		this.nuOfferService = nuOfferService;
		this.speedOfferService = speedOfferService;
		this.timeOfferService = timeOfferService;
	}

	@GetMapping("/offers")
	public String processFindForm(final Map<String, Object> model) {
		Pageable elements = PageRequest.of(0, 3);

		List<FoodOffer> foodOfferLs = this.foodOfferService.findActiveFoodOffer(elements);
		List<NuOffer> nuOfferLs = this.nuOfferService.findActiveNuOffer(elements);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findActiveSpeedOffer(elements);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findActiveTimeOffer(elements);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersList";

	}

	@GetMapping("/offersByName/{page}")
	public String processFindFormByName(@PathVariable("page") final int page, final Map<String, Object> model, final String name) {
		Pageable elements = PageRequest.of(page, 2);
		Pageable nextPage = PageRequest.of(page+1, 2);
		
		List<Object[]> datos = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientName(name, elements)) {
			Object[] fo = {of, "food"};
			datos.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientName(name, elements)) {
			Object[] nu = {of, "nu"};
			datos.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientName(name, elements)) {
			Object[] sp = {of, "speed"};
			datos.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientName(name, elements)) {
			Object[] ti = {of, "time"};
			datos.add(ti);
		}
		
		List<Object[]> datosNext = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientName(name, nextPage)) {
			Object[] fo = {of, "food"};
			datosNext.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientName(name, nextPage)) {
			Object[] nu = {of, "nu"};
			datosNext.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientName(name, nextPage)) {
			Object[] sp = {of, "speed"};
			datosNext.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientName(name, nextPage)) {
			Object[] ti = {of, "time"};
			datosNext.add(ti);
		}
		Integer next = datosNext.size();
		Integer now = datos.size();
		model.put("now", now);
		model.put("nextPage", next);
		model.put("datos", datos);
		model.put("name", name);
		
		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListNameSearch";

	}

	@GetMapping("/offersByFood/{page}")
	public String processFindFormByFood(@PathVariable("page") final int page, final Map<String, Object> model, final String name) {
		Pageable elements = PageRequest.of(page, 2);
		Pageable nextPage = PageRequest.of(page+1, 2);
		
		List<Object[]> datos = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientFood(name, elements)) {
			Object[] fo = {of, "food"};
			datos.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientFood(name, elements)) {
			Object[] nu = {of, "nu"};
			datos.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientFood(name, elements)) {
			Object[] sp = {of, "speed"};
			datos.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientFood(name, elements)) {
			Object[] ti = {of, "time"};
			datos.add(ti);
		}
		
		List<Object[]> datosNext = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientFood(name, nextPage)) {
			Object[] fo = {of, "food"};
			datosNext.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientFood(name, nextPage)) {
			Object[] nu = {of, "nu"};
			datosNext.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientFood(name, nextPage)) {
			Object[] sp = {of, "speed"};
			datosNext.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientFood(name, nextPage)) {
			Object[] ti = {of, "time"};
			datosNext.add(ti);
		}
		Integer next = datosNext.size();
		Integer now = datos.size();
		model.put("now", now);
		model.put("nextPage", next);
		model.put("datos", datos);
		model.put("name", name);
		
		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListFoodSearch";

	}

	@GetMapping("/offersByPlace/{page}")
	public String processFindFormByPlace(@PathVariable("page") final int page, final Map<String, Object> model, final HttpServletRequest request) {

		if (request.getParameter("municipio").equals("") || request.getParameter("municipio").equals(null)) {
			// Añade la lista de municipios al desplegable
			model.put("municipios", Municipio.values());

			//Se añade formateador de fecha al modelo
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			return "redirect:/offers/";
		}

		Municipio mun = Municipio.valueOf(request.getParameter("municipio"));

		Pageable elements = PageRequest.of(page, 2);
		Pageable nextPage = PageRequest.of(page+1, 2);
		
		List<Object[]> datos = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientPlace(mun, elements)) {
			Object[] fo = {of, "food"};
			datos.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientPlace(mun, elements)) {
			Object[] nu = {of, "nu"};
			datos.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientPlace(mun, elements)) {
			Object[] sp = {of, "speed"};
			datos.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientPlace(mun, elements)) {
			Object[] ti = {of, "time"};
			datos.add(ti);
		}
		
		List<Object[]> datosNext = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientPlace(mun, nextPage)) {
			Object[] fo = {of, "food"};
			datosNext.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientPlace(mun, nextPage)) {
			Object[] nu = {of, "nu"};
			datosNext.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientPlace(mun, nextPage)) {
			Object[] sp = {of, "speed"};
			datosNext.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientPlace(mun, nextPage)) {
			Object[] ti = {of, "time"};
			datosNext.add(ti);
		}
		Integer next = datosNext.size();
		Integer now = datos.size();
		model.put("mun", mun);
		model.put("now", now);
		model.put("nextPage", next);
		model.put("datos", datos);
		
		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListPlaceSearch";

	}

	@GetMapping("/myOffers")
	public String processMyOffersForm(final Map<String, Object> model) {

		int actual = this.clientService.getCurrentClient().getId();

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferActOclByUserId(actual);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferActOclByUserId(actual);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferActOclByUserId(actual);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferActOclByUserId(actual);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/myOffersList";
	}

	@GetMapping("/offersCreate")
	public String createOffers() {

		return "offers/offersCreate";
	}

	//	@GetMapping("/owners/{ownerId}/edit")
	//	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//		model.addAttribute(owner);
	//		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//	}
	//
	//	@PostMapping("/owners/{ownerId}/edit")
	//	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
	//			@PathVariable("ownerId") int ownerId) {
	//		if (result.hasErrors()) {
	//			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//		}
	//		else {
	//			owner.setId(ownerId);
	//			this.ownerService.saveOwner(owner);
	//			return "redirect:/owners/{ownerId}";
	//		}
	//	}
	//	@GetMapping("/owners/{ownerId}")
	//	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
	//		ModelAndView mav = new ModelAndView("owners/ownerDetails");
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//
	//		mav.addObject(owner);
	//		return mav;
	//	}

}
