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

import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.Authorities;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.service.AuthoritiesService;
import org.springframework.cheapy.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class WelcomeController {

	private final ClientService		clientService;
	private final AuthoritiesService authoritiesService;
	
	public WelcomeController( final ClientService clientService, final AuthoritiesService authoritiesService) {
		this.clientService = clientService;
		this.authoritiesService = authoritiesService;
	}
	
	@GetMapping("/")
	public String welcome( final HttpServletRequest request) throws ServletException {
		Client client = this.clientService.getCurrentClient();
		
		//Authorities auth=this.authoritiesService.findAuthoritiyByUsername(username);
		if(client!=null) {
			String username=client.getUsuar().getUsername();
			LocalDate exp=client.getExpiration();
			Authorities auth=this.authoritiesService.findAuthoritiyByUsername(username);
			if(exp.isBefore(LocalDate.now())&&auth.getAuthority().equals("client")) {
				this.authoritiesService.saveAuthorities(username, "notsubscribed");
				request.logout();
				return "redirect:/login";
			}
		}
		return "welcome";
	}
	
	@GetMapping("/termAndCondition")
		public String termAndConditions(){
		return "termAndConditions";
	}

	
	
}
