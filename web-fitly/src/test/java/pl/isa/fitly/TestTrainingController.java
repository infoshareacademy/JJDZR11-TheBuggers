package pl.isa.fitly;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TestTrainingController {

    @Autowired
    MockMvc mockMvc;

    @Test
    void printingTrainingForGivenWeight() throws Exception {
        mockMvc.perform(post("/training").param("weight", "70.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainings"))
                .andExpect(model().attribute("deadliftWeight", 105.0));
    }
}
