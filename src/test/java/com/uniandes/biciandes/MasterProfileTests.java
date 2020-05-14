package com.uniandes.biciandes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisabledIf(expression = "#{environment['spring.profiles.active'] != 'master'}", loadContext = true)
public class MasterProfileTests extends BiciandesApplicationTests {

    @WithMockUser(authorities = {"master"})
    @Test
    public void getPublishCompleteTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/publish"))
                .andExpect(status().isOk())
                .andExpect(view().name("publish"))
                .andExpect(content().string(containsString("<div class=\"videos section\">")))
                .andReturn();

    }

    @WithMockUser(authorities = {"master"})
    @Test
    public void getGroupsTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/group"))
                .andExpect(status().isOk())
                .andExpect(view().name("group"))
                .andExpect(content().string(containsString("<h2>Grupos</h2>")))
                .andReturn();

    }
}
