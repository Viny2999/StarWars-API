package com.starwars.b2w.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwars.b2w.API.models.Films;
import com.starwars.b2w.API.client.RequestSWApi;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    private PlanetRepository planetRepository;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private ObjectMapper jsonParse = new ObjectMapper();
    private RequestSWApi requestSWApi = new RequestSWApi();

    public PlanetController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @GetMapping
    public List<Planet> getAllPlanet() {
        List<Planet> planets = planetRepository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
        return planets;
    }

    @GetMapping(value = "/{id}")
    public Object getPlanetById(@PathVariable("id") Integer id) {
        UriComponents uri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("swapi.co/api")
            .path("/planets/" + id)
            .build();

            this.headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            this.headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            Object response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET,entity,Object.class);

            return response;

//        this.requestSWApi.callSWApi();
//        return planetRepository.findById(id);
    }

    @GetMapping(value = "/name/{name}")
    public Planet getPlanetById(@PathVariable("name") String name) {
        return planetRepository.findByName(name);
    }

    @PostMapping
    public Planet createPlanet(@Valid @RequestBody Planet planet) {
        List<Planet> planets = planetRepository.findAll();
        planet.setId(new Integer(planets.size() + 1));
        planetRepository.insert(planet);
        return planet;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePlanet(@PathVariable Integer id) {
        planetRepository.delete(planetRepository.findById(id));
    }
}
