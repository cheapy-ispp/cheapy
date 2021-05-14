
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
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TimeOfferController {

	private static final String		VIEWS_TIME_OFFER_CREATE_OR_UPDATE_FORM	= "offers/time/createOrUpdateTimeOfferForm";
	private final TimeOfferService	timeOfferService;
	private final ClientService		clientService;


	public TimeOfferController(final TimeOfferService timeOfferService, final ClientService clientService) {
		this.timeOfferService = timeOfferService;
		this.clientService = clientService;
	}

	private boolean checkIdentity(final int timeOfferId) {
		boolean res = false;
		Client client = this.clientService.getCurrentClient();
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		Client clientOffer = timeOffer.getClient();
		if (client.equals(clientOffer)) {
			res = true;
		}
		return res;
	}

	private boolean checkOffer(final TimeOffer session, final TimeOffer offer) {
		boolean res = false;
		if (session.getId() == offer.getId() && session.getStatus().equals(offer.getStatus()) && (session.getCode() == null ? offer.getCode().equals("") : session.getCode().equals(offer.getCode())) && !session.getStatus().equals(StatusOffer.inactive)) {
			res = true;
		}
		return res;
	}

	private boolean checkDates(final TimeOffer timeOffer) {
		boolean res = false;
		if (timeOffer.getEnd() == null || timeOffer.getStart() == null || timeOffer.getEnd().isAfter(timeOffer.getStart())) {
			res = true;
		}
		return res;
	}

	private boolean checkTimes(final TimeOffer timeOffer) {
		boolean res = false;
		if (timeOffer.getFinish() == null || timeOffer.getInit() == null || timeOffer.getFinish().isAfter(timeOffer.getInit())) {
			res = true;
		}
		return res;
	}

	@GetMapping("/offers/timeOfferList/{page}")
	public String processFindForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 5);
		Pageable nextPage = PageRequest.of(page + 1, 5);

		List<TimeOffer> timeOfferLs = this.timeOfferService.findActiveTimeOffer(elements);
		for (int i = 0; i < timeOfferLs.size(); i++) {
			TimeOffer fo = timeOfferLs.get(i);
			String aux = fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux + fo.getClient().getName().substring(1));

			timeOfferLs.set(i, fo);
		}
		Integer next = this.timeOfferService.findActiveTimeOffer(nextPage).size();

		Municipio[]municipios=Municipio.values();
		Arrays.sort(municipios);
		
		model.put("municipios", municipios);

		model.put("timeOfferLs", timeOfferLs);
		model.put("nextPage", next);
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return "offers/time/timeOffersList";

	}

	@GetMapping("/offers/time/new")
	public String initCreationForm(final Map<String, Object> model) {
		TimeOffer timeOffer = new TimeOffer();
		model.put("timeOffer", timeOffer);
		return TimeOfferController.VIEWS_TIME_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/offers/time/new")
	public String processCreationForm(@Valid final TimeOffer timeOffer, final BindingResult result) {

		if (!this.checkDates(timeOffer)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}

		if (!this.checkTimes(timeOffer)) {
			result.rejectValue("finish", "", "La hora de fin debe ser posterior a la de inicio");

		}

		if (timeOffer.getStart() == null || timeOffer.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");

		}

		if (result.hasErrors()) {
			return TimeOfferController.VIEWS_TIME_OFFER_CREATE_OR_UPDATE_FORM;
		}

		timeOffer.setStatus(StatusOffer.hidden);

		Client client = this.clientService.getCurrentClient();

		timeOffer.setClient(client);

		this.timeOfferService.saveTimeOffer(timeOffer);
		return "redirect:/offers/time/" + timeOffer.getId();

	}

	@GetMapping(value = "/offers/time/{timeOfferId}/activate")
	public String activateTimeOffer(@PathVariable("timeOfferId") final int timeOfferId, final ModelMap modelMap) {
		Client client = this.clientService.getCurrentClient();
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		if (timeOffer.getClient().equals(client) && !timeOffer.isInactive()) {
			timeOffer.setStatus(StatusOffer.active);
			timeOffer.setCode("TI-" + timeOfferId);
			this.timeOfferService.saveTimeOffer(timeOffer);
		}
		return "redirect:/offers/time/" + timeOffer.getId();

	}

	@GetMapping("/offers/time/{timeOfferId}")
	public String processShowForm(@PathVariable("timeOfferId") final int timeOfferId, final Map<String, Object> model) {
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		if (timeOffer.getStatus().equals(StatusOffer.active) || timeOffer.getStatus().equals(StatusOffer.hidden) && this.checkIdentity(timeOfferId)) {
			model.put("timeOffer", timeOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/time/timeOffersShow";

		} else {
			return "error";
		}
	}

	@GetMapping(value = "/offers/time/{timeOfferId}/edit")
	public String updateTimeOffer(@PathVariable("timeOfferId") final int timeOfferId, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(timeOfferId)) {
			return "error";
		}
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		if (timeOffer.getStatus().equals(StatusOffer.inactive)) {
			return "error";
		}

		model.addAttribute("timeOffer", timeOffer);
		request.getSession().setAttribute("idTime", timeOfferId);
		return TimeOfferController.VIEWS_TIME_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/offers/time/{timeOfferId}/edit")
	public String updateTimeOffer(@PathVariable("timeOfferId") final int timeOfferId, @Valid final TimeOffer timeOfferEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(timeOfferId)) {
			return "error";
		}
		Integer id = (Integer) request.getSession().getAttribute("idTime");
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(id);
		if (!this.checkOffer(timeOffer, timeOfferEdit)) {
			return "error";
		}

		if (timeOfferEdit.getStart() == null || timeOfferEdit.getStart().isBefore(LocalDateTime.now()) && !timeOfferEdit.getStart().equals(timeOffer.getStart())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura o la original");

		}

		if (!this.checkDates(timeOfferEdit)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}
		if (!this.checkTimes(timeOfferEdit)) {
			result.rejectValue("finish", "", "La hora de fin debe ser posterior a la de inicio");

		}
		if (result.hasErrors()) {
			model.addAttribute("timeOffer", timeOfferEdit);
			return TimeOfferController.VIEWS_TIME_OFFER_CREATE_OR_UPDATE_FORM;

		}

		BeanUtils.copyProperties(this.timeOfferService.findTimeOfferById(timeOfferEdit.getId()), timeOfferEdit, "start", "end", "init", "finish", "discount");
		this.timeOfferService.saveTimeOffer(timeOfferEdit);
		return "redirect:/offers/time/" + timeOfferEdit.getId();

	}

	@GetMapping(value = "/offers/time/{timeOfferId}/disable")
	public String disableTimeOffer(@PathVariable("timeOfferId") final int timeOfferId, final ModelMap model) {

		if (!this.checkIdentity(timeOfferId)) {
			return "error";
		}

		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		model.put("timeOffer", timeOffer);
		return "offers/time/timeOffersDisable";
	}

	@PostMapping(value = "/offers/time/{timeOfferId}/disable")
	public String disableTimeOfferForm(@PathVariable("timeOfferId") final int timeOfferId, final ModelMap model) {

		if (!this.checkIdentity(timeOfferId)) {
			return "error";
		}

		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);

		timeOffer.setStatus(StatusOffer.inactive);

		this.timeOfferService.saveTimeOffer(timeOffer);

		return "redirect:/myOffers";

	}

}
