package com.starwars.b2w.API.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Planet")
public class Planet {
  @Id @Getter @Setter
  private Integer index;
  private ObjectId id;
  @Getter @Setter
  private String name;
  @Getter @Setter
  private String climate;
  @Getter @Setter
  private String terrain;

  public Planet(Integer index, String name, String climate, String terrain) {
    this.index = index;
    this.name = name;
    this.climate = climate;
    this.terrain = terrain;
  }
}
