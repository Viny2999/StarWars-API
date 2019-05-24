package com.starwars.b2w.API;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMain() {
        return "{\n " +
                "apiName: \"B2W Star Wars API\",\n" +
                "createIn:\"2019-05-24\",\n" +
                "creator: \"Vinícius Menezes\"," +
                "\n}";
    }
}
