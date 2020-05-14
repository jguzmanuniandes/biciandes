package com.uniandes.biciandes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BiciandesApplicationTests {

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void getProfileRedirectTest() throws Exception {
		mockMvc.perform(get("/verUser"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@Test
	public void getRedirectIfNonSecurityContextTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/idontexist"))
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@WithMockUser
	@Test
	public void getErrorForNonExistentPageTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/idontexist"))
				.andExpect(status().isNotFound())
				.andReturn();
	}

}
