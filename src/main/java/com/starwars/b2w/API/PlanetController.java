package com.starwars.b2w.API;

import com.starwars.b2w.API.models.Planet;
import com.starwars.b2w.API.repositories.PlanetRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    private PlanetRepository repository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Planet> getAllPlanet() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Planet getPlanetById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyPlanetById(@PathVariable("id") ObjectId id, @Valid
    @RequestBody Planet planet) {
        planet.set_id(id);
        repository.save(planet);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Planet createPlanet(@Valid @RequestBody Planet planet) {
        planet.set_id(ObjectId.get());
        repository.save(planet);
        return planet;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlanet(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }
}
