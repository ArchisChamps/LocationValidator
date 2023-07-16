package com.archi.locval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*")
public class LocationController {
    
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<?> scrapeWebsite(){
        return new ResponseEntity<String>(locationService.scrapeWebsite(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/invalid-locations")
    public ResponseEntity<?> getInvalidLocations(){
        return new ResponseEntity<List<InvalidLocationDTO>>(locationService.getInvalidLocations(), HttpStatus.OK);
    }
}