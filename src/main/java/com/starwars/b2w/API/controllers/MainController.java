package com.starwars.b2w.API.controllers;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public String getMain() {

    JSONObject info = new JSONObject();
    info.put("apiName", "B2W Star Wars API");
    info.put("createIn", "2019-05-24");
    info.put("creator", "Vinícius Menezes");

    return info.toString();
  }
}
