
package org.springframework.cheapy.web;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NuOfferController {

	private static final String		VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM	= "offers/nu/createOrUpdateNuOfferForm";

	private final NuOfferService	nuOfferService;
	private final ClientService		clientService;


	public NuOfferController(final NuOfferService nuOfferService, final ClientService clientService) {
		this.nuOfferService = nuOfferService;
		this.clientService = clientService;
	}

	private boolean checkIdentity(final int nuOfferId) {
		boolean res = false;
		Client client = this.clientService.getCurrentClient();
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		Client clientOffer = nuOffer.getClient();
		if (client.equals(clientOffer)) {
			res = true;
		}
		return res;
	}

	private boolean checkOffer(final NuOffer session, final NuOffer offer) {
		boolean res = false;
		if (session.getId() == offer.getId() && session.getStatus().equals(offer.getStatus()) && (session.getCode() == null ? offer.getCode().equals("") : session.getCode().equals(offer.getCode())) && !session.getStatus().equals(StatusOffer.inactive)) {
			res = true;
		}
		return res;
	}

	private boolean checkDates(final NuOffer nuOffer) {
		boolean res = false;
		if (nuOffer.getEnd() == null || nuOffer.getStart() == null || nuOffer.getEnd().isAfter(nuOffer.getStart())) {
			res = true;
		}
		return res;
	}

	private boolean checkConditionsGold(final NuOffer nuOffer) {
		boolean res = false;
		if (nuOffer.getGold() == null || nuOffer.getSilver() == null) {
			res = true;
		} else if (nuOffer.getGold() >= nuOffer.getSilver()) {
			res = true;
		}
		return res;
	}
	
	private boolean checkConditionsSilver(final NuOffer nuOffer) {
		boolean res = false;
		if (nuOffer.getGold() == null || nuOffer.getSilver() == null) {
			res = true;
		} else if (nuOffer.getSilver() >= nuOffer.getBronze()) {
			res = true;
		}
		return res;
	}
	
	private boolean checkConditionsBronze(final NuOffer nuOffer) {
		boolean res = false;
		if (nuOffer.getGold() == null || nuOffer.getSilver() == null || nuOffer.getBronze() == null) {
			res = true;
		} else if (nuOffer.getGold() >= nuOffer.getBronze() && nuOffer.getSilver() >= nuOffer.getBronze()) {
			res = true;
		}
		return res;
	}

	private boolean checkDiscountsGold(final NuOffer NuOffer) {
		boolean res = false;
		if (NuOffer.getDiscountGold() == null || NuOffer.getDiscountSilver() == null) {
			res = true;
		} else if (NuOffer.getDiscountGold() >= NuOffer.getDiscountSilver()) {
			res = true;
		}
		return res;
	}
	
	private boolean checkDiscountsSilver(final NuOffer NuOffer) {
		boolean res = false;
		if (NuOffer.getDiscountSilver() == null || NuOffer.getDiscountBronze() == null) {
			res = true;
		} else if (NuOffer.getDiscountSilver() >= NuOffer.getDiscountBronze()) {
			res = true;
		}
		return res;
	}
	
	private boolean checkDiscountsBronze(final NuOffer NuOffer) {
		boolean res = false;
		if (NuOffer.getDiscountGold() == null || NuOffer.getDiscountSilver() == null || NuOffer.getDiscountBronze() == null) {
			res = true;
		} else if (NuOffer.getDiscountGold() >= NuOffer.getDiscountBronze() && NuOffer.getDiscountSilver() >= NuOffer.getDiscountBronze()) {
			res = true;
		}
		return res;
	}

	@GetMapping("/offers/nuOfferList/{page}")
	public String processFindForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 5);
		Pageable nextPage = PageRequest.of(page + 1, 5);

		List<NuOffer> foodOfferLs = this.nuOfferService.findActiveNuOffer(elements);
		for(int i=0; i<foodOfferLs.size();i++) {
			NuOffer fo= foodOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			foodOfferLs.set(i, fo);
		}
		Integer next = this.nuOfferService.findActiveNuOffer(nextPage).size();

		model.put("municipios", Municipio.values());

		model.put("nuOfferLs", foodOfferLs);
		model.put("nextPage", next);
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return "offers/nu/nuOffersList";

	}

	@GetMapping("/offers/nu/new")
	public String initCreationForm(final Map<String, Object> model) {
		NuOffer nuOffer = new NuOffer();
		model.put("nuOffer", nuOffer);
		return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/offers/nu/new")
	public String processCreationForm(@Valid final NuOffer nuOffer, final BindingResult result) {

		if (!this.checkDates(nuOffer)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}
		if (!this.checkConditionsGold(nuOffer)) {
			result.rejectValue("gold", "", "Debe ser mayor o igual que plata");

		}
		if (!this.checkConditionsSilver(nuOffer)) {
			result.rejectValue("silver", "", "Debe ser mayor o igual que bronce");

		}
		if (!this.checkConditionsBronze(nuOffer)) {
			result.rejectValue("bronze", "", "Debe ser menor o igual que plata y oro");

		}
		if (!this.checkDiscountsGold(nuOffer)) {
			result.rejectValue("discountGold", "", "Debe ser mayor o igual que el de plata");

		}
		if (!this.checkDiscountsSilver(nuOffer)) {
			result.rejectValue("discountSilver", "", "Debe ser mayor o igual que el de bronce");

		}
		if (!this.checkDiscountsBronze(nuOffer)) {
			result.rejectValue("discountBronze", "", "Debe ser menor o igual que el de plata y el de oro");

		}

		if (nuOffer.getStart() == null || nuOffer.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");

		}

		if (result.hasErrors()) {
			return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
		}
		nuOffer.setStatus(StatusOffer.hidden);

		Client client = this.clientService.getCurrentClient();

		nuOffer.setClient(client);

		this.nuOfferService.saveNuOffer(nuOffer);
		return "redirect:/offers/nu/" + nuOffer.getId();

	}

	@GetMapping(value = "/offers/nu/{nuOfferId}/activate")
	public String activateNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final ModelMap modelMap) {
		Client client = this.clientService.getCurrentClient();
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		if (nuOffer.getClient().equals(client) && !nuOffer.isInactive()) {
			nuOffer.setStatus(StatusOffer.active);
			nuOffer.setCode("NU-" + nuOfferId);
			this.nuOfferService.saveNuOffer(nuOffer);
		}
		return "redirect:/offers/nu/" + nuOffer.getId();

	}

	@GetMapping("/offers/nu/{nuOfferId}")
	public String processShowForm(@PathVariable("nuOfferId") final int nuOfferId, final Map<String, Object> model) {
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		if ((nuOffer.getStatus().equals(StatusOffer.active)) ||
				(nuOffer.getStatus().equals(StatusOffer.hidden) && this.checkIdentity(nuOfferId))) {
			model.put("nuOffer", nuOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/nu/nuOffersShow";

		} else {
			return "error";
		}

	}

	@GetMapping(value = "/offers/nu/{nuOfferId}/edit")
	public String updateNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(nuOfferId)) {
			return "error";
		}
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		if (nuOffer.getStatus().equals(StatusOffer.inactive)) {
			return "error";
		}
		model.addAttribute("nuOffer", nuOffer);
		request.getSession().setAttribute("idNu", nuOfferId);
		return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/offers/nu/{nuOfferId}/edit")
	public String updateNuOffer(@PathVariable("nuOfferId") final int nuOfferId, @Valid final NuOffer nuOfferEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {

		if (!this.checkIdentity(nuOfferId)) {
			return "error";
		}
		Integer id = (Integer) request.getSession().getAttribute("idNu");
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(id);
		if (!this.checkOffer(nuOffer, nuOfferEdit)) {
			return "error";
		}

		if (!this.checkDates(nuOfferEdit)) {
			result.rejectValue("end", "", "La fecha de fin debe ser posterior a la fecha de inicio");

		}

		if (nuOfferEdit.getStart() == null || nuOfferEdit.getStart().isBefore(LocalDateTime.now())) {
			result.rejectValue("start", "", "La fecha de inicio debe ser futura");

		}

		if (!this.checkConditionsGold(nuOfferEdit)) {
			result.rejectValue("gold", "", "Debe ser mayor o igual que plata");

		}
		if (!this.checkConditionsSilver(nuOfferEdit)) {
			result.rejectValue("silver", "", "Debe ser mayor o igual que bronce");

		}
		if (!this.checkConditionsBronze(nuOfferEdit)) {
			result.rejectValue("bronze", "", "Debe ser menor o igual que plata y oro");

		}
		if (!this.checkDiscountsGold(nuOfferEdit)) {
			result.rejectValue("discountGold", "", "Debe ser mayor o igual que el de plata");

		}
		if (!this.checkDiscountsSilver(nuOfferEdit)) {
			result.rejectValue("discountSilver", "", "Debe ser mayor o igual que el de bronce");

		}
		if (!this.checkDiscountsBronze(nuOfferEdit)) {
			result.rejectValue("discountBronze", "", "Debe ser menor o igual que el de plata y el de oro");

		}

		if (result.hasErrors()) {
			model.addAttribute("nuOffer", nuOfferEdit);
			return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;

		}
		BeanUtils.copyProperties(this.nuOfferService.findNuOfferById(nuOfferEdit.getId()), nuOfferEdit, "start", "end", "gold", "discountGold", "silver", "discountSilver", "bronze", "discountBronze");
		this.nuOfferService.saveNuOffer(nuOfferEdit);
		return "redirect:/offers/nu/" + nuOfferEdit.getId();

	}

	@GetMapping(value = "/offers/nu/{nuOfferId}/disable")
	public String disableNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final Principal principal, final ModelMap model) {

		if (!this.checkIdentity(nuOfferId)) {
			return "error";
		}

		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		model.put("nuOffer", nuOffer);
		return "offers/nu/nuOffersDisable";
	}

	@PostMapping(value = "/offers/nu/{nuOfferId}/disable")
	public String disableNuOfferForm(@PathVariable("nuOfferId") final int nuOfferId, final Principal principal, final ModelMap model) {

		if (!this.checkIdentity(nuOfferId)) {
			return "error";
		}

		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		nuOffer.setStatus(StatusOffer.inactive);
		this.nuOfferService.saveNuOffer(nuOffer);
		return "redirect:/myOffers";

	}

}
