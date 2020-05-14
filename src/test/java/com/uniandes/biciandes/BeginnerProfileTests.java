package com.uniandes.biciandes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.DisabledIf;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisabledIf(expression = "#{environment['spring.profiles.active'] != 'beginner'}", loadContext = true)
public class BeginnerProfileTests extends BiciandesApplicationTests {

    @WithMockUser(authorities = {"beginner"})
    @Test
    public void getPublishWithPhotosTest() throws Exception {
        mockMvc.perform(get("/publish"))
                .andExpect(status().isOk())
                .andExpect(view().name("publish"))
                .andReturn();
    }

    @WithMockUser(authorities = {"beginner"})
    @Test
    public void getPublishWithoutVideosTest() throws Exception {
        Assertions.assertThrows(AssertionError.class, () -> mockMvc
                .perform(get("/publish"))
                .andExpect(content().string(containsString("<div class=\"videos section\">")))
        );
    }

    @WithMockUser(authorities = {"beginner"})
    @Test
    public void getErrorForGroupsTest() throws Exception {
        mockMvc.perform(get("/group"))
                .andExpect(status().isForbidden());
    }

}
