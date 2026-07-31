package api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDatabasesFromMockJson() throws Exception {
        mockMvc.perform(get("/catalog/databases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].name", hasItem("student_db")))
                .andExpect(jsonPath("$[*].name", hasItem("system_db")))
                .andExpect(jsonPath("$[*].name", hasItem("finance_db")));
    }

    @Test
    public void testGetAllDatabasesWithApiV1Prefix() throws Exception {
        mockMvc.perform(get("/api/v1/catalog/databases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].name", hasItem("student_db")));
    }
}
