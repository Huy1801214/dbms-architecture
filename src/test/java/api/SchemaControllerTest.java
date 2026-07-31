package api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SchemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSchemaSuccess() throws Exception {
        String existDbId = "6d8fd07b-0863-48a5-bd7b-5e265f758e22";
        String jsonSchemaRequest = """
                {
                    "name": "public_schema",
                    "owner": "admin"
                }
                """;

        mockMvc.perform(post("/api/v1/databases/" + existDbId + "/schemas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSchemaRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("public_schema"))
                .andExpect(jsonPath("$.owner").value("admin"))
                .andExpect(jsonPath("$.databaseId").value(existDbId));

    }

}
