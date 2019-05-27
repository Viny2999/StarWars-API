package com.starwars.b2w.API.client;

import com.starwars.b2w.API.models.Films;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestSWApi {
    public void callSWApi() {
        RestTemplate request = new RestTemplate();
        Films films = request.getForObject("https://swapi.co/api/planets/1", Films.class);
        System.out.println(films);
    }
}
