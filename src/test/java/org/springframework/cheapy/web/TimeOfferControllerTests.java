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
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = TimeOfferController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class TimeOfferControllerTest {

	private static final int TEST_CLIENT_ID = 1;
	private static final int TEST_CLIENT_2_ID = 2;
	private static final int TEST_TIMEOFFER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TimeOfferService timeOfferService;
	
	@MockBean
	private ClientService clientService;

	private TimeOffer time1;
	private Client clientTest;
	private Client clientTest2;
	private List<TimeOffer> timeOfferLs;

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
		client1.setInit(LocalTime.of(12, 00));
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
		client1.setPreguntaSegura1("client2");
		client1.setPreguntaSegura2("client2");
		client2.setUsuar(user2);
		this.clientTest2 = client2;
		
		TimeOffer time1test = new TimeOffer();
		time1test.setId(TEST_TIMEOFFER_ID);
		time1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		time1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		time1test.setInit(LocalTime.of(12, 00));
		time1test.setFinish(LocalTime.of(13, 00));
		time1test.setDiscount(10);
		time1test.setClient(client1);
		time1test.setStatus(StatusOffer.hidden);
		time1test.setCode("");
		this.time1 = time1test;
		BDDMockito.given(this.timeOfferService.findTimeOfferById(TEST_TIMEOFFER_ID)).willReturn(this.time1);	
		
		List<TimeOffer> timeOfferLsTest = new ArrayList<>();
		timeOfferLsTest.add(time1test);
		this.timeOfferLs = timeOfferLsTest;
		BDDMockito.given(this.timeOfferService.findActiveTimeOffer(PageRequest.of(0, 5))).willReturn(this.timeOfferLs);
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/offers/timeOfferList/{page}", 0))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attribute("municipios", Municipio.values()))
				.andExpect(model().attributeExists("timeOfferLs"))
				.andExpect(model().attribute("timeOfferLs", timeOfferLs))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/time/timeOffersList"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowActive() throws Exception {
		mockMvc.perform(get("/offers/time/{timeOfferId}", TEST_TIMEOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("timeOffer"))
				.andExpect(model().attribute("timeOffer", time1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/time/timeOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenSuccess() throws Exception {
		
		time1.setStatus(StatusOffer.hidden);
		
		mockMvc.perform(get("/offers/time/{timeOfferId}", TEST_TIMEOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("timeOffer"))
				.andExpect(model().attribute("timeOffer", time1))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/time/timeOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenHasErrors() throws Exception {
		
		time1.setStatus(StatusOffer.hidden);
		time1.setClient(clientTest2);
		
		mockMvc.perform(get("/offers/time/{timeOfferId}", TEST_TIMEOFFER_ID))
				.andExpect(view().name("error"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/offers/time/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("timeOffer"))
				.andExpect(view().name("offers/time/createOrUpdateTimeOfferForm"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/offers/time/new")
				.with(csrf())
				.param("start", "2021-12-23T12:30")
				.param("end", "2022-12-23T12:30")
				.param("init", "12:30")
				.param("finish", "13:30")
				.param("discount", "10"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/offers/time/new")
					.with(csrf())
					.param("start", "2020-12-23T12:30")
					.param("end", "2020-12-22T12:30")
					.param("init", "gold")
					.param("finish", "")
					.param("discount", ""))
				.andExpect(model().attributeHasErrors("timeOffer"))
				.andExpect(model().attributeHasFieldErrors("timeOffer", "start"))
				.andExpect(model().attributeHasFieldErrors("timeOffer", "end"))
				.andExpect(model().attributeHasFieldErrors("timeOffer", "init"))
				.andExpect(model().attributeHasFieldErrors("timeOffer", "finish"))
				.andExpect(model().attributeHasFieldErrors("timeOffer", "discount"))
				.andExpect(view().name("offers/time/createOrUpdateTimeOfferForm"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateSuccess() throws Exception {
		mockMvc.perform(get("/offers/time/{timeOfferId}/activate", TEST_TIMEOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/time/"+TEST_TIMEOFFER_ID));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateHasErrors() throws Exception {
		mockMvc.perform(get("/offers/time/{timeOfferId}/activate", TEST_TIMEOFFER_ID+1))
				.andExpect(view().name("exception"));
	}


	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitSuccess() throws Exception {
		this.mockMvc.perform(get("/offers/time/{timeOfferId}/disable", TEST_TIMEOFFER_ID))
					.andExpect(status().isOk())
					.andExpect(view().name("offers/time/timeOffersDisable"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
    @Test
    void testDisableFormSuccess() throws Exception {
        this.mockMvc.perform(post("/offers/time/{timeOfferId}/disable", TEST_TIMEOFFER_ID)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/myOffers"));
    }

	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        time1.setClient(c);
		mockMvc.perform(get("/offers/time/{timeOfferId}/disable", TEST_TIMEOFFER_ID))
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableFormHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        time1.setClient(c);
		mockMvc.perform(post("/offers/time/{timeOfferId}/disable", TEST_TIMEOFFER_ID)
				.with(csrf()))
				.andExpect(view().name("error"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateTimeOfferSuccess() throws Exception {
		mockMvc.perform(get("/offers/time/{timeOfferId}/edit",TEST_TIMEOFFER_ID)
					.with(csrf()))
				.andExpect(model().attributeExists("timeOffer"))
				.andExpect(model().attribute("timeOffer", hasProperty("start", is(LocalDateTime.of(2021, 12, 23, 12, 30)))))
				.andExpect(model().attribute("timeOffer", hasProperty("end", is(LocalDateTime.of(2022, 12, 23, 12, 30)))))
				.andExpect(model().attribute("timeOffer", hasProperty("init",is(LocalTime.of(12, 00)))))
				.andExpect(model().attribute("timeOffer", hasProperty("finish",is(LocalTime.of(13, 00)))))
				.andExpect(model().attribute("timeOffer", hasProperty("discount", is(10))))
				.andExpect(model().attribute("timeOffer", hasProperty("client", is(clientTest))))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/time/createOrUpdateTimeOfferForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateTimeOfferError() throws Exception {
		time1.setStatus(StatusOffer.inactive);
		mockMvc.perform(get("/offers/time/{timeOfferId}/edit",TEST_TIMEOFFER_ID)
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateTimeOfferSuccess() throws Exception {
		mockMvc.perform(post("/offers/time/{timeOfferId}/edit",TEST_TIMEOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("init", "12:30")
					.param("finish", "13:30")
					.param("status", "hidden")
					.param("discount", "10")
					.param("code", "")
					.sessionAttr("idTime", TEST_TIMEOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/time/"+TEST_TIMEOFFER_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateTimeOfferError() throws Exception {
		mockMvc.perform(post("/offers/time/{timeOfferId}/edit",TEST_TIMEOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2021-12-22T12:30")
					.param("init", "12:30")
					.param("finish", "11:30")
					.param("status", "hidden")
					.param("discount", "10")
					.param("code", "")
					.sessionAttr("idTime", TEST_TIMEOFFER_ID))
				.andExpect(model().attributeExists("timeOffer"))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/time/createOrUpdateTimeOfferForm"));
	}


	
}
