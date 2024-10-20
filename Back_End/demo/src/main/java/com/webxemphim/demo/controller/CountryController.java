package com.webxemphim.demo.controller;

import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getCountry(@PathVariable int id) {
        ResponseData responseData = countryService.getCountryById(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
