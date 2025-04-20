package com.agrichallenge.agdata.controller;

import com.agrichallenge.agdata.model.AgData;
import com.agrichallenge.agdata.service.AgDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/agdata")
public class AgDataController {

    @Autowired
    private AgDataService agDataService;

    @GetMapping("")
    public ResponseEntity<List<AgData>> getAllProducts() throws IOException {
        List<AgData> products = agDataService.loadAgData();
        if (products.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // TODO: GET /api/agdata/crop-count?cropName=corn
    @GetMapping("/crop-count")
    public ResponseEntity<String> getCropCount(@RequestParam (required = true) String cropName){
        try {
            Long res = agDataService.getCropCount(cropName);
            if(res == -1){
                return new ResponseEntity<>("No data", HttpStatus.NO_CONTENT);
            }
            else
                return new ResponseEntity<>(String.valueOf(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // TODO: GET /api/agdata/average-yield?cropName=wheat
    @GetMapping("/average-yield")
    public ResponseEntity<String> getAvgYeild(@RequestParam (required = true) String cropName){
        try {
            double res = agDataService.getAverageYield(cropName);
            if(res == -1){
                return new ResponseEntity<>("No data", HttpStatus.NO_CONTENT);
            }
            else
                return new ResponseEntity<>(String.valueOf(res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // TODO: GET /api/agdata/by-region?region=Midwest
    @GetMapping("/by-region")
    public ResponseEntity<?> filterByRegion( @RequestParam (required = true) String region){
        try {
            List<AgData> res = agDataService.getRecordsByRegion(region);
            if(res == null || res.isEmpty()){
                return new ResponseEntity<>("No data", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
