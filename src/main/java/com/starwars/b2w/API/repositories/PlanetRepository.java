package com.starwars.b2w.API.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.starwars.b2w.API.models.Planet;

import java.util.Optional;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {
  @Query(value = "{'index': ?0}")
  Planet findByIndex(Integer index);

  @Query(value = "{'index': ?0}")
  Optional<Planet> findByIndexOpt(Integer index);

  @Query(value = "{'name': ?0}")
  Planet findByName(String name);

  @Query(value = "{'name': ?0}")
  Optional<Planet> findByNameOpt(String name);
}
