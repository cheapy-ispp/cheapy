package org.springframework.cheapy.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cheapy.configuration.SecurityConfiguration;
import org.springframework.cheapy.model.Review;
import org.springframework.cheapy.model.User;
import org.springframework.cheapy.service.ReviewService;
import org.springframework.cheapy.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(value = ReviewController.class, 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class ReviewControllerTest {

	private static final int TEST_REVIEW_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private ReviewService reviewService;
	
	private User user;
	private List<Review> reviewLs;

	@BeforeEach
	void setup() {
		user = new User();
		user.setUsername("user");
		user.setPassword("user");
		BDDMockito.given(this.userService.getCurrentUser()).willReturn(user);
		
		Review review = new Review();
		review.setOpinion("test");
		review.setStars(5);
		review.setEscritor(user);
		BDDMockito.given(this.reviewService.findReviewById(TEST_REVIEW_ID)).willReturn(review);
		
		reviewLs = new ArrayList<>();
		reviewLs.add(review);
		BDDMockito.given(this.reviewService.findAllReviews(PageRequest.of(0, 6))).willReturn(reviewLs);
		
		BDDMockito.given(this.userService.duplicateUsername("spring")).willReturn(true);
	}
	
	@WithMockUser(value = "spring", authorities = "usuario")
	@Test
	void testFind() throws Exception {
		mockMvc.perform(get("/reviewsList/{page}", 0))
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
				.andExpect(model().attributeHasFieldErrors("review", "opinion"))
				.andExpect(model().attributeHasFieldErrors("review", "stars"))
				.andExpect(status().isOk())
				.andExpect(view().name("reviews/createOrUpdateReviewForm"));
	}
}
