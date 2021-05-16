package org.springframework.cheapy.web;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Order;
import org.springframework.cheapy.service.AuthoritiesService;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.PaypalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	public static final String SUCCESS_URL_MONTH = "pay/successMonth";
	public static final String SUCCESS_URL_YEAR = "pay/successYear";
	public static final String SUCCESS_URL = "pay/successPayment";
	public static final String CANCEL_URL = "pay/cancel";
	
	@GetMapping("/pay/month")
	public String formPaymentPremiumMonth(final Map<String, Object> model) {
		Order order = new Order();
		order.setIntent("sale");
		order.setCurrency("EUR");
		order.setMethod("paypal");
		order.setPrice(30);
		order.setDescription("Suscripción a Cheapy para bares o restaurantes de 1 mes.");
		
		LocalDate expira = this.clientservice.getCurrentClient().getExpiration();
		
		model.put("order", order);
		model.put("tipo", "month");
		model.put("expira", expira);
		
		return "pay/createPaymentForm";
	}
	
	@GetMapping("/pay/year")
	public String formPaymentPremiumYear(final Map<String, Object> model) {
		Order order = new Order();
		order.setIntent("sale");
		order.setCurrency("EUR");
		order.setMethod("paypal");
		order.setPrice(320);
		order.setDescription("Suscripción a Cheapy para bares o restaurantes de 1 año.");

		LocalDate expira = this.clientservice.getCurrentClient().getExpiration();
		
		model.put("order", order);
		model.put("tipo", "year");
		model.put("expira", expira);
		
		return "pay/createPaymentForm";
	}

	@PostMapping("/pay/month")
	public String paymentMonth(@ModelAttribute("order") Order order, HttpServletRequest request) {
		try {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			
			Payment payment = payPalService.createPayment(Double.valueOf(order.getPrice()), order.getCurrency(),
					order.getMethod(), order.getIntent(), order.getDescription(), basePath + CANCEL_URL,
					basePath + SUCCESS_URL_MONTH);
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
	
	@PostMapping("/pay/year")
	public String paymentYear(@ModelAttribute("order") Order order, HttpServletRequest request) {
		try {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			 
			Payment payment = payPalService.createPayment(Double.valueOf(order.getPrice()), order.getCurrency(),
					order.getMethod(), order.getIntent(), order.getDescription(), basePath + CANCEL_URL,
					basePath + SUCCESS_URL_YEAR);
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

	@GetMapping(value = SUCCESS_URL_MONTH)
	public String successPayMonth(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, final HttpServletRequest request) throws ServletException {
		try {
			Payment payment = payPalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				Client client = this.clientservice.getCurrentClient();
				String username=client.getUsuar().getUsername();
				this.authoritiesService.saveAuthorities(username, "client");
				LocalDate expiration = client.getExpiration().isAfter(LocalDate.now()) ? client.getExpiration() : LocalDate.now();
				client.setExpiration(expiration.plusMonths(1));
				this.clientservice.saveClient(client);
				return SUCCESS_URL;
			}
		} catch (PayPalRESTException e) {
		}
		request.logout();
		return "redirect:/login";
	}
	
	@GetMapping(value = SUCCESS_URL_YEAR)
	public String successPayYear(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, final HttpServletRequest request) throws ServletException {
		try {
			Payment payment = payPalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				Client client = this.clientservice.getCurrentClient();
				String username=client.getUsuar().getUsername();
				this.authoritiesService.saveAuthorities(username, "client");
				LocalDate expiration = client.getExpiration().isAfter(LocalDate.now()) ? client.getExpiration() : LocalDate.now();
				client.setExpiration(expiration.plusYears(1));
				this.clientservice.saveClient(client);
				return SUCCESS_URL;
			}
		} catch (PayPalRESTException e) {
		}
		request.logout();
		return "redirect:/login";
	}
	
	
}
