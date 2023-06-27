package CSCT321.ProjectAqua;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateString() throws Exception {
        String newString = "New test string";

        mockMvc.perform(post("/api/create/" + newString)) // What CRUD request to perform on what URI including any extra params
                .andExpect(status().isOk()) // Expects an HTTP 200 status code in the return
                .andExpect(content().string("Created new string: " + newString)); //Expects that the string matches in the response
    }

    @Test
    public void testReadString() throws Exception {
        mockMvc.perform(get("/api/read"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateString() throws Exception {
        String updatedString = "Updated test string";

        mockMvc.perform(put("/api/update/" + updatedString))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated string to: " + updatedString));
    }

    @Test
    public void testDeleteString() throws Exception {
        mockMvc.perform(delete("/api/delete"))
                .andExpect(status().isOk());
    }
}
