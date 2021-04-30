
package org.springframework.cheapy.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.Offer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfertaController {

	private final ClientService		clientService;

	private final FoodOfferService	foodOfferService;
	private final NuOfferService	nuOfferService;
	private final SpeedOfferService	speedOfferService;
	private final TimeOfferService	timeOfferService;


	public OfertaController(final FoodOfferService foodOfferService, final NuOfferService nuOfferService, final SpeedOfferService speedOfferService, final TimeOfferService timeOfferService, final ClientService clientService) {
		this.clientService = clientService;
		this.foodOfferService = foodOfferService;
		this.nuOfferService = nuOfferService;
		this.speedOfferService = speedOfferService;
		this.timeOfferService = timeOfferService;
	}

	@GetMapping("/offers")
	public String processFindForm(final Map<String, Object> model) {
		Pageable elements = PageRequest.of(0, 3);

		List<FoodOffer> foodOfferLs = this.foodOfferService.findActiveFoodOffer(elements);
		for(int i=0; i<foodOfferLs.size();i++) {
			FoodOffer fo= foodOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			foodOfferLs.set(i, fo);
		}
		List<NuOffer> nuOfferLs = this.nuOfferService.findActiveNuOffer(elements);
		for(int i=0; i<nuOfferLs.size();i++) {
			NuOffer fo= nuOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			nuOfferLs.set(i, fo);
		}
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findActiveSpeedOffer(elements);
		for(int i=0; i<speedOfferLs.size();i++) {
			SpeedOffer fo= speedOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			speedOfferLs.set(i, fo);
		}
		List<TimeOffer> timeOfferLs = this.timeOfferService.findActiveTimeOffer(elements);
		for(int i=0; i<timeOfferLs.size();i++) {
			TimeOffer fo= timeOfferLs.get(i);
			String aux=fo.getClient().getName().substring(0, 1).toUpperCase();
			fo.getClient().setName(aux+fo.getClient().getName().substring(1));
			
			timeOfferLs.set(i, fo);
		}

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);
		model.put("municipios", Municipio.values());
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersList";

	}

	@GetMapping("/offersByName/{page}")
    public String processFindFormByName(@PathVariable("page") final int page, final Map<String, Object> model, final String name) {
        Pageable elements = PageRequest.of(page, 2);
        Pageable nextPage = PageRequest.of(page+1, 2);

        List<Object[]> datos = ofertasPorNombre(name, elements);
        List<Object[]> datosNext = ofertasPorNombre(name, nextPage);

        Integer next = datosNext.size();
        model.put("nextPage", next);
        model.put("datos", datos);
        model.put("name", name);
        model.put("municipios", Municipio.values());
        model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return "offers/offersListNameSearch";

    }

	private List<Object[]> ofertasPorNombre(String name,Pageable pag){

    List<Object[]> datos = new ArrayList<Object[]>();

    for(Offer of:this.foodOfferService.findFoodOfferByClientName(name, pag)) {
        Object[] fo = {of, "food"};
        datos.add(fo);
    }

    for(Offer of:this.nuOfferService.findNuOfferByClientName(name, pag)) {
        Object[] nu = {of, "nu"};
        datos.add(nu);
    }

    for(Offer of:this.speedOfferService.findSpeedOfferByClientName(name, pag)) {
        Object[] sp = {of, "speed"};
        datos.add(sp);
    }

    for(Offer of:this.timeOfferService.findTimeOfferByClientName(name, pag)) {
        Object[] ti = {of, "time"};
        datos.add(ti);
        }
    return datos;
    }

	@GetMapping("/offersByFood/{page}")
    public String processFindFormByFood(@PathVariable("page") final int page, final Map<String, Object> model, final String name) {
        Pageable elements = PageRequest.of(page, 2);
        Pageable nextPage = PageRequest.of(page+1, 2);

        List<Object[]> datos = ofertasPorComida(name, elements);
        List<Object[]> datosNext = ofertasPorComida(name, nextPage);

        Integer next = datosNext.size();
        Integer now = datos.size();
        model.put("now", now);
        model.put("nextPage", next);
        model.put("datos", datos);
        model.put("name", name);
        model.put("municipios", Municipio.values());
        model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return "offers/offersListFoodSearch";

    }

    private List<Object[]> ofertasPorComida(String name,Pageable pag ){
        List<Object[]> datos = new ArrayList<Object[]>();

        for(Offer of:this.foodOfferService.findFoodOfferByClientFood(name, pag)) {
            Object[] fo = {of, "food"};
            datos.add(fo);
        }

        for(Offer of:this.nuOfferService.findNuOfferByClientFood(name, pag)) {
            Object[] nu = {of, "nu"};
            datos.add(nu);
        }

        for(Offer of:this.speedOfferService.findSpeedOfferByClientFood(name, pag)) {
            Object[] sp = {of, "speed"};
            datos.add(sp);
        }

        for(Offer of:this.timeOfferService.findTimeOfferByClientFood(name, pag)) {
            Object[] ti = {of, "time"};
            datos.add(ti);
        }
        return datos;
    }

    @GetMapping("/offersByPlace/{page}")
	public String processFindFormByPlace(@PathVariable("page") final int page, final Map<String, Object> model, final HttpServletRequest request) {
		if (request.getParameter("municipio").equals("") || request.getParameter("municipio") == null) {
			model.put("municipios", Municipio.values());
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			return "redirect:/offers/";
		}

		Municipio mun = Municipio.valueOf(request.getParameter("municipio"));
		Pageable elements = PageRequest.of(page, 2);
		Pageable nextPage = PageRequest.of(page+1, 2);
		
		List<Object[]> datos = ofertasPorMunicipio(mun,elements);
		List<Object[]> datosNext = ofertasPorMunicipio(mun,nextPage);
						
		Integer next = datosNext.size();
		Integer now = datos.size();
		model.put("mun", mun);
		model.put("now", now);
		model.put("nextPage", next);
		model.put("datos", datos);
		model.put("municipios", Municipio.values());
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListPlaceSearch";

	}
	
	private List<Object[]> ofertasPorMunicipio(Municipio mun,Pageable pag ){
		List<Object[]> datos = new ArrayList<Object[]>();
		
		for(Offer of:this.foodOfferService.findFoodOfferByClientPlace(mun, pag)) {
			Object[] fo = {of, "food"};
			datos.add(fo);
		}
		
		for(Offer of:this.nuOfferService.findNuOfferByClientPlace(mun, pag)) {
			Object[] nu = {of, "nu"};
			datos.add(nu);
		}
		
		for(Offer of:this.speedOfferService.findSpeedOfferByClientPlace(mun, pag)) {
			Object[] sp = {of, "speed"};
			datos.add(sp);
		}
		
		for(Offer of:this.timeOfferService.findTimeOfferByClientPlace(mun, pag)) {
			Object[] ti = {of, "time"};
			datos.add(ti);
		}
		return datos;
	}
	
	@GetMapping("/offersByDate/{page}")
    public String processFindFormByDate(@PathVariable("page") final int page, final Map<String, Object> model,
    		final String start) {
		LocalDateTime inic = LocalDateTime.parse(start,
		        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); 
		
		
        Pageable elements = PageRequest.of(page, 2);
        Pageable nextPage = PageRequest.of(page+1, 2);

        List<Object[]> datos = ofertasPorFecha(inic, elements);
        List<Object[]> datosNext = ofertasPorFecha(inic, nextPage);

        Integer next = datosNext.size();
        Integer now = datos.size();
        model.put("now", now);
        model.put("nextPage", next);
        model.put("datos", datos);
        model.put("municipios", Municipio.values());
        model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return "offers/offersListFoodSearch";

    }

    private List<Object[]> ofertasPorFecha(final LocalDateTime start, Pageable pag){
        List<Object[]> datos = new ArrayList<Object[]>();

        for(Offer of:this.foodOfferService.findFoodOfferByDate(start, pag)) {
            Object[] fo = {of, "food"};
            datos.add(fo);
        }

        for(Offer of:this.nuOfferService.findNuOfferByDate(start, pag)) {
            Object[] nu = {of, "nu"};
            datos.add(nu);
        }

        for(Offer of:this.speedOfferService.findSpeedOfferByDate(start, pag)) {
            Object[] sp = {of, "speed"};
            datos.add(sp);
        }

        for(Offer of:this.timeOfferService.findTimeOfferByDate(start, pag)) {
            Object[] ti = {of, "time"};
            datos.add(ti);
        }
        return datos;
    }


	@GetMapping("/myOffers")
	public String processMyOffersForm(final Map<String, Object> model) {

		int actual = this.clientService.getCurrentClient().getId();

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferActOclByUserId(actual);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferActOclByUserId(actual);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferActOclByUserId(actual);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferActOclByUserId(actual);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/myOffersList";
	}

	@GetMapping("/offersCreate")
	public String createOffers() {

		return "offers/offersCreate";
	}

	//	@GetMapping("/owners/{ownerId}/edit")
	//	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//		model.addAttribute(owner);
	//		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//	}
	//
	//	@PostMapping("/owners/{ownerId}/edit")
	//	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
	//			@PathVariable("ownerId") int ownerId) {
	//		if (result.hasErrors()) {
	//			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//		}
	//		else {
	//			owner.setId(ownerId);
	//			this.ownerService.saveOwner(owner);
	//			return "redirect:/owners/{ownerId}";
	//		}
	//	}
	//	@GetMapping("/owners/{ownerId}")
	//	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
	//		ModelAndView mav = new ModelAndView("owners/ownerDetails");
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//
	//		mav.addObject(owner);
	//		return mav;
	//	}

}
