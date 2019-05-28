package com.starwars.b2w.API.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;
import com.starwars.b2w.API.services.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
  @Autowired
  private PlanetRepository planetRepository;
  @Autowired
  private PlanetService planetService;

  @GetMapping
  public List<Planet> getAllPlanet(){
    return planetService.getAllPlanet();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getPlanetById(@PathVariable("id") Integer id) {
    return planetService.getPlanetById(id);
  }

  @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getPlanetByName(@PathVariable("name") String name) {
    return planetService.getPlanetByName(name);
  }

  @PostMapping
  public Planet createPlanet(@Valid @RequestBody Planet planet) {
    return planetService.createPlanet(planet);
  }

  @DeleteMapping(value = "/{id}")
  public void deletePlanet(@PathVariable Integer id) {
    planetService.deletePlanet(id); 
  }
}
