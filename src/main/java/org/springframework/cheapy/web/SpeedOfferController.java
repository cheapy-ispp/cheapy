
package org.springframework.cheapy.web;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SpeedOfferController {

	private static final String		VIEWS_SPEED_OFFER_CREATE_OR_UPDATE_FORM	= "offers/speed/createOrUpdateSpeedOfferForm";

	private final SpeedOfferService	speedOfferService;
	private final ClientService		clientService;


	public SpeedOfferController(final SpeedOfferService speedOfferService, final ClientService clientService) {
		this.speedOfferService = speedOfferService;
		this.clientService = clientService;
	}

	private boolean checkIdentity(final int speedOfferId) {
		boolean res = false;
		Client client = this.clientService.getCurrentClient();
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		Client clientOffer = speedOffer.getClient();
		if (client.equals(clientOffer)) {
			res = true;
		}
		return res;
	}

	private boolean checkOffer(final SpeedOffer session, final SpeedOffer offer) {
		boolean res = false;
		if (session.getId() == offer.getId() && session.getStatus().equals(offer.getStatus()) && (session.getCode() == null ? offer.getCode().equals("") : session.getCode().equals(offer.getCode())) && !session.getStatus().equals(StatusOffer.inactive)) {
			res = true;
		}
		return res;
	}

	private boolean checkDates(final SpeedOffer speedOffer) {
		boolean res = false;
		if (speedOffer.getEnd() == null || speedOffer.getStart() == null || speedOffer.getEnd().isAfter(speedOffer.getStart())) {
			res = true;
		}
		return res;
	}

	private boolean checkConditions(final SpeedOffer speedOffer) {
		boolean res = false;
		if (speedOffer.getGold() == null || speedOffer.getSilver() == null || speedOffer.getBronze() == null) {
			res = true;
		} else if (speedOffer.getGold().isBefore(speedOffer.getSilver()) && speedOffer.getSilver().isBefore(speedOffer.getBronze())) {
			res = true;
		}
		return res;
	}

	private boolean checkDiscounts(final SpeedOffer speedOffer) {
		boolean res = false;
		if (speedOffer.getDiscountGold() == null || speedOffer.getDiscountSilver() == null || speedOffer.getDiscountBronze() == null) {
			res = true;
		} else if (speedOffer.getDiscountGold() >= speedOffer.getDiscountSilver() && speedOffer.getDiscountSilver() >= speedOffer.getDiscountBronze()) {
			res = true;
		}
		return res;
	}

	@GetMapping("/offers/speedOfferList/{page}")
	public String processFindForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 5);
		Pageable nextPage = PageRequest.of(page + 1, 5);

		List<SpeedOffer> speedOfferLs = this.speedOfferService.findActiveSpeedOffer(elements);
		for(int i=0; i<speedOfferLs.size();i++) {
			SpeedOffer fo= speedOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			speedOfferLs.set(i, fo);
		}
		Integer next = this.speedOfferService.findActiveSpeedOffer(nextPage).size();

		model.put("municipios", Municipio.values());

		model.put("speedOfferLs", speedOfferLs);
		model.put("nextPage", next);
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return "offers/speed/speedOffersList";

	}

	@GetMapping("/offers/speed/new")
	public String initCreationForm(final Map<String, Object> model) {
		SpeedOffer speedOffer = new SpeedOffer();
		model.put("speedOffer", speedOffer);
		return SpeedOfferController.VIEWS_SPEED_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/offers/speed/new")
	public String processCreationForm(@Valid final SpeedOffer speedOffer, final BindingResult result, final Map<String, Object> model) {

		if (!this.checkDates(speedOffer)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}
		if (!this.checkConditions(speedOffer)) {
			result.rejectValue("gold", "", "Oro debe ser menor o igual que plata, y plata menor o igual que bronce");

		}
		if (!this.checkDiscounts(speedOffer)) {
			result.rejectValue("discountGold", "", "El descuento de Oro debe ser menor o igual que el de plata, y el de plata menor o igual que el de bronce");

		}

		if (speedOffer.getStart() == null || speedOffer.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");

		}

		if (speedOffer.getGold() != null) {
			LocalTime a = speedOffer.getGold();
			model.put("gold", a);
		}

		if (speedOffer.getSilver() != null) {
			LocalTime b = speedOffer.getSilver();
			model.put("silver", b);
		}

		if (speedOffer.getBronze() != null) {
			LocalTime c = speedOffer.getBronze();
			model.put("bronze", c);
		}

		if (result.hasErrors()) {

			return SpeedOfferController.VIEWS_SPEED_OFFER_CREATE_OR_UPDATE_FORM;
		}

		Client client = this.clientService.getCurrentClient();
		speedOffer.setClient(client);
		speedOffer.setStatus(StatusOffer.hidden);
		this.speedOfferService.saveSpeedOffer(speedOffer);
		return "redirect:/offers/speed/" + speedOffer.getId();

	}

	@GetMapping(value = "/offers/speed/{speedOfferId}/activate")
	public String activateSpeedOffer(@PathVariable("speedOfferId") final int speedOfferId, final ModelMap modelMap) {
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		Client client = this.clientService.getCurrentClient();
		if (speedOffer.getClient().equals(client) && !speedOffer.isInactive()) {
			speedOffer.setStatus(StatusOffer.active);
			speedOffer.setCode("SP-" + speedOfferId);
			this.speedOfferService.saveSpeedOffer(speedOffer);
		}
		return "redirect:/offers/speed/" + speedOffer.getId();
	}

	@GetMapping("/offers/speed/{speedOfferId}")
	public String processShowForm(@PathVariable("speedOfferId") final int speedOfferId, final Map<String, Object> model) {
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		if ((speedOffer.getStatus().equals(StatusOffer.active)) ||
				(speedOffer.getStatus().equals(StatusOffer.hidden) && this.checkIdentity(speedOfferId))) {
			model.put("speedOffer", speedOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/speed/speedOffersShow";

		} else {
			return "error";
		}
	}

	@GetMapping(value = "/offers/speed/{speedOfferId}/edit")
	public String updateSpeedOffer(@PathVariable("speedOfferId") final int speedOfferId, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(speedOfferId)) {
			return "error";
		}
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		if (speedOffer.getStatus().equals(StatusOffer.inactive)) {
			return "error";
		}

		model.addAttribute("speedOffer", speedOffer);
		request.getSession().setAttribute("idSpeed", speedOfferId);
		return SpeedOfferController.VIEWS_SPEED_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/offers/speed/{speedOfferId}/edit")
	public String updateSpeedOffer(@PathVariable("speedOfferId") final int speedOfferId, @Valid final SpeedOffer speedOfferEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(speedOfferId)) {
			return "error";
		}
		Integer id = (Integer) request.getSession().getAttribute("idSpeed");
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(id);
		if (!this.checkOffer(speedOffer, speedOfferEdit)) {
			return "error";
		}

		if (!this.checkDates(speedOfferEdit)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}

		if (speedOfferEdit.getStart() == null || speedOfferEdit.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");

		}

		if (!this.checkConditions(speedOfferEdit)) {
			result.rejectValue("gold", "", "Los minutos de Oro deben ser menores o iguales que los de plata, y los de plata menores o iguales que los de bronce");

		}
		if (!this.checkDiscounts(speedOfferEdit)) {
			result.rejectValue("discountGold", "", "El descuento de Oro debe ser mayor o igual que el de plata, y el de plata mayor o igual que el de bronce");

		}

		if (result.hasErrors()) {
			model.addAttribute("speedOffer", speedOfferEdit);
			return SpeedOfferController.VIEWS_SPEED_OFFER_CREATE_OR_UPDATE_FORM;

		}
		BeanUtils.copyProperties(this.speedOfferService.findSpeedOfferById(speedOfferEdit.getId()), speedOfferEdit, "start", "end", "gold", "discountGold", "silver", "discountSilver", "bronze", "discountBronze");
		this.speedOfferService.saveSpeedOffer(speedOfferEdit);
		return "redirect:/offers/speed/" + speedOfferEdit.getId();

	}

	@GetMapping(value = "/offers/speed/{speedOfferId}/disable")
	public String disableSpeedOffer(@PathVariable("speedOfferId") final int speedOfferId, final ModelMap model) {

		if (!this.checkIdentity(speedOfferId)) {
			return "error";
		}

		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		model.put("speedOffer", speedOffer);
		return "offers/speed/speedOffersDisable";
	}

	@PostMapping(value = "/offers/speed/{speedOfferId}/disable")
	public String disableSpeedOfferForm(@PathVariable("speedOfferId") final int speedOfferId, final ModelMap model) {

		if (!this.checkIdentity(speedOfferId)) {
			return "error";
		}

		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);

		speedOffer.setStatus(StatusOffer.inactive);

		this.speedOfferService.saveSpeedOffer(speedOffer);

		return "redirect:/myOffers";

	}
}
