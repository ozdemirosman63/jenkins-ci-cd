package com.example.project4_devops.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    public String home() {
        return "Sau s Seng";

    }
    @GetMapping("/index")
    public String getIndex() {
        return "JENKİNS ci-cd pipeline project";
    }

}
