package com.starwars.b2w.API.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

  public List<Planet> getAllPlanets () {
    List<Planet> planets = planetRepository.findAll(Sort.by(Sort.Direction.ASC, "_id"));

    return planets;
  }

  public String getPlanetById(Integer id) {
    Planet planet = planetRepository.findById(id);

    return requestSWAPI(planet);
  }

  public String getPlanetByName(String name) {
    Planet planet = planetRepository.findByName(name);

    return requestSWAPI(planet);
  }

  public ResponseEntity createPlanet(Planet planet) {
    if(!checkPlanet(planet)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing Information");
    }

    Optional<Planet> planetOptional = planetRepository.findByNameOpt(planet.getName());

    if (planetOptional.isPresent())
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Planet" + planet.getName() + "already exists");

    List<Planet> planets = planetRepository.findAll();
    planet.setId(new Integer(planets.size() + 1));
    planetRepository.insert(planet);
    return ResponseEntity.status(HttpStatus.CREATED).body("Planet Created");
  }

  public void deletePlanet(Integer id) {
    planetRepository.delete(planetRepository.findById(id));
  }

  private String requestSWAPI(Planet planet) {
    Object responseSWApi = restTemplate.exchange(SWAPI_URL + planet.getId(), HttpMethod.GET, headerGenerator(), Object.class);

    JSONObject responseJson = new JSONObject(responseSWApi);

    JSONObject newPlanet = new JSONObject();

    newPlanet.put("id",  planet.getId());
    newPlanet.put("name", planet.getName());
    newPlanet.put("climate",  planet.getClimate());
    newPlanet.put("terrain",  planet.getTerrain());
    newPlanet.put("movies", responseJson.getJSONObject("body").getJSONArray("films"));

    return newPlanet.toString();
  }

  private HttpEntity headerGenerator() {
    this.headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    this.headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

    return entity;
  }

  private Boolean checkPlanet(Planet planet) {
    if (
        planet.getName().isEmpty() || planet.getName().equals(null) &&
        planet.getClimate().isEmpty() || planet.getClimate().equals(null) &&
        planet.getTerrain().isEmpty() || planet.getTerrain().equals(null)
    ) return false;

    return true;
  }
}
