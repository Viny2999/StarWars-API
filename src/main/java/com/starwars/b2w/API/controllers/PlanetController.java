package com.starwars.b2w.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.services.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
  @Autowired
  private PlanetService planetService;

  @GetMapping
  public ResponseEntity getAllPlanets(){
    return planetService.getAllPlanets();
  }

  @GetMapping(value = "/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getPlanetById(@PathVariable("index") Integer index) {
    return planetService.getPlanetByIndex(index);
  }

  @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getPlanetByName(@PathVariable("name") String name) {
    return planetService.getPlanetByName(name);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createPlanet(@Valid @RequestBody Planet planet) {
    return planetService.createPlanet(planet);
  }

  @DeleteMapping(value = "/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deletePlanet(@PathVariable Integer index) {
    return planetService.deletePlanet(index);
  }
}
