package io.datajek.tennisplayerrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Tennis Player REST API";
    }

}
