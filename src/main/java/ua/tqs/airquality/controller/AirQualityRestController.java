package ua.tqs.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.cache.Cache;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.service.AirQualityService;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

@RestController
@RequestMapping("/api")
public class AirQualityRestController {

    @Autowired
    private AirQualityService airQualityService;

    private static final Logger logger
            = Logger.getLogger(
            AirQualityRestController.class.getName());


    @GetMapping("/air-quality/{city}")
    public Map<City, AirQuality> getAirQualityByCity(@PathVariable("city") String city) throws IOException, InterruptedException {
        logger.log(Level.INFO, "External API Request");
        Map<City, AirQuality> response = airQualityService.getCurrentAirQualityByCity(city);
        logger.log(Level.INFO, "Response: {0}", response);

        return response;
    }

    @GetMapping(value = "/air-quality-lat-lng")
    public Map<City, AirQuality> getAirQualityByLatLng(@RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lng", required = false) String lng, Model model) throws IOException, InterruptedException {
        logger.log(Level.INFO, () -> "Get city lat and lng to search for airquality: " + lat + " - " + lng);

        Map<City, AirQuality> response = airQualityService.getCurrentAirQualityByLatLng(lat, lng);
        logger.log(Level.INFO, "Response: {0}", response);

        return response;
    }

    @GetMapping("/stats")
    public Cache getCacheStatistics() {
        logger.log(Level.INFO, "Cache stats requested");
        return airQualityService.getCacheStatistics();
    }

}
