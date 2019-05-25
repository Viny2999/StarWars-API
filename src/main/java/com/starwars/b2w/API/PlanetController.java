package com.starwars.b2w.API;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    private PlanetRepository planetRepository;

    public PlanetController(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @GetMapping
    public List<Planet> getAllPlanet() {
        List<Planet> planets = this.planetRepository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
        return planets;
    }

    @GetMapping(value = "/{id}")
    public Planet getPlanetById(@PathVariable("id") Integer id) {
        return planetRepository.findById(id);
    }

    @GetMapping(value = "/name/{name}")
    public Planet getPlanetById(@PathVariable("name") String name) {
        return planetRepository.findByName(name);
    }

    @PutMapping(value = "/{id}")
    public void modifyPlanetById(@PathVariable("id") Integer id, @Valid
    @RequestBody Planet planet) {
        planetRepository.save(planet);
    }

    @PostMapping
    public Planet createPlanet(@Valid @RequestBody Planet planet) {
        
        planetRepository.save(planet);
        return planet;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePlanet(@PathVariable Integer id) {
        planetRepository.delete(planetRepository.findById(id));
    }
}
