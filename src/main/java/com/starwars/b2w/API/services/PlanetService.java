package com.starwars.b2w.API.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.List;


@Service
public class PlanetService {
  @Autowired
  private PlanetRepository planetRepository;
  private RestTemplate restTemplate = new RestTemplate();
  private HttpHeaders headers = new HttpHeaders();

  private final String URL_SWAPI = "https://swapi.co/api/planets/";

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  public List<Planet> getAllPlanet () {
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

  public Planet createPlanet(Planet planet) {
    List<Planet> planets = planetRepository.findAll();
    planet.setId(new Integer(planets.size() + 1));
    planetRepository.insert(planet);
    return planet;
  }

  public void deletePlanet(Integer id) {
    planetRepository.delete(planetRepository.findById(id));
  }

  private String requestSWAPI(Planet planet) {
    Object responseSWApi = restTemplate.exchange(URL_SWAPI + planet.getId(), HttpMethod.GET, headerGenerator(),Object.class);

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
}
