package com.starwars.b2w.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.services.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
  @Autowired
  private PlanetService planetService;

  @GetMapping
  public List<Planet> getAllPlanets(){
    return planetService.getAllPlanets();
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
  public ResponseEntity createPlanet(@Valid @RequestBody Planet planet) {
    return planetService.createPlanet(planet);
  }

  @DeleteMapping(value = "/{id}")
  public void deletePlanet(@PathVariable Integer id) {
    planetService.deletePlanet(id);
  }
}
