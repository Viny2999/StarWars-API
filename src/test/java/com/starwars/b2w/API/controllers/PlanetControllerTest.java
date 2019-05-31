package com.starwars.b2w.API.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.starwars.b2w.API.StarWarsApiApplication;
import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StarWarsApiApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  PlanetRepository planetRepository;

  @Test
  public void ApostPlanet() throws Exception {
    Planet planet = new Planet(999, "Earth", "Moderate", "Solid");
    JSONObject jsonPlanet = new JSONObject(planet);

    mockMvc.perform(post("/planets")
            .content(jsonPlanet.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void BremoveExistingPlanet() throws Exception {
    List<Planet> planets = planetRepository.findAll();

    mockMvc.perform(delete("/planets/"+ planets.size())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
  }

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

  @Test
  public void postInvalidPlanet() throws Exception {
    Planet planet = new Planet(999, "Earth", "Moderate", "Solid");
    JSONObject jsonPlanet = new JSONObject(planet);
    jsonPlanet.remove("name");

    mockMvc.perform(post("/planets")
            .content(jsonPlanet.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void postExistingPlanet() throws Exception {
    Planet planet = new Planet(1, "Tatooine", "Moderate", "Solid");
    JSONObject jsonPlanet = new JSONObject(planet);

    mockMvc.perform(post("/planets")
            .content(jsonPlanet.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void removeInvalidPlanet() throws Exception {
    mockMvc.perform(delete("/planets/9999999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
  }
}
