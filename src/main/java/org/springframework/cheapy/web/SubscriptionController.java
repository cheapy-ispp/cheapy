package org.springframework.cheapy.web;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.repository.ClientRepository;
import org.springframework.cheapy.repository.UserRepository;
import org.springframework.cheapy.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;

@RestController
public class SubscriptionController {
	
	@Autowired
	private StripeService stripeService;
	
	
	@Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @RequestMapping("/pago")
    public String home(Model model) {
        model.addAttribute("amount", 50 * 100); // In cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "prueba";
    }
    
    @RequestMapping(value = "/pago/charge", method = RequestMethod.POST)
    public String chargeCard(HttpServletRequest request) throws Exception {
        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token, amount);
        return "result";
    }
	
//	@Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//	@Value("${STRIPE_API_KEY:sk_test_51Iid45EJXGO6ge55ADsfdCgj1uPg4HkwU36erKEHJ1vdZmwVYwpMnJDfNnB0zBoe2itRHJEdJocvxo3AyYficv1t00PPoPAClr}")
//	private String stripeApiKey;
//	
//	private static ObjectMapper objectMapper = new ObjectMapper();
//	
//	@Value("${STRIPE_WEBHOOK_SECRET:whsec_mac5W8VryHFGV4JWO735ztnmgzZsRd3z}")
//    private String stripeWebhookSecret;
//
//	// @PreAuthorize("hasRole('ROLE_CLIENT')")
//	    @PostMapping("/create_session")
//	    public Map<String, Object> createSession(@RequestBody() String body) {
//	        Stripe.apiKey = stripeApiKey;
//
//	        try {
//	        	ZonedDateTime actualDate = ZonedDateTime.now();
//				actualDate = actualDate.withZoneSameInstant(ZoneId.of("Europe/Madrid"));
//	            JsonNode jsonNode = objectMapper.readTree(body);
//	            Integer quantity = objectMapper.readTree(jsonNode.get("unit_amount_decimal").toString()).asInt();
//	            String description = objectMapper.readTree(jsonNode.get("description").toString()).asText();
//	            String unidadIntervTiempo = objectMapper.readTree(jsonNode.get("interval").toString()).asText();//month
//	            String cantidadTiempo = objectMapper.readTree(jsonNode.get("interval_count").toString()).asText();//1 (month)
//
//	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	            
//	            User user = userRepository.findByEmail(authentication.getPrincipal().toString());
//
//	            String idUser = user.getUsername();
//	            LocalDateTime bannedUntil = user.getBannedUntil();
//	            Trip trip = tripRepository.findById(new ObjectId(idTrip));
//	            
//	            if(bannedUntil != null) {
//	            	if(bannedUntil.isAfter(actualDate.toLocalDateTime())) {
//	            		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario esta baneado, no puede realizar esta accion");
//	            	}
//	            }
//
////	            if (!(trip.getPlaces() >= quantity)) {
////	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El viaje no tiene tantas plazas");
////	            }
////
////
////	            if(trip.getDriver().getId().equals(idUser)){
////	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puedes reservar tu propio viaje");
////	            }
//
//
//	            List<Object> paymentMethodTypes = new ArrayList<>();
//	            paymentMethodTypes.add("card");
//	            List<Object> lineItems = new ArrayList<>();
//	            Map<String, Object> lineItem1 = new HashMap<>();
//	            lineItem1.put("amount", trip.getPrice());
//	            lineItem1.put("quantity", quantity);
//	            lineItem1.put("currency", "EUR");
//	            lineItem1.put("name", description);
//	            lineItems.add(lineItem1);
//	            Map<String, Object> params = new HashMap<>();
//	            params.put("success_url", "https://gotacar.es/payment-success");
//	            params.put("cancel_url", "https://gotacar.es/payment-failed");
//	            params.put("payment_method_types", paymentMethodTypes);
//	            params.put("line_items", lineItems);
//	            params.put("mode", "payment");
//	            params.put("locale", "es");
//	            Map<String, Object> metadata = new HashMap<>();
//	            metadata.put("userId", idUser);
//	            metadata.put("tripId", idTrip);
//	            metadata.put("quantity", quantity);
//	            metadata.put("amount", trip.getPrice());
//	            metadata.put("currency", "EUR");
//	            metadata.put("name", description);
//	            params.put("metadata", metadata);
//
//	            Map<String, Object> response = new LinkedHashMap<>();
//	            response.put("session_id", Session.create(params).getId());
//
//	            return response;
//
//	        } catch (Exception e) {
//	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
//	        }
//	    }

}
