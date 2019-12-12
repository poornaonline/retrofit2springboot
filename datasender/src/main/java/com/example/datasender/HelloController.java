package com.example.datasender;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/billionaire")
    public Billionaire getBillionaire() {
        Billionaire poorna = new Billionaire();
        poorna.setFirstName("Poorna");
        poorna.setLastName("Jayasinghe");
        poorna.setAge(28);

        return poorna;
    }
}
