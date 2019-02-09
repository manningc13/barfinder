package com.barfinder.barfinder.controller;

import com.barfinder.barfinder.fetch.MapLocationFetcher;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web Controller for routing to separate web pages
 */
@RestController
public class WebController {

    @Autowired
    private MapLocationFetcher mapLocationFetcher;
    private static final String GEOCODE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    @RequestMapping("/")
    public String index() {

        return "Home Page";
    }

    @RequestMapping("/search")
    public Pair<Integer, Integer> calculateDirectionToTarget(String destination) {
//        GeoApiContext context =
//        DirectionsApiRequest request = DirectionsApi.getDirections();
        return mapLocationFetcher.getClosestBar(destination);
    }
}
