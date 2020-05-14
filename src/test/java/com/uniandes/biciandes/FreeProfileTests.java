package com.uniandes.biciandes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.DisabledIf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DisabledIf(expression = "#{environment['spring.profiles.active'] != 'free'}", loadContext = true)
public class FreeProfileTests extends BiciandesApplicationTests {

    @WithMockUser
    @Test
    public void getStatisticsTest() throws Exception {
        mockMvc.perform(get("/estadisticas"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/estadisticas"))
                .andReturn();
    }

    @WithMockUser
    @Test
    public void getAccessDeniedForGroupsTest() throws Exception {
        mockMvc.perform(get("/group"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void getAccessDeniedForBicyclesTest() throws Exception {
        mockMvc.perform(get("/bicycles"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void getAccessDeniedForPublishTest() throws Exception {
        mockMvc.perform(get("/publish"))
                .andExpect(status().isForbidden());
    }

}
