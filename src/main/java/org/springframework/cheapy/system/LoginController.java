/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cheapy.system;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.cheapy.utils.MD5;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class LoginController {

	private final ClientService		clientService;
	
	private final UsuarioService	usuarioService;
	
	public LoginController(final ClientService clientService, final UsuarioService usuarioService) {
			this.clientService = clientService;
			this.usuarioService = usuarioService;
	}
	
	@GetMapping("/login")
	public String login() {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}
	
	@GetMapping("/contrasenaOlvidada")
	public String contrasenaOlvidada(final Map<String, Object> model, final HttpServletRequest request) {
		
		String username = "";
		model.put("username", username);
		
		String preguntaSegura1 = ""; 
		model.put("preguntaSegura1", preguntaSegura1);
		
		String preguntaSegura2 = ""; 
		model.put("preguntaSegura2", preguntaSegura2);
		
		String nuevaContrasena = ""; 
		model.put("nuevaContrasena", nuevaContrasena);
		
		return "contrasenaOlvidada";
	}
	
	@PostMapping("/contrasenaOlvidada")
	public String contrasenaOlvidadaForm(final String username, final String preguntaSegura1, final String preguntaSegura2, 
			final String nuevaContrasena, final ModelMap model, final HttpServletRequest request) throws ServletException {
		
		Client client = this.clientService.findByUsername(username);
		Usuario usuario = this.usuarioService.findByUsername(username);
		
		if(client != null) {
			
			if(client.getPreguntaSegura1().equals(preguntaSegura1) && client.getPreguntaSegura2().equals(preguntaSegura2)) {
				client.getUsuar().setPassword(MD5.md5(nuevaContrasena));
				this.clientService.saveClient(client);
				return "redirect:/";
			} else {
				
				return "error";
			}
			
		} else if (usuario != null) {
			
			if(usuario.getPreguntaSegura1().equals(preguntaSegura1) && usuario.getPreguntaSegura2().equals(preguntaSegura2)) {
				usuario.getUsuar().setPassword(MD5.md5(nuevaContrasena));
				this.usuarioService.saveUsuario(usuario);
				return "redirect:/";
			} else {
				
				return "error";
			}
			
		} else {
			 return "error";
		}
		
	}
}
