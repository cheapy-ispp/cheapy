package org.springframework.cheapy.system;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Authorities;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.AuthoritiesService;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.UserService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.cheapy.utils.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SingUpController {

	//private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	@Autowired
	private final ClientService clientService;
	@Autowired
	private final UserService userService;
	@Autowired
	private final UsuarioService usuarioService;
	@Autowired
	private final AuthoritiesService authoritiesService;

	public SingUpController(final ClientService clientService, UserService userService, AuthoritiesService authoritiesService,
			UsuarioService usuarioService) {
		this.clientService = clientService;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
		this.usuarioService = usuarioService;

	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	private boolean checkTimes(final Client client) {
		boolean res = false;
		if(client.getFinish()==null || client.getInit()==null || client.getFinish().isAfter(client.getInit())) {
			res = true;
		}
		return res;
	}

	@GetMapping("/sign-up-user/new")
	public String singUpUserForm(Map<String, Object> model) {
		Map<Object, String> municipios = new HashMap<Object, String>();
		
		Municipio[] a = Municipio.values();
		int cont = 0;
		for (Municipio i : Municipio.values()) {
		    municipios.put(a[cont], i.toString());
		    cont++;
		}
		
		Usuario usuario = new Usuario();
		
		User user=new User();
		
		usuario.setUsuar(user);
		model.put("usuario", usuario);
		model.put("municipios", municipios);
		//model.put("user", user);
		return "singup/singUpUser";
	}

	@PostMapping("/sign-up-user/new")
	public String singUpUserForm(@Valid Usuario usuario, BindingResult result, Map<String, Object> model) {
		Authorities auth=new Authorities();
		User user= usuario.getUsuar();
		user.setEnabled(true);
		
		usuario.setUsuar(user);
		auth.setUsername(user.getUsername());
		auth.setAuthority("usuario");
		Boolean duplicate=this.userService.duplicateUsername(usuario.getUsuar().getUsername());
		if(duplicate) {
			result.rejectValue("usuar.username","" ,"El nombre de usuario ya esta registrado");
		}
		if (result.hasErrors()) {
			Map<Object, String> municipios = new HashMap<Object, String>();
			Municipio[] a = Municipio.values();
			int cont = 0;
			for (Municipio i : Municipio.values()) {
			    municipios.put(a[cont], i.toString());
			    cont++;
			}
			model.put("municipios", municipios);
			
			if(usuario.getUsuar().getPassword().equals("")) {
				result.rejectValue("usuar.password","" ,"La contraseña no puede estar vacía");
			}
			if(usuario.getUsuar().getUsername().equals("")) {
				result.rejectValue("usuar.username","" ,"El nombre de usuario no puede estar vacío");
			}
			return "singup/singUpUser";
		 }else if(usuario.getUsuar().getPassword().equals("")||usuario.getUsuar().getUsername().equals("")) {
			 model.put("municipio", Municipio.values());
			 if(usuario.getUsuar().getPassword().equals("")) {
					result.rejectValue("usuar.password","" ,"La contraseña no puede estar vacía");
				}
				if(usuario.getUsuar().getUsername().equals("")) {
					result.rejectValue("usuar.username","" ,"El nombre de usuario no puede estar vacío");
				}
				return "singup/singUpUser";
		 }else {
			
			//auth.setId(1);
			//this.authoritiesService.saveAuthorities(auth);
			usuario.getUsuar().setPassword(MD5.md5(usuario.getUsuar().getPassword())); //MD5 a la contraseña para que no circule en claro por la red
			this.usuarioService.saveUsuario(usuario);
			this.userService.saveUser(user);
			this.authoritiesService.saveAuthorities(usuario.getUsuar().getUsername(), "usuario");
			
			return "redirect:/";
		}
	}

	@GetMapping("/sign-up-client/new")
	public String singUpClientForm(Map<String, Object> model) {
		Map<Object, String> municipios = new HashMap<Object, String>();
		
		Municipio[] a = Municipio.values();
		int cont = 0;
		for (Municipio i : Municipio.values()) {
		    municipios.put(a[cont], i.toString());
		    cont++;
		}
		
		Client cliente = new Client();
		
		User user=new User();
		
		cliente.setUsuar(user);
		LocalDate ayer= LocalDate.now().minusDays(2);
		cliente.setExpiration(ayer);
		model.put("municipios", municipios);
		model.put("cliente", cliente);
		//model.put("user", user);
		return "singup/singUpClient";
	}

	@PostMapping("/sign-up-client/new")
	public String singUpClientForm(@ModelAttribute("cliente") @Valid Client cliente, BindingResult result,  Map<String, Object> model) {
		Authorities auth=new Authorities();
		User user= cliente.getUsuar();
		user.setEnabled(true);
		cliente.setUsuar(user);
		auth.setUsername(user.getUsername());
		auth.setAuthority("client");
		LocalDate ayer= LocalDate.now().minusDays(2);
		cliente.setExpiration(ayer);
		if(!this.checkTimes(cliente)) {
			result.rejectValue("finish","" ,"La hora de cierre debe ser posterior a la hora de apertura");
			
		}
		Boolean duplicate=this.userService.duplicateUsername(cliente.getUsuar().getUsername());
		if(duplicate) {
			result.rejectValue("usuar.username","" ,"El nombre de usuario ya esta registrado");
		}
		if (result.hasErrors()) {
			Map<Object, String> municipios = new HashMap<Object, String>();
			Municipio[] a = Municipio.values();
			int cont = 0;
			for (Municipio i : Municipio.values()) {
			    municipios.put(a[cont], i.toString());
			    cont++;
			}
			model.put("municipios", municipios);
			
			
			model.put("cliente", cliente);
			if(cliente.getUsuar().getPassword().equals("")) {
				result.rejectValue("usuar.password","" ,"La contraseña no puede estar vacía");
			}
			if(cliente.getUsuar().getUsername().equals("")) {
				result.rejectValue("usuar.username","" ,"El nombre de usuario no puede estar vacío");
			}
			return "singup/singUpClient";
		}else if(cliente.getUsuar().getPassword().equals("")||cliente.getUsuar().getUsername().equals("")) {
			Map<Object, String> municipios = new HashMap<Object, String>();
			Municipio[] a = Municipio.values();
			int cont = 0;
			for (Municipio i : Municipio.values()) {
			    municipios.put(a[cont], i.toString());
			    cont++;
			}
			municipios.put("null", "Seleccione uno de los municipios");
			model.put("municipios", municipios);
			model.put("cliente", cliente); 
			 if(cliente.getUsuar().getPassword().equals("")) {
					result.rejectValue("usuar.password","" ,"La contraseña no puede estar vacía");
			}
			if(cliente.getUsuar().getUsername().equals("")) {
					result.rejectValue("usuar.username","" ,"El nombre de usuario no puede estar vacío");
			}
				return "singup/singUpClient";
		 }else {
			cliente.getUsuar().setPassword(MD5.md5(cliente.getUsuar().getPassword())); //MD5 a la contraseña para que no circule en claro por la red
			this.clientService.saveClient(cliente);
			this.userService.saveUser(user);
			this.authoritiesService.saveAuthorities(cliente.getUsuar().getUsername(), "notsubscribed");
			
			
			return "redirect:/";
		}
	}
	
}
