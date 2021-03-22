package com.copytorex.restservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.ResultMatcher.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestservicesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createUser() throws Exception {
		String json = "{\"name\": \"Kin\", \"birthDate\": \"2000-05-25T03:31:26.528+00:00\"}";
		mockMvc.perform(
				post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json)
		).andExpect(
				matchAll(
						status().isCreated(),
						content().json("{\"User ID\":1}")
				)
		);
	}

}
