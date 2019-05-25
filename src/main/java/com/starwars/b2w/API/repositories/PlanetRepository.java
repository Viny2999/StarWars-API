package com.starwars.b2w.API.repositories;

import com.starwars.b2w.API.models.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {
 Planet findById(Integer id);
}
