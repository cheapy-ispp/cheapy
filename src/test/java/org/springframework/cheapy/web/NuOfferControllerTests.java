package org.springframework.cheapy.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = NuOfferController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class NuOfferControllerTest {

	private static final int TEST_CLIENT_ID = 1;
	private static final int TEST_CLIENT_2_ID = 2;
	private static final int TEST_NUOFFER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NuOfferService nuOfferService;
	
	@MockBean
	private ClientService clientService;

	private NuOffer nu1;
	private Client clientTest;
	private Client clientTest2;
	private List<NuOffer> nuOfferLs;

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
		client1.setParking(true);
		client1.setInit(LocalTime.of(01, 00));
		client1.setFinish(LocalTime.of(01, 01));
		client1.setTelephone("123456789");
		client1.setDescription("client1");
		client1.setFood("client1");
		client1.setPreguntaSegura1("client1");
		client1.setPreguntaSegura2("client1");
		client1.setUsuar(user1);
		clientTest = client1;
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client1);
		
		User user2 = new User();
		user2.setUsername("user2");
		user2.setPassword("user2");
		Client client2 = new Client();
		client2.setId(TEST_CLIENT_2_ID);
		client2.setName("client2");
		client2.setEmail("client2");
		client2.setAddress("client2");
		client2.setParking(true);
		client2.setInit(LocalTime.of(01, 00));
		client2.setFinish(LocalTime.of(01, 01));
		client2.setTelephone("123456789");
		client2.setDescription("client2");
		client2.setFood("client2");
		client2.setPreguntaSegura1("client2");
		client2.setPreguntaSegura2("client2");
		client2.setUsuar(user2);
		this.clientTest2 = client2;
		
		NuOffer nu1test = new NuOffer();
		nu1test.setId(TEST_NUOFFER_ID);
		nu1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		nu1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		nu1test.setGold(15);
		nu1test.setDiscountGold(15);
		nu1test.setSilver(10);
		nu1test.setDiscountSilver(10);
		nu1test.setBronze(5);
		nu1test.setDiscountBronze(5);
		nu1test.setClient(client1);
		nu1test.setStatus(StatusOffer.hidden);
		nu1test.setCode("");
		this.nu1 = nu1test;
		BDDMockito.given(this.nuOfferService.findNuOfferById(TEST_NUOFFER_ID)).willReturn(this.nu1);
		
		List<NuOffer> nuOfferLsTest = new ArrayList<>();
		nuOfferLsTest.add(nu1test);
		this.nuOfferLs = nuOfferLsTest;
		BDDMockito.given(this.nuOfferService.findActiveNuOffer(PageRequest.of(0, 5))).willReturn(this.nuOfferLs);
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/offers/nuOfferList/{page}", 0))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attribute("municipios", Municipio.values()))
				.andExpect(model().attributeExists("nuOfferLs"))
				.andExpect(model().attribute("nuOfferLs", nuOfferLs))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/nu/nuOffersList"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowActive() throws Exception {
		mockMvc.perform(get("/offers/nu/{nuOfferId}", TEST_NUOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("nuOffer"))
				.andExpect(model().attribute("nuOffer", nu1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/nu/nuOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenSuccess() throws Exception {
		
		nu1.setStatus(StatusOffer.hidden);
		
		mockMvc.perform(get("/offers/nu/{nuOfferId}", TEST_NUOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("nuOffer"))
				.andExpect(model().attribute("nuOffer", nu1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/nu/nuOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenHasErrors() throws Exception {
		
		nu1.setStatus(StatusOffer.hidden);
		nu1.setClient(clientTest2);
		
		mockMvc.perform(get("/offers/nu/{nuOfferId}", TEST_NUOFFER_ID))
				.andExpect(view().name("error"));
	}
		
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/offers/nu/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("nuOffer"))
				.andExpect(view().name("offers/nu/createOrUpdateNuOfferForm"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/offers/nu/new")
				.with(csrf())
				.param("start", "2021-12-23T12:30")
				.param("end", "2022-12-23T12:30")
				.param("gold", "15")
				.param("discountGold", "15")
				.param("silver", "10")
				.param("discountSilver", "10")
				.param("bronze", "5")
				.param("discountBronze", "5"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/offers/nu/new")
					.with(csrf())
					.param("start", "2020-12-23T12:30")
					.param("end", "2020-12-22T12:30")
					.param("gold", "gold")
					.param("discountGold", "")
					.param("silver", "")
					.param("discountSilver", "")
					.param("bronze", "")
					.param("discountBronze", ""))
				.andExpect(model().attributeHasErrors("nuOffer"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "start"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "end"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "gold"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "discountGold"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "silver"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "discountSilver"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "bronze"))
				.andExpect(model().attributeHasFieldErrors("nuOffer", "discountBronze"))
				.andExpect(view().name("offers/nu/createOrUpdateNuOfferForm"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateSuccess() throws Exception {
		mockMvc.perform(get("/offers/nu/{nuOfferId}/activate", TEST_NUOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/nu/"+TEST_NUOFFER_ID));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateHasErrors() throws Exception {
		mockMvc.perform(get("/offers/nu/{nuOfferId}/activate", TEST_NUOFFER_ID+1))
				.andExpect(view().name("exception"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitSuccess() throws Exception {
		this.mockMvc.perform(get("/offers/nu/{nuOfferId}/disable", TEST_NUOFFER_ID))
					.andExpect(status().isOk())
					.andExpect(view().name("offers/nu/nuOffersDisable"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
    @Test
    void testDisableFormSuccess() throws Exception {
        this.mockMvc.perform(post("/offers/nu/{nuOfferId}/disable", TEST_NUOFFER_ID)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/myOffers"));
    }

	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        nu1.setClient(c);
		mockMvc.perform(get("/offers/nu/{nuOfferId}/disable", TEST_NUOFFER_ID))
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableFormHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        nu1.setClient(c);
		mockMvc.perform(post("/offers/nu/{nuOfferId}/disable", TEST_NUOFFER_ID)
				.with(csrf()))
				.andExpect(view().name("error"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateNuOfferSuccess() throws Exception {
		mockMvc.perform(get("/offers/nu/{nuOfferId}/edit",TEST_NUOFFER_ID)
					.with(csrf()))
				.andExpect(model().attributeExists("nuOffer"))
				.andExpect(model().attribute("nuOffer", hasProperty("start", is(LocalDateTime.of(2021, 12, 23, 12, 30)))))
				.andExpect(model().attribute("nuOffer", hasProperty("end", is(LocalDateTime.of(2022, 12, 23, 12, 30)))))
				.andExpect(model().attribute("nuOffer", hasProperty("gold", is(15))))
				.andExpect(model().attribute("nuOffer", hasProperty("discountGold", is(15))))
				.andExpect(model().attribute("nuOffer", hasProperty("silver", is(10))))
				.andExpect(model().attribute("nuOffer", hasProperty("discountSilver", is(10))))
				.andExpect(model().attribute("nuOffer", hasProperty("bronze", is(5))))
				.andExpect(model().attribute("nuOffer", hasProperty("discountBronze", is(5))))
				.andExpect(model().attribute("nuOffer", hasProperty("client", is(clientTest))))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/nu/createOrUpdateNuOfferForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateNuOfferError() throws Exception {
		nu1.setStatus(StatusOffer.inactive);
		mockMvc.perform(get("/offers/nu/{nuOfferId}/edit",TEST_NUOFFER_ID)
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateNuOfferSuccess() throws Exception {
		mockMvc.perform(post("/offers/nu/{nuOfferId}/edit",TEST_NUOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("status", "hidden")
					.param("gold", "15")
					.param("discountGold", "15")
					.param("silver", "10")
					.param("discountSilver", "10")
					.param("bronze", "5")
					.param("discountBronze", "5")
					.param("code", "")
					.sessionAttr("idNu", TEST_NUOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/nu/"+TEST_NUOFFER_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateNuOfferError() throws Exception {
		mockMvc.perform(post("/offers/nu/{nuOfferId}/edit",TEST_NUOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2021-12-22T12:30")
					.param("status", "hidden")
					.param("gold", "15")
					.param("discountGold", "15")
					.param("silver", "10")
					.param("discountSilver", "10")
					.param("bronze", "5")
					.param("discountBronze", "5")
					.param("code", "")
					.sessionAttr("idNu", TEST_NUOFFER_ID))
				.andExpect(model().attributeExists("nuOffer"))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/nu/createOrUpdateNuOfferForm"));
	}
	
}
