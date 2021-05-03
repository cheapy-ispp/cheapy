
package org.springframework.cheapy.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.Offer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.service.UserService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdministratorController {


	private final UsuarioService	usuarioService;
	private final ClientService		clientService;
	private final UserService		userService;

	private final FoodOfferService	foodOfferService;
	private final SpeedOfferService	speedOfferService;
	private final NuOfferService	nuOfferService;
	private final TimeOfferService	timeOfferService;


	public AdministratorController(final UsuarioService usuarioService, final ClientService clientService, final FoodOfferService foodOfferService, final SpeedOfferService speedOfferService, final NuOfferService nuOfferService,
		final TimeOfferService timeOfferService, final UserService userService) {
		this.usuarioService = usuarioService;
		this.clientService = clientService;
		this.foodOfferService = foodOfferService;
		this.speedOfferService = speedOfferService;
		this.nuOfferService = nuOfferService;
		this.timeOfferService = timeOfferService;
		this.userService = userService;
	}

	@GetMapping("/administrators/usuarios/page/{page}")
	public String processFindUsuariosForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 10);
		Pageable nextPage = PageRequest.of(page + 1, 10);

		List<Usuario> usuarioLs = this.usuarioService.findAllUsuario(elements);
		
		Integer next = this.usuarioService.findAllUsuario(nextPage).size();
		model.put("usuarioLs", usuarioLs);
		model.put("nextPage", next);
		return "usuarios/usuariosList";
	}

	@GetMapping("/administrators/clients/page/{page}")
	public String processFindClientesForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 10);
		Pageable nextPage = PageRequest.of(page + 1, 10);

		List<Client> clientLs = this.clientService.findAllNonDeletedClients(elements);
		
		Integer next = this.clientService.findAllNonDeletedClients(nextPage).size();
		model.put("clientLs", clientLs);
		model.put("nextPage", next);
		return "clients/clientsList";
	}
	@GetMapping("/administrators/usuarios/{username}")
	public String processUsuarioShowForm(@PathVariable("username") final String username, final Map<String, Object> model) {
		Usuario usuario = this.usuarioService.findByUsername(username);
		if (usuario==null) {
			return "error";
		}
		model.put("usuario", usuario);
		return "usuarios/usuariosShow";
	}

	@GetMapping("/administrators/clients/{username}")
	public String processClientShowForm(@PathVariable("username") final String username, final Map<String, Object> model) {
		Client client = this.clientService.findByUsername(username);
		if (client==null) {
			return "error";
		}
		model.put("client", client);
		return "clients/clientShow";
	}

	@GetMapping(value = "/administrators/usuarios/{username}/disable")
	public String disableUsuario(@PathVariable("username") final String username, final ModelMap model) {

		Usuario usuario = this.usuarioService.findByUsername(username);
		model.put("usuario", usuario);
		return "usuarios/usuariosDisable";
	}

	@PostMapping(value = "/administrators/usuarios/{username}/disable")
	public String disableUsuarioForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Usuario usuario = this.usuarioService.findByUsername(username);
		usuario.getUsuar().setEnabled(false);
		this.usuarioService.saveUsuario(usuario);
		return "redirect:/administrators/usuarios/page/0";
	}

	@GetMapping(value = "/administrators/clients/{username}/disable")
	public String disableClient(@PathVariable("username") final String username, final ModelMap model) {

		Client client = this.clientService.findByUsername(username);
		model.put("client", client);
		return "clients/clientDisable";
	}
	@PostMapping(value = "/administrators/clients/{username}/disable")
	public String disableClientForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.findByUsername(username);
		client.getUsuar().setEnabled(false);

		List<FoodOffer> foodOffers = this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers = this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers = this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers = this.timeOfferService.findTimeOfferByUserId(client.getId());

		foodOffers.stream().forEach(f -> f.setStatus(StatusOffer.inactive));

		speedOffers.stream().forEach(s -> s.setStatus(StatusOffer.inactive));

		nuOffers.stream().forEach(n -> n.setStatus(StatusOffer.inactive));

		timeOffers.stream().forEach(t -> t.setStatus(StatusOffer.inactive));

		this.clientService.saveClient(client);
		return "redirect:/administrators/clients/page/0";
	}

	@GetMapping(value = "/administrators/clients/{username}/activate")
	public String activateClient(@PathVariable("username") final String username, final ModelMap model) {

		Client client = this.clientService.findByUsername(username);
		model.put("client", client);
		return "clients/clientActivate";
	}
	@PostMapping(value = "/administrators/clients/{username}/activate")
	public String activateClientForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.findByUsername(username);
		client.getUsuar().setEnabled(true);
		this.clientService.saveClient(client);
		return "redirect:/administrators/clients/page/0";
	}
	
	@GetMapping("/administrators/offersRecord{page}")
    public String processOffersRecordForm(@PathVariable("page") final int page, final Map<String, Object> model) {
        Pageable elements = PageRequest.of(page, 2);
        Pageable nextPage = PageRequest.of(page+1, 2);

        List<Object[]> datos = ofertasPag(elements);
        List<Object[]> datosNext = ofertasPag(nextPage);

        Integer next = datosNext.size();
        model.put("nextPage", next);
        model.put("datos", datos);
        model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return "offers/offersRecordList";
    }

    private List<Object[]> ofertasPag(Pageable pag ){
    	List<Object[]> datos = new ArrayList<Object[]>();

        for(Offer of:this.foodOfferService.findAllFoodOffer(pag)) {
            Object[] fo = {of, "food"};
            datos.add(fo);
        }

        for(Offer of:this.nuOfferService.findAllNuOffer(pag)) {
            Object[] nu = {of, "nu"};
            datos.add(nu);
        }

        for(Offer of:this.speedOfferService.findAllSpeedOffer(pag)) {
            Object[] sp = {of, "speed"};
            datos.add(sp);
        }

        for(Offer of:this.timeOfferService.findAllTimeOffer(pag)) {
            Object[] ti = {of, "time"};
            datos.add(ti);
        }
        return datos;

    }
	
	@GetMapping("/administrators/offers/nu/{nuOfferId}")
	public String processShowNuForm(@PathVariable("nuOfferId") final int nuOfferId, final Map<String, Object> model) {
		
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		
		if (nuOffer != null) {
			model.put("nuOffer", nuOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/nu/nuOffersShow";
		}	else {
			return "welcome";
		}

	}
	
	@GetMapping("/administrators/offers/food/{foodOfferId}")
	public String processShowFoodForm(@PathVariable("foodOfferId") final int foodOfferId, final Map<String, Object> model) {

		FoodOffer foodOffer = this.foodOfferService.findFoodOfferById(foodOfferId);
		
		if (foodOffer != null) {
			model.put("foodOffer", foodOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/food/foodOffersShow";

		} else {
			return "welcome";
		}
	}
	
	@GetMapping("/administrators/offers/speed/{speedOfferId}")
	public String processShowSpeedForm(@PathVariable("speedOfferId") final int speedOfferId, final Map<String, Object> model) {
		
		SpeedOffer speedOffer = this.speedOfferService.findSpeedOfferById(speedOfferId);
		
		if (speedOffer != null) {
			model.put("speedOffer", speedOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/speed/speedOffersShow";
		} else {
			return "welcome";
		}
	}
	
	@GetMapping("/administrators/offers/time/{timeOfferId}")
	public String processShowTimeForm(@PathVariable("timeOfferId") final int timeOfferId, final Map<String, Object> model) {
		
		TimeOffer timeOffer = this.timeOfferService.findTimeOfferById(timeOfferId);
		
		if (timeOffer != null) {
			model.put("timeOffer", timeOffer);
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "offers/time/timeOffersShow";

		} else {
			return "welcome";
		}
	}
	
	@GetMapping(value = "/administrators/usuarios/{usuarioId}/delete")
	public String deleteUsuario(@PathVariable("usuarioId") final int usuarioId , final ModelMap model) {
		Usuario usuario = this.usuarioService.findById(usuarioId);
		model.put("usuario", usuario);
		return "usuarios/usuariosDelete";
	}

	@PostMapping(value = "/administrators/usuarios/{usuarioId}/delete")
	public String deleteUsuarioForm(@PathVariable("usuarioId") final int usuarioId , final ModelMap model, final HttpServletRequest request) {
		Usuario usuario = this.usuarioService.findById(usuarioId);
		this.usuarioService.deleteUsuario(usuario);
		return "redirect:/administrators/usuarios/page/0";
	}
	
	@GetMapping(value = "/administrators/clients/{clientId}/delete")
	public String deleteClient(@PathVariable("clientId") final int clientId, final ModelMap model) {
		Client client = this.clientService.findById(clientId);
		model.put("client", client);
		return "/clients/clientDelete";
	}

	@PostMapping(value = "/administrators/clients/{clientId}/delete")
	public String deleteClientForm(@PathVariable("clientId") final int clientId, final ModelMap model, final HttpServletRequest request) {
		Client client = this.clientService.findById(clientId);

		List<FoodOffer> foodOffers = this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers = this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers = this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers = this.timeOfferService.findTimeOfferByUserId(client.getId());

		foodOffers.stream().forEach(f -> f.setStatus(StatusOffer.inactive));

		speedOffers.stream().forEach(s -> s.setStatus(StatusOffer.inactive));

		nuOffers.stream().forEach(n -> n.setStatus(StatusOffer.inactive));

		timeOffers.stream().forEach(t -> t.setStatus(StatusOffer.inactive));

		client.setAddress("Eliminado");
		client.setDescription("Eliminado");
		client.setEmail("eliminado@gmail.com");
		client.setExpiration(LocalDate.now());
		client.setFinish(LocalTime.of(00, 00));
		client.setFood("Eliminado");
		client.setInit(LocalTime.of(00, 00));
		client.setMunicipio(Municipio.Sevilla);
		client.setTelephone("000000000");
		User elim = client.getUsuar();
		client.setUsuar(null);
		this.clientService.saveClient(client);	
		this.userService.deleteUser(elim);
		return "redirect:/administrators/clients/page/0";

	}
}
