package org.springframework.cheapy.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import org.springframework.cheapy.model.Review;
import org.springframework.cheapy.model.ReviewClient;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.ReviewClientService;
import org.springframework.cheapy.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = ReviewClientController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class ReviewClientControllerTest {

	private static final int TEST_REVIEW_CLIENT_ID = 1;
	private static final String TEST_CLIENT_ID = "client";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private ClientService clientService;
	
	@MockBean
	private ReviewClientService reviewClientService;
	
	private User user;
	private Client client;
	private List<ReviewClient> reviewClientLs;

	@BeforeEach
	void setup() {
		user = new User();
		user.setUsername("user");
		user.setPassword("user");
		BDDMockito.given(this.userService.getCurrentUser()).willReturn(user);
		
		User userClient = new User();
		userClient.setUsername("client");
		userClient.setPassword("client");
		Client client = new Client();
		client.setName("client1");
		client.setEmail("client1");
		client.setAddress("client1");
		client.setInit(LocalTime.of(01, 00));
		client.setFinish(LocalTime.of(01, 01));
		client.setExpiration(LocalDate.of(3000,12,30));
		client.setTelephone("123456789");
		client.setDescription("client1");
		client.setFood("client1");
		client.setUsuar(userClient);
		BDDMockito.given(this.clientService.getCurrentClient()).willReturn(client);
		BDDMockito.given(this.clientService.findByUsername("client")).willReturn(client);
		
		ReviewClient reviewClient = new ReviewClient();
		reviewClient.setOpinion("test");
		reviewClient.setService(5);
		reviewClient.setFood(5);
		reviewClient.setQualityPrice(5);
		reviewClient.setEscritor(user);
		reviewClient.setBar(client);
		BDDMockito.given(this.reviewClientService.findReviewById(TEST_REVIEW_CLIENT_ID)).willReturn(reviewClient);
		
		reviewClientLs = new ArrayList<>();
		reviewClientLs.add(reviewClient);
		BDDMockito.given(this.reviewClientService.findAllReviewsByBar(PageRequest.of(0, 6), client)).willReturn(reviewClientLs);
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/reviewsClientList/{idClient}/{page}", ))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("reviewsLs"))
				.andExpect(model().attribute("reviewsLs", reviewLs))
				.andExpect(view().name("reviews/reviewsList"));
	}
	

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testShow() throws Exception {
		mockMvc.perform(get("/reviews/{reviewId}", TEST_REVIEW_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("review"))
				.andExpect(view().name("reviews/reviewsShow"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/reviews/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("review"))
				.andExpect(view().name("reviews/createOrUpdateReviewForm"));
	}

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/reviews/new")
					.with(csrf())
					.param("opinion", "test")
					.param("stars", "5"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/reviews/new")
					.with(csrf())
					.param("opinion", "")
					.param("stars", "6"))
				.andExpect(model().attributeHasErrors("review"))
				.andExpect(model().attributeHasFieldErrors("review", "opinion"))
				.andExpect(model().attributeHasFieldErrors("review", "stars"))
				.andExpect(view().name("reviews/createOrUpdateReviewForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/reviews/{reviewId}/edit", TEST_REVIEW_ID)
					.with(csrf()))
				.andExpect(model().attributeExists("review"))
				.andExpect(model().attribute("review", hasProperty("opinion", is("test"))))
				.andExpect(model().attribute("review", hasProperty("stars", is(5))))
				.andExpect(model().attribute("review", hasProperty("escritor",is(user))))
				.andExpect(status().isOk())
				.andExpect(view().name("reviews/createOrUpdateReviewForm"));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/reviews/{reviewId}/edit", TEST_REVIEW_ID)
					.with(csrf())
					.param("id","1")
					.param("opinion", "test")
					.param("stars", "5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/reviews/"+ TEST_REVIEW_ID));
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testUpdateFormHasError() throws Exception {
		mockMvc.perform(post("/reviews/{reviewId}/edit", TEST_REVIEW_ID)
					.with(csrf())
					.param("id", "1")
					.param("opinion", "")
					.param("stars", "6"))
				.andExpect(model().attributeExists("review"))
//				.andExpect(model().attributeHasFieldErrors("review", "id"))
				.andExpect(model().attributeHasFieldErrors("review", "opinion"))
				.andExpect(model().attributeHasFieldErrors("review", "stars"))
				.andExpect(status().isOk())
				.andExpect(view().name("reviews/createOrUpdateReviewForm"));
	}
}
