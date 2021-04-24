
package org.springframework.cheapy.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.utils.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

	private static final String VIEWS_CREATE_OR_UPDATE_CLIENT = "clients/createOrUpdateClientForm";

	private final ClientService clientService;
	
	private final FoodOfferService foodOfferService;
	
	private final SpeedOfferService speedOfferService;
	
	private final NuOfferService nuOfferService;
	
	private final TimeOfferService timeOfferService;
	
	

	public ClientController(final ClientService clientService, FoodOfferService foodOfferService, 
			SpeedOfferService speedOfferService, NuOfferService nuOfferService, TimeOfferService timeOfferService) {
		this.clientService = clientService;
		this.foodOfferService=foodOfferService;
		this.speedOfferService=speedOfferService;
		this.nuOfferService=nuOfferService;
		this.timeOfferService=timeOfferService;
	}


	private boolean checkTimes(final Client client) {
		boolean res = false;
		if(client.getFinish()==null || client.getInit()==null || client.getFinish().isAfter(client.getInit())) {
			res = true;
		}
		return res;
	}


	@GetMapping("/clients/show")
	public String processShowForm(Map<String, Object> model) {

		Client client = this.clientService.getCurrentClient();
		

			model.put("client", client);
			return "clients/clientShow";
		


	}

	@GetMapping(value = "/clients/edit")
	public String updateClient( final ModelMap model, HttpServletRequest request) {
		Map<Object, String> municipios = new HashMap<Object, String>();
		
		Municipio[] a = Municipio.values();
		int cont = 0;
		for (Municipio i : Municipio.values()) {
		    municipios.put(a[cont], i.toString());
		    cont++;
		}
		Client client = this.clientService.getCurrentClient();
		
		model.put("municipios", municipios);
		model.addAttribute("client", client);
		model.addAttribute("municipio", client.getMunicipio());

		return ClientController.VIEWS_CREATE_OR_UPDATE_CLIENT;
	}

	@PostMapping(value = "/clients/edit")
	public String updateClient(@Valid final Client clientEdit, final BindingResult result,
			final ModelMap model, HttpServletRequest request) {
		Client clienteSesion = this.clientService.getCurrentClient();
		BeanUtils.copyProperties(clienteSesion, clientEdit, "name", "email", "address","init","municipio", "finish","telephone", "description","food","expiration","usuar");
		if(!this.checkTimes(clientEdit)) {
			result.rejectValue("finish","" ,"La hora de cierre debe ser posterior a la hora de apertura");
			
		}
		
		if(clientEdit.getUsuar().getPassword().equals("")) {
			result.rejectValue("usuar.password","" ,"La contraseña no puede estar vacía");
			
		}
	

			if (result.hasErrors()) {
				model.addAttribute("client", clientEdit);
				Map<Object, String> municipios = new HashMap<Object, String>();
				Municipio[] a = Municipio.values();
				int cont = 0;
				for (Municipio i : Municipio.values()) {
				    municipios.put(a[cont], i.toString());
				    cont++;
				}
				model.put("municipios", municipios);
				return ClientController.VIEWS_CREATE_OR_UPDATE_CLIENT;
			}
			
			clientEdit.getUsuar().setUsername(clienteSesion.getUsuar().getUsername());
			clientEdit.getUsuar().setEnabled(true);
			this.clientService.saveClient(clientEdit);
			return "redirect:/clients/show";
		
	}

	@GetMapping(value = "/clients/disable")
	public String disableClient(final ModelMap model) {

		Client client = this.clientService.getCurrentClient();
		model.put("client", client);
		return "/clients/clientDisable";
	}

	@PostMapping(value = "/clients/disable")
	public String disableClientForm(final ModelMap model,  HttpServletRequest request) {


		Client client = this.clientService.getCurrentClient();

		
		
		List<FoodOffer> foodOffers=this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers=this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers=this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers=this.timeOfferService.findTimeOfferByUserId(client.getId());
		
		foodOffers.stream().forEach(f->f.setStatus(StatusOffer.inactive));
		
		speedOffers.stream().forEach(s->s.setStatus(StatusOffer.inactive));
		
		nuOffers.stream().forEach(n->n.setStatus(StatusOffer.inactive));
		
		timeOffers.stream().forEach(t->t.setStatus(StatusOffer.inactive));
		
		
		client.getUsuar().setEnabled(false);
		this.clientService.saveClient(client);
		
	try {
		request.logout();
	} catch (ServletException e) {

	}
		return "redirect:/login";

	}
	@GetMapping(value = "/restaurant/{clientId}")
	public String showRestaurant(final ModelMap model, @PathVariable("clientId") Integer id) {

		Client client = this.clientService.findById(id);
		Integer valoraciones=this.clientService.mediaValoraciones(client);

		model.put("client", client);
		model.put("reviews", valoraciones);
		return "clients/restaurantShow";
	}
	
	@GetMapping(value = "/clients/edit/password")
	public String updatePassClient(final ModelMap model, final HttpServletRequest request) {
	
		Client clienteSesion = this.clientService.getCurrentClient();
		clienteSesion.getUsuar().setPassword("");
		model.addAttribute("client", clienteSesion);
		return "clients/password";
	}
	
	@PostMapping(value = "/clients/edit/password")
	public String updatePassClient(@Valid final Client clientEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {

		Client clienteSesion = this.clientService.getCurrentClient();

		if (clientEdit.getUsuar().getPassword().equals("")) {
			result.rejectValue("usuar.password", "", "La contraseña no puede estar vacía");
		}

		if (result.hasErrors()) {
			return "clients/password";
		}

		BeanUtils.copyProperties(clienteSesion, clientEdit, "name", "email", "address","init","municipio", "finish","telephone", "description","food","expiration","usuar");
		clientEdit.getUsuar().setUsername(clienteSesion.getUsuar().getUsername());
		clientEdit.getUsuar().setPassword(MD5.md5(clientEdit.getUsuar().getPassword()));
		clientEdit.getUsuar().setEnabled(true);
		this.clientService.saveClient(clientEdit);
		return "redirect:/clients/show";
}
}
