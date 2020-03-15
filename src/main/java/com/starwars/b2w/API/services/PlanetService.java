package com.starwars.b2w.API.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {
  @Autowired
  private PlanetRepository planetRepository;
  private RestTemplate restTemplate = new RestTemplate();
  private HttpHeaders headers = new HttpHeaders();

  @Value("${swapi.url}")
  private String SWAPI_URL;

  public ResponseEntity getAllPlanets () {
    try {
      List<Planet> planets = planetRepository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
      return ResponseEntity.status(HttpStatus.OK).body(planets);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Internal Server Error\"}");
    }
  }

  public ResponseEntity getPlanetByIndex(Integer index) {
    try {
      Optional<Planet> planet = planetRepository.findByIndexOpt(index);

      if (planet.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Planet not Found\"}");

      return requestSWAPI(planet.get());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Internal Server Error\"}");
    }
  }

  public ResponseEntity getPlanetByName(String name) {
    try {
      Optional<Planet> planet = planetRepository.findByNameOpt(name);

      if (planet.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Planet not Found\"}");

      return requestSWAPI(planet.get());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Internal Server Error\"}");
    }
  }

  public synchronized ResponseEntity<String> createPlanet(Planet planet) {
    try {
      if (!checkPlanet(planet)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Missing Information\"}");
      }

      Optional<Planet> planetOptional = planetRepository.findByNameOpt(planet.getName());

      if (planetOptional.isPresent())
        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"error\":\"Planet " + planet.getName() + " already exists\"}");

      List<Planet> planets = planetRepository.findAll();
      planet.setIndex(Integer.valueOf(planets.size() + 1));
      planetRepository.insert(planet);
      return ResponseEntity.status(HttpStatus.CREATED).body("{\"success\":\"Planet Created\"}");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Internal Server Error\"}");
    }
  }

  public ResponseEntity deletePlanet(Integer index) {
    try {
      planetRepository.delete(planetRepository.findByIndex(index));
      return ResponseEntity.status(HttpStatus.OK).body("{\"success\":\"Planet Deleted\"}");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Planet not Found\"}");
    }
  }

  private ResponseEntity requestSWAPI(Planet planet) {
    try {
      Object responseSWApi = restTemplate.exchange(SWAPI_URL + planet.getIndex(), HttpMethod.GET, headerGenerator(), Object.class);

      JSONObject responseJson = new JSONObject(responseSWApi);

      JSONObject newPlanet = new JSONObject();

      newPlanet.put("index", planet.getIndex());
      newPlanet.put("name", planet.getName());
      newPlanet.put("climate", planet.getClimate());
      newPlanet.put("terrain", planet.getTerrain());
      newPlanet.put("movies", responseJson.getJSONObject("body").getJSONArray("films"));

      return ResponseEntity.status(HttpStatus.OK).body(newPlanet.toString());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"SWAPI Unavailable\"}");
    }
  }

  private HttpEntity headerGenerator() {
    this.headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    this.headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

    return entity;
  }

  private Boolean checkPlanet(Planet planet) {
    return  !StringUtils.isEmpty(planet.getName()) &&
            !StringUtils.isEmpty(planet.getClimate()) &&
            !StringUtils.isEmpty(planet.getTerrain());
  }
}
