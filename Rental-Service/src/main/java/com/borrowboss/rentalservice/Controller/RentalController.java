package com.borrowboss.rentalservice.Controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
public class RentalController {

    @GetMapping("/hi")
    public String getHi(){
        return "hai from rental service";
    }
}
