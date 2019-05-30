package com.starwars.b2w.API.controllers;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.starwars.b2w.API.StarWarsApiApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StarWarsApiApplication.class)
@AutoConfigureMockMvc
public class PlanetControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlanetControllerTest planetControllerTest;

  @Test
  public void getAllPlanet() throws Exception {
    mockMvc.perform(get("/planets")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }

  @Test
  public void getValidPlanetById() throws Exception {
    mockMvc.perform(get("/planets/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }

  @Test
  public void getValidPlanetByName() throws Exception {
    mockMvc.perform(get("/planets/name/Tatooine")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }

  @Test
  public void getInvalidPlanetById() throws Exception {
    mockMvc.perform(get("/planets/19289182")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }

  @Test
  public void getInvalidPlanetByName() throws Exception {
    mockMvc.perform(get("/planets/name/askdjasdnasodn")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
  }


}
