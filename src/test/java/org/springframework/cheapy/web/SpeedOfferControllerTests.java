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
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = SpeedOfferController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class SpeedOfferControllerTest {

	private static final int TEST_CLIENT_ID = 1;
	private static final int TEST_CLIENT_2_ID = 2;
	private static final int TEST_SPEEDOFFER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SpeedOfferService speedOfferService;
	
	@MockBean
	private ClientService clientService;

	private SpeedOffer sp1;
	private Client clientTest;
	private Client clientTest2;
	private List<SpeedOffer> speedOfferLs;

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
		
		SpeedOffer sp1test = new SpeedOffer();
		sp1test.setId(TEST_SPEEDOFFER_ID);
		sp1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		sp1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		sp1test.setGold(LocalTime.of(00,05,30 ));
		sp1test.setDiscountGold(15);
		sp1test.setSilver(LocalTime.of(00,10,30 ));
		sp1test.setDiscountSilver(10);
		sp1test.setBronze(LocalTime.of(00,15,30 ));
		sp1test.setDiscountBronze(5);
		sp1test.setClient(client1);
		sp1test.setStatus(StatusOffer.hidden);
		sp1test.setCode("");
		this.sp1 = sp1test;
		BDDMockito.given(this.speedOfferService.findSpeedOfferById(TEST_SPEEDOFFER_ID)).willReturn(this.sp1);
		
		List<SpeedOffer> speedOfferLsTest = new ArrayList<>();
		speedOfferLsTest.add(sp1test);
		this.speedOfferLs = speedOfferLsTest;
		BDDMockito.given(this.speedOfferService.findActiveSpeedOffer(PageRequest.of(0, 5))).willReturn(this.speedOfferLs);
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/offers/speedOfferList/{page}", 0))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attribute("municipios", Municipio.values()))
				.andExpect(model().attributeExists("speedOfferLs"))
				.andExpect(model().attribute("speedOfferLs", speedOfferLs))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/speed/speedOffersList"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowActive() throws Exception {
		mockMvc.perform(get("/offers/speed/{speedOfferId}", TEST_SPEEDOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("speedOffer"))
				.andExpect(model().attribute("speedOffer", sp1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/speed/speedOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenSuccess() throws Exception {
		
		sp1.setStatus(StatusOffer.hidden);
		
		mockMvc.perform(get("/offers/speed/{speedOfferId}", TEST_SPEEDOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("speedOffer"))
				.andExpect(model().attribute("speedOffer", sp1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/speed/speedOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenHasErrors() throws Exception {
		
		sp1.setStatus(StatusOffer.hidden);
		sp1.setClient(clientTest2);
		
		mockMvc.perform(get("/offers/speed/{speedOfferId}", TEST_SPEEDOFFER_ID))
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/offers/speed/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("speedOffer"))
				.andExpect(view().name("offers/speed/createOrUpdateSpeedOfferForm"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/offers/speed/new")
					.with(csrf())
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("gold", "00:05:30")
					.param("discountGold", "15")
					.param("silver", "00:10:30")
					.param("discountSilver", "10")
					.param("bronze", "00:15:30")
					.param("discountBronze", "5"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/offers/speed/new")
					.with(csrf())
					.param("start", "2020-12-23T12:30")
					.param("end", "2020-12-22T12:30")
					.param("gold", "gold")
					.param("discountGold", "")
					.param("silver", "")
					.param("discountSilver", "")
					.param("bronze", "")
					.param("discountBronze", ""))
				.andExpect(model().attributeHasErrors("speedOffer"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "start"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "end"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "gold"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "discountGold"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "silver"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "discountSilver"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "bronze"))
				.andExpect(model().attributeHasFieldErrors("speedOffer", "discountBronze"))
				.andExpect(view().name("offers/speed/createOrUpdateSpeedOfferForm"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateSuccess() throws Exception {
		mockMvc.perform(get("/offers/speed/{speedOfferId}/activate", TEST_SPEEDOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/speed/"+TEST_SPEEDOFFER_ID));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateHasErrors() throws Exception {
		mockMvc.perform(get("/offers/speed/{speedOfferId}/activate", TEST_SPEEDOFFER_ID+1))
				.andExpect(view().name("exception"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitSuccess() throws Exception {
		this.mockMvc.perform(get("/offers/speed/{speedOfferId}/disable", TEST_SPEEDOFFER_ID))
					.andExpect(status().isOk())
					.andExpect(view().name("offers/speed/speedOffersDisable"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
    @Test
    void testDisableFormSuccess() throws Exception {
        this.mockMvc.perform(post("/offers/speed/{speedOfferId}/disable", TEST_SPEEDOFFER_ID)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/myOffers"));
  }

	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        sp1.setClient(c);
		mockMvc.perform(get("/offers/speed/{speedOfferId}/disable", TEST_SPEEDOFFER_ID))
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableFormHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        sp1.setClient(c);
		mockMvc.perform(post("/offers/speed/{speedOfferId}/disable", TEST_SPEEDOFFER_ID)
				.with(csrf()))
				.andExpect(view().name("error"));
  }

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateSpeedOfferSuccess() throws Exception {
		mockMvc.perform(get("/offers/speed/{speedOfferId}/edit",TEST_SPEEDOFFER_ID)
					.with(csrf()))
				.andExpect(model().attributeExists("speedOffer"))
				.andExpect(model().attribute("speedOffer", hasProperty("start", is(LocalDateTime.of(2021, 12, 23, 12, 30)))))
				.andExpect(model().attribute("speedOffer", hasProperty("end", is(LocalDateTime.of(2022, 12, 23, 12, 30)))))
				.andExpect(model().attribute("speedOffer", hasProperty("gold", is(LocalTime.of(00,05,30 )))))
				.andExpect(model().attribute("speedOffer", hasProperty("discountGold", is(15))))
				.andExpect(model().attribute("speedOffer", hasProperty("silver", is(LocalTime.of(00,10,30 )))))
				.andExpect(model().attribute("speedOffer", hasProperty("discountSilver", is(10))))
				.andExpect(model().attribute("speedOffer", hasProperty("bronze", is(LocalTime.of(00,15,30 )))))
				.andExpect(model().attribute("speedOffer", hasProperty("discountBronze", is(5))))
				.andExpect(model().attribute("speedOffer", hasProperty("client", is(clientTest))))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/speed/createOrUpdateSpeedOfferForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateSpeedOfferError() throws Exception {
		sp1.setStatus(StatusOffer.inactive);
		mockMvc.perform(get("/offers/speed/{speedOfferId}/edit",TEST_SPEEDOFFER_ID)
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateSpeedOfferSuccess() throws Exception {
		mockMvc.perform(post("/offers/speed/{speedOfferId}/edit",TEST_SPEEDOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("food", "food1test")
					.param("status", "hidden")
					.param("gold", "00:05:30")
					.param("discountGold", "15")
					.param("silver", "00:10:30")
					.param("discountSilver", "10")
					.param("bronze", "00:15:30")
					.param("discountBronze", "5")
					.param("code", "")
					.sessionAttr("idSpeed", TEST_SPEEDOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/speed/"+TEST_SPEEDOFFER_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateSpeedOfferError() throws Exception {
		mockMvc.perform(post("/offers/speed/{speedOfferId}/edit",TEST_SPEEDOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2021-12-22T12:30")
					.param("food", "food1test")
					.param("status", "hidden")
					.param("gold", "00:05:30")
					.param("discountGold", "15")
					.param("silver", "00:10:30")
					.param("discountSilver", "10")
					.param("bronze", "00:15:00")
					.param("discountBronze", "5")
					.param("code", "")
					.sessionAttr("idSpeed", TEST_SPEEDOFFER_ID))
				.andExpect(model().attributeExists("speedOffer"))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/speed/createOrUpdateSpeedOfferForm"));

	}
}
