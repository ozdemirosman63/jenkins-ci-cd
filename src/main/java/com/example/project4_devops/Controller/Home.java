package com.example.project4_devops.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    public String home() {
        return "Sau Library";

    }
    @GetMapping("/index")
    public String getIndex() {
        return "ff ci-cd pipeline project";
    }

}
