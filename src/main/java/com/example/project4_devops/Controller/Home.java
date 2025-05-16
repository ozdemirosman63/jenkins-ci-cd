package com.example.project4_devops.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    public String home() {
        return "dsafad ALA rrr";
    }
    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

}
