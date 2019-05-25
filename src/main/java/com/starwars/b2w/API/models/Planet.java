package com.starwars.b2w.API.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Planet")
public class Planet {
    @Id
    public Integer id;
    public String name;
    public String climate;
    public String terrain;

    public Planet(Integer id, String name, String climate, String terrain) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClimate() { return climate; }
    public void setClimate(String climate) { this.climate = climate; }

    public String getTerrain() { return terrain; }
    public void setTerrain(String terrain) { this.terrain = terrain; }
}
