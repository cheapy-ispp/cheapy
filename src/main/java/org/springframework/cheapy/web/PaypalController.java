package org.springframework.cheapy.web;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Authorities;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Order;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.AuthoritiesService;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.PaypalService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

	@Autowired
	private PaypalService payPalService;
	
	@Autowired
	private ClientService clientservice;

	@Autowired
	private AuthoritiesService authoritiesService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	@GetMapping("/pay")
	public String formPaymentPremium(final Map<String, Object> model) {
		Order order = new Order();
		order.setIntent("sale");
		order.setCurrency("EUR");
		order.setMethod("paypal");
		order.setPrice(30);
		order.setDescription("Suscripción a Cheapy para bares o restaurantes de 1 mes.");
		
		Client client = this.clientservice.getCurrentClient();
		
		if (true) {
			//Checkear si el cliente esta suscrito
		} else {
			return "redirect:/";
		}

		model.put("order", order);
		return "pay/createPaymentForm";
	}

	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") Order order) {
		try {
			Payment payment = payPalService.createPayment(Double.valueOf(order.getPrice()), order.getCurrency(),
					order.getMethod(), order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {
					
					return "redirect:" + link.getHref();
				}
			}
		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "pay/cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, final HttpServletRequest request) throws ServletException {
		try {
			Payment payment = payPalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				Client client = this.clientservice.getCurrentClient();
				String username=client.getUsuar().getUsername();
				this.authoritiesService.saveAuthorities(username, "client");
				client.setExpiration(LocalDate.now().plusMonths(1));
				this.clientservice.saveClient(client);
				//return SUCCESS_URL;
			}
		} catch (PayPalRESTException e) {
		}
		request.logout();
		return "redirect:/login";
	}
}
