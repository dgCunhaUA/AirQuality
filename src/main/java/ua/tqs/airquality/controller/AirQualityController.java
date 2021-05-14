package ua.tqs.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.service.AirQualityService;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AirQualityController {

    private String resultsTemplate = "results";

    @Autowired
    private AirQualityService airQualityService;

    private static final Logger logger
            = Logger.getLogger(
            AirQualityController.class.getName());

    @GetMapping("/")
    public String homePage(Model model) {

        model.addAttribute("city", new City());
        return "index";
    }

    @GetMapping("/air-quality")
    public String getAirQualityByCityName(@ModelAttribute City city, Model model) throws IOException, InterruptedException {

        logger.log(Level.INFO, "Getting current air quality city by name ...");
        Map<City, AirQuality> response = airQualityService.getCurrentAirQualityByCity(city.getName());
        logger.log(Level.INFO, "Response: {0}", response);

        Map.Entry<City, AirQuality> entry = response.entrySet().iterator().next();

        logger.log(Level.INFO, () -> "Return template, infos: " + entry.getKey().toString() + entry.getValue().toString());
        model.addAttribute("airQuality", entry.getValue());
        model.addAttribute("city", entry.getKey());

        return resultsTemplate;
    }


    @GetMapping("/air-quality-lat-lng")
    public String getAirQualityByLatLng(@ModelAttribute City city, Model model) throws IOException, InterruptedException {

        logger.log(Level.INFO, "Getting current air quality city by coords ...");
        Map<City, AirQuality> response = airQualityService.getCurrentAirQualityByLatLng(city.getLat(), city.getLng());
        logger.log(Level.INFO, "Response: {0}", response);


        Map.Entry<City, AirQuality> entry = response.entrySet().iterator().next();

        logger.log(Level.INFO, () -> "Return template, infos: " + entry.getKey().toString() + entry.getValue().toString());
        model.addAttribute("airQuality", entry.getValue());
        model.addAttribute("city", entry.getKey());

        return resultsTemplate;
    }
}