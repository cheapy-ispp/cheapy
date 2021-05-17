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
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = FoodOfferController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class FoodOfferControllerTest {

	private static final int TEST_CLIENT_ID = 1;
	private static final int TEST_CLIENT_2_ID = 2;
	private static final int TEST_FOODOFFER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FoodOfferService foodOfferService;
	
	@MockBean
	private ClientService clientService;

	private FoodOffer fo1;
	private Client clientTest;
	private Client clientTest2;
	private List<FoodOffer> foodOfferLs;

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
		this.clientTest = client1;
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
		
		FoodOffer fo1test = new FoodOffer();
		fo1test.setId(TEST_FOODOFFER_ID);
		fo1test.setStart(LocalDateTime.of(2021, 12, 23, 12, 30));
		fo1test.setEnd(LocalDateTime.of(2022, 12, 23, 12, 30));
		fo1test.setFood("fo1test");
		fo1test.setDiscount(1);
		fo1test.setPrice(10.0);
		fo1test.setStatus(StatusOffer.hidden);
		fo1test.setCode("");
		fo1test.setClient(client1);
		this.fo1 = fo1test;
		BDDMockito.given(this.foodOfferService.findFoodOfferById(TEST_FOODOFFER_ID)).willReturn(this.fo1);
		
		List<FoodOffer> foodOfferLsTest = new ArrayList<>();
		foodOfferLsTest.add(fo1test);
		this.foodOfferLs = foodOfferLsTest;
		BDDMockito.given(this.foodOfferService.findActiveFoodOffer(PageRequest.of(0, 5))).willReturn(this.foodOfferLs);
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/offers/foodOfferList/{page}", 0))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("municipios"))
				.andExpect(model().attribute("municipios", Municipio.values()))
				.andExpect(model().attributeExists("foodOfferLs"))
				.andExpect(model().attribute("foodOfferLs", foodOfferLs))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/food/foodOffersList"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowActive() throws Exception {
		mockMvc.perform(get("/offers/food/{foodOfferId}", TEST_FOODOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("foodOffer"))
				.andExpect(model().attribute("foodOffer", fo1))
				.andExpect(model().attributeExists("newPrice"))
				.andExpect(model().attribute("newPrice", fo1.getNewPrice()))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/food/foodOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenSuccess() throws Exception {
		
		fo1.setStatus(StatusOffer.hidden);
		
		mockMvc.perform(get("/offers/food/{foodOfferId}", TEST_FOODOFFER_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("foodOffer"))
				.andExpect(model().attribute("foodOffer", fo1))
				.andExpect(model().attributeExists("newPrice"))
				.andExpect(model().attribute("newPrice", fo1.getNewPrice()))
				.andExpect(model().attributeExists("localDateTimeFormat"))
//				.andExpect(model().attribute("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.andExpect(view().name("offers/food/foodOffersShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testShowHiddenHasErrors() throws Exception {
		
		fo1.setStatus(StatusOffer.hidden);
		fo1.setClient(clientTest2);
		
		mockMvc.perform(get("/offers/food/{foodOfferId}", TEST_FOODOFFER_ID))
				.andExpect(view().name("error"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/offers/food/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("foodOffer"))
				.andExpect(view().name("offers/food/createOrUpdateFoodOfferForm"));
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		
		mockMvc.perform(post("/offers/food/new")
					.with(csrf())
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("food", "food")
					.param("discount", "10")
					.param("price", "10.5")
					.param("image", ""))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/offers/food/new")
					.with(csrf())
					.param("start", "2020-12-23T12:30")
					.param("end", "2020-12-22T12:30")
					.param("food", "")
					.param("discount", "")
					.param("price", "")
					.param("image", ""))
				.andExpect(model().attributeHasErrors("foodOffer"))
				.andExpect(model().attributeHasFieldErrors("foodOffer", "start"))
				.andExpect(model().attributeHasFieldErrors("foodOffer", "end"))
				.andExpect(model().attributeHasFieldErrors("foodOffer", "food"))
				.andExpect(model().attributeHasFieldErrors("foodOffer", "discount"))
				.andExpect(model().attributeHasFieldErrors("foodOffer", "price"))
				.andExpect(view().name("offers/food/createOrUpdateFoodOfferForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateFoodOfferSuccess() throws Exception {
		mockMvc.perform(get("/offers/food/{foodOfferId}/edit",TEST_FOODOFFER_ID)
					.with(csrf()))
				.andExpect(model().attributeExists("foodOffer"))
				.andExpect(model().attribute("foodOffer", hasProperty("start", is(LocalDateTime.of(2021, 12, 23, 12, 30)))))
				.andExpect(model().attribute("foodOffer", hasProperty("end", is(LocalDateTime.of(2022, 12, 23, 12, 30)))))
				.andExpect(model().attribute("foodOffer", hasProperty("food",is("fo1test"))))
				.andExpect(model().attribute("foodOffer", hasProperty("discount", is(1))))
				.andExpect(model().attribute("foodOffer", hasProperty("price", is(10.0))))
				.andExpect(model().attribute("foodOffer", hasProperty("client", is(clientTest))))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/food/createOrUpdateFoodOfferForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testInitUpdateFoodOfferError() throws Exception {
		fo1.setStatus(StatusOffer.inactive);
		mockMvc.perform(get("/offers/food/{foodOfferId}/edit",TEST_FOODOFFER_ID)
					.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateFoodOfferSuccess() throws Exception {

		mockMvc.perform(post("/offers/food/{foodOfferId}/edit",TEST_FOODOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2022-12-23T12:30")
					.param("food", "food1test")
					.param("status", "hidden")
					.param("discount", "10")
					.param("price", "10.5")
					.param("code", "")
					.param("image", "")
					.sessionAttr("idFood", TEST_FOODOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/food/"+TEST_FOODOFFER_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testUpdateFoodOfferError() throws Exception {
		
		mockMvc.perform(post("/offers/food/{foodOfferId}/edit",TEST_FOODOFFER_ID)
					.with(csrf())
					.param("id","1")
					.param("start", "2021-12-23T12:30")
					.param("end", "2021-12-22T12:30")
					.param("food", "food1test")
					.param("status", "hidden")
					.param("discount", "10")
					.param("price", "manoli")
					.param("code", "")
					.param("image", "")
					.sessionAttr("idFood", TEST_FOODOFFER_ID))
				.andExpect(model().attributeExists("foodOffer"))
				.andExpect(status().isOk())
				.andExpect(view().name("offers/food/createOrUpdateFoodOfferForm"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testActivateSuccess() throws Exception {
		mockMvc.perform(get("/offers/food/{foodOfferId}/activate", TEST_FOODOFFER_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/offers/food/"+TEST_FOODOFFER_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "client")
	@Test
	void testActivateHasErrors() throws Exception {
		mockMvc.perform(get("/offers/food/{foodOfferId}/activate", TEST_FOODOFFER_ID+1))
				.andExpect(view().name("exception"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitSuccess() throws Exception {
		this.mockMvc.perform(get("/offers/food/{foodOfferId}/disable", TEST_FOODOFFER_ID))
					.andExpect(status().isOk())
					.andExpect(view().name("offers/food/foodOffersDisable"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
    @Test
    void testDisableFormSuccess() throws Exception {
        this.mockMvc.perform(post("/offers/food/{foodOfferId}/disable", TEST_FOODOFFER_ID)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/myOffers"));
    }

	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableInitHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        fo1.setClient(c);
		mockMvc.perform(get("/offers/food/{foodOfferId}/disable", TEST_FOODOFFER_ID))
				.andExpect(view().name("error"));
	}
	
	@WithMockUser(value = "user1", authorities = "client")
	@Test
	void testDisableFormHasErrors() throws Exception {
		Client c = new Client();
        c.setId(2);
        fo1.setClient(c);
		mockMvc.perform(post("/offers/food/{foodOfferId}/disable", TEST_FOODOFFER_ID)
				.with(csrf()))
				.andExpect(view().name("error"));
	}
}
