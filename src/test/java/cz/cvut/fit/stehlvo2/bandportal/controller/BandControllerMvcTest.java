package cz.cvut.fit.stehlvo2.bandportal.controller;

import cz.cvut.fit.stehlvo2.bandportal.domain.Band;
import cz.cvut.fit.stehlvo2.bandportal.service.BandService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BandController.class)
public class BandControllerMvcTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BandService bandService;

	@Test
	void create() throws Exception {
		Band b = new Band();
		b.setBandName("Band");
		Mockito.when(bandService.create(b)).thenReturn(b);

		mockMvc.perform(
			MockMvcRequestBuilders
				.post("/band")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{" +
					"\"bandName\": \"Band\"," +
					"\"bandWebsite\": \"bandWebsite\"" +
					"}")
		)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.bandName", Matchers.is("Band")));
	}
}
