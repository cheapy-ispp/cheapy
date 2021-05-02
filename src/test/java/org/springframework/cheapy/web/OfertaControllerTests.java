package org.springframework.cheapy.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = OfertaController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class OfertaControllerTest {

	private static final int TEST_CLIENT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private  ClientService		clientService;
	
	@MockBean
	private FoodOfferService foodOfferService;
	
	@MockBean
	private NuOfferService nuOfferService;
	
	@MockBean
	private SpeedOfferService	speedOfferService;
	
	@MockBean
	private TimeOfferService	timeOfferService;

	@BeforeEach
	void setup() {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("user1");
		Client client1 = new Client();
		client1.setId(TEST_CLIENT_ID);
		client1.setName("client1");
		client1.setEmail("client1");
		client1.setAddress("client1");
		client1.setInit(LocalTime.of(01, 00));
		client1.setFinish(LocalTime.of(01, 01));
		client1.setTelephone("123456789");
		client1.setDescription("client1");
		client1.setFood("client1");
		client1.setUsuar(user1);
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client1);
		BDDMockito.given(this.foodOfferService.findFoodOfferActOclByUserId(1)).willReturn(new ArrayList<FoodOffer>());
		BDDMockito.given(this.nuOfferService.findNuOfferActOclByUserId(1)).willReturn(new ArrayList<NuOffer>());
		BDDMockito.given(this.speedOfferService.findSpeedOfferActOclByUserId(1)).willReturn(new ArrayList<SpeedOffer>());
		BDDMockito.given(this.timeOfferService.findTimeOfferActOclByUserId(1)).willReturn(new ArrayList<TimeOffer>());
		
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationFormByName() throws Exception {
		mockMvc.perform(get("/offersByName/{page}?name=bar",0).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("datos"))
				.andExpect(view().name("offers/offersListNameSearch"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationFormByFood() throws Exception {
		mockMvc.perform(get("/offersByFood/{page}",0).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("datos"))
				.andExpect(view().name("offers/offersListFoodSearch"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationFormByPlace() throws Exception {
		mockMvc.perform(get("/offersByPlace/{page}?municipio={mun}",0,Municipio.Sevilla).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/offersListPlaceSearch"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationFormByDate() throws Exception {
		mockMvc.perform(get("/offersByDate/{page}?start={date}",0,"2021-08-16T14:00").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/offersListFoodSearch"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void processFindForm() throws Exception {
		mockMvc.perform(get("/offers").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("foodOfferLs"))
				.andExpect(model().attributeExists("nuOfferLs"))
				.andExpect(model().attributeExists("speedOfferLs"))
				.andExpect(model().attributeExists("timeOfferLs"))
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attributeExists("localDateTimeFormat"))
				.andExpect(view().name("offers/offersList"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testMyOffers() throws Exception {
		mockMvc.perform(get("/myOffers").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/myOffersList"));
	}
}