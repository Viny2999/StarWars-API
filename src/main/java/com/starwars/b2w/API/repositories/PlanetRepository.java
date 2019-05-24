package com.starwars.b2w.API.repositories;

import com.starwars.b2w.API.models.Planet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, String> {
 Planet findBy_id(ObjectId _id);
}
