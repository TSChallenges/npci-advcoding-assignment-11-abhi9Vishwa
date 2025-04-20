package com.agrichallenge.agdata.service;

import com.agrichallenge.agdata.model.AgData;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

// Service class for analyzing agricultural data
// TODO: Implement this service class according to the requirements.

@Service
public class AgDataService {

    private final List<AgData> agDataList;

    public AgDataService() throws IOException {
        this.agDataList = loadAgData();
    }

    public List<AgData> loadAgData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Load the data from JSON file from resources
        InputStream inputStream = getClass().getResourceAsStream("/data/agdata.json");
        // Map the JSON to a List of AgData objects
        return objectMapper.readValue(inputStream, new TypeReference<List<AgData>>() {});
    }

    public Long getCropCount(String cropName) {
        Long ct = 0L;
        for(AgData d : agDataList){
            if(d.getCrop().equalsIgnoreCase(cropName))
                ct++;
        }
        return ct;
    }

    public double getAverageYield(String cropName) {
        // TODO: Implement this method to Calculate the average yield for a specific crop if it exists, else return 0.0
        long total = 0L;
        long ct = 0L;

        for(AgData d : agDataList){
            if(d.getCrop().equalsIgnoreCase(cropName)){
                total += d.getYield();
                ct++;
            }
        }

        double avg = (double) total /ct;

        avg = Math.round(avg* Math.pow(10,3))/ Math.pow(10,3);

        return avg;
    }

    public List<AgData> getRecordsByRegion(String region) {
        // TODO: Implement this method to Get all records from a specific region
        return agDataList.stream().filter(d -> d.getRegion().equalsIgnoreCase(region)).collect(Collectors.toList());
    }

}

