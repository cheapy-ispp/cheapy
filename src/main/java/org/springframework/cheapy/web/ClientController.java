
package org.springframework.cheapy.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.service.UserService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.cheapy.utils.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClientController {

	private static final String		VIEWS_CREATE_OR_UPDATE_CLIENT	= "clients/createOrUpdateClientForm";

	private final ClientService		clientService;

	private final UserService		userService;

	private final FoodOfferService	foodOfferService;

	private final SpeedOfferService	speedOfferService;

	private final NuOfferService	nuOfferService;

	private final TimeOfferService	timeOfferService;

	private final UsuarioService	usuarioService;


	public ClientController(final ClientService clientService, final UserService userService, final FoodOfferService foodOfferService, final SpeedOfferService speedOfferService, final NuOfferService nuOfferService, final TimeOfferService timeOfferService,
		final UsuarioService usuarioService) {
		this.clientService = clientService;
		this.userService = userService;
		this.foodOfferService = foodOfferService;
		this.speedOfferService = speedOfferService;
		this.nuOfferService = nuOfferService;
		this.timeOfferService = timeOfferService;
		this.usuarioService = usuarioService;
	}

	private boolean checkTimes(final Client client) {
		boolean res = false;
		if (client.getFinish() == null || client.getInit() == null || client.getFinish().isAfter(client.getInit())) {
			res = true;
		}
		return res;
	}

	@GetMapping("/clients/show")
	public String processShowForm(final Map<String, Object> model) {

		Client client = this.clientService.getCurrentClient();

		model.put("client", client);
		return "clients/clientShow";

	}

	@GetMapping(value = "/clients/edit")
	public String updateClient(final ModelMap model, final HttpServletRequest request) {
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
	public String updateClient(@RequestParam("file") MultipartFile imagen, @Valid final Client clientEdit, final BindingResult result, final ModelMap model, final HttpServletRequest request) {
		Client clienteSesion = this.clientService.getCurrentClient();
		BeanUtils.copyProperties(clienteSesion, clientEdit, "name", "email", "address", "parking", "init", "municipio", "finish", "telephone", "description", "food","image");
		if (!this.checkTimes(clientEdit)) {
			result.rejectValue("finish", "", "La hora de cierre debe ser posterior a la hora de apertura");

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
		
			if(!imagen.isEmpty()) {
			
			Path directorio = Paths.get("src//main//resources//static//resources//imagenesSubidas");
			String rutaAbsoluta = directorio.toFile().getAbsolutePath();
			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				if(clientEdit.getImage() != null) {
					Path rutaAntigua = Paths.get(rutaAbsoluta+"//"+clientEdit.getImage());
					Files.delete(rutaAntigua);
				}
				clientEdit.setImage(imagen.getOriginalFilename());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			}

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
	public String disableClientForm(final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.getCurrentClient();

		List<FoodOffer> foodOffers = this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers = this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers = this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers = this.timeOfferService.findTimeOfferByUserId(client.getId());

		foodOffers.stream().forEach(f -> f.setStatus(StatusOffer.inactive));

		speedOffers.stream().forEach(s -> s.setStatus(StatusOffer.inactive));

		nuOffers.stream().forEach(n -> n.setStatus(StatusOffer.inactive));

		timeOffers.stream().forEach(t -> t.setStatus(StatusOffer.inactive));

		client.getUsuar().setEnabled(false);
		this.clientService.saveClient(client);

		try {
			request.logout();
		} catch (ServletException e) {

		}
		return "redirect:/login";

	}

	@GetMapping(value = "/restaurant/{clientId}")
	public String showRestaurant(final ModelMap model, @PathVariable("clientId") final Integer id) {
		Client client = this.clientService.findById(id);
		Integer valoraciones = this.clientService.mediaValoraciones(client);
		Usuario usuario = this.usuarioService.getCurrentUsuario();
		if(client.getUsuar()==null) {
            return "error";
        }

		if (usuario == null) {
			model.put("favoritos", 0);
		} else if (usuario.getFavoritos().contains(client)) {
			model.put("favoritos", 1);
		} else {
			model.put("favoritos", 2);
		}
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
		
		if(!clientEdit.getUsuar().getPassword().matches("^[A-Za-z0-9]{4,}+") ) {
            result.rejectValue("usuar.password","" ,"La contraseña debe contener al menos cuatro caracteres (letras y números)");
        }
		
		if (result.hasErrors()) {
			return "clients/password";
		}

		String pass = MD5.md5(clientEdit.getUsuar().getPassword());
		BeanUtils.copyProperties(clienteSesion, clientEdit);
		clientEdit.getUsuar().setPassword(pass);
		this.clientService.saveClient(clientEdit);
		return "redirect:/clients/show";
	}
	
	@GetMapping(value = "/clients/delete/image")
	public String deleteImageClient(final ModelMap model) {

		Client clienteSesion = this.clientService.getCurrentClient();
		
		model.addAttribute("client", clienteSesion);
		return "clients/clientImageDelete";
	}

	@PostMapping(value = "/clients/delete/image")
	public String deleteImageClient(final ModelMap model, final HttpServletRequest request) {

		Client cliente = this.clientService.getCurrentClient();
		
			if(cliente.getImage() != null ) {
			
			Path directorio = Paths.get("src//main//resources//static//resources//imagenesSubidas");
			String rutaAbsoluta = directorio.toFile().getAbsolutePath();
			try {
				
				Path rutaAntigua = Paths.get(rutaAbsoluta+"//"+cliente.getImage());
				Files.delete(rutaAntigua);
				
				cliente.setImage(null);
				this.clientService.saveClient(cliente);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		
		

		return "redirect:/clients/show";
	}

	@GetMapping(value = "/clients/delete")
	public String deleteClient(final ModelMap model) {

		Client client = this.clientService.getCurrentClient();
		model.put("client", client);
		return "/clients/clientDelete";
	}

	@PostMapping(value = "/clients/delete")
	public String deleteClientForm(final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.getCurrentClient();

		List<FoodOffer> foodOffers = this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers = this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers = this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers = this.timeOfferService.findTimeOfferByUserId(client.getId());

		foodOffers.stream().forEach(f -> f.setStatus(StatusOffer.inactive));

		speedOffers.stream().forEach(s -> s.setStatus(StatusOffer.inactive));

		nuOffers.stream().forEach(n -> n.setStatus(StatusOffer.inactive));

		timeOffers.stream().forEach(t -> t.setStatus(StatusOffer.inactive));

		client.setAddress("Eliminado");
		client.setParking(false);
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

		try {
			request.logout();
		} catch (ServletException e) {

		}
		return "redirect:/login";

	}

}
