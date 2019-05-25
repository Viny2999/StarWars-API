package com.starwars.b2w.API;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Planet> getAllPlanet() {
        List<Planet> planets = this.planetRepository.findAll(Sort.by(Sort.Direction.ASC, "_id"));
        return planets;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Planet getPlanetById(@PathVariable("id") Integer id) {
        return planetRepository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyPlanetById(@PathVariable("id") Integer id, @Valid
    @RequestBody Planet planet) {
        planetRepository.save(planet);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Planet createPlanet(@Valid @RequestBody Planet planet) {
        planetRepository.save(planet);
        return planet;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlanet(@PathVariable Integer id) {
        planetRepository.delete(planetRepository.findById(id));
    }
}
