package ua.tqs.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.service.AirQualityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AirQualityController {

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

    @RequestMapping(value = "/air-quality", method = RequestMethod.GET)
    public String getAirQualityByCityName(@ModelAttribute City city, Model model) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get city name to search for airquality: {0}", city.toString());

        logger.log(Level.INFO, "External API Request for {0}", city.getName());
        HashMap<City, AirQuality> response = airQualityService.getCurrentAirQualityByCity(city.getName());
        logger.log(Level.INFO, "Response: " + response.toString());

        for (City i : response.keySet()) {
            AirQuality airQuality = response.get(i);

            logger.log(Level.INFO, "Return template, infos: " + i.toString() + airQuality.toString());
            model.addAttribute("airQuality", airQuality);
            model.addAttribute("city", i);
            return "results";
        }

        return "results";
    }

    //    public String getAirQualityByLatLng( @RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lng", required = false) String lng, Model model) throws IOException, InterruptedException {
    //40.6575
    //-7.91428
    @RequestMapping(value = "/air-quality-lat-lng", method = RequestMethod.GET)
    public String getAirQualityByLatLng(@ModelAttribute City city, Model model) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get city lat and lng to search for airquality: {0}", city.toString());
        //logger.log(Level.INFO, "Get city lat and lng to search for airquality: " + lat + " - " + lng);

        logger.log(Level.INFO, "External API Request for {0}", city.getName());
        HashMap<City, AirQuality> response = airQualityService.getCurrentAirQualityByLatLng(city.getLat(), city.getLng());
        logger.log(Level.INFO, "Response: " + response.toString());


        for (City i : response.keySet()) {
            AirQuality airQuality = response.get(i);

            logger.log(Level.INFO, "Return template, infos: " + i.toString() + airQuality.toString());
            model.addAttribute("airQuality", airQuality);
            model.addAttribute("city", i);
            return "results";
        }

        return "results";
    }

    /*
    @RequestMapping(value = "/air-quality-historical", method = RequestMethod.GET)
    public String getHistoricalAirQualityByCityName(@ModelAttribute City city, Model model) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Get Historical AirQuality data for: {0}", city.toString());

        HashMap<City, AirQuality> response = airQualityService.getHistoricalAirQualityByCity(city);
        //logger.log(Level.INFO, "Response: " + response.toString());


        return "results-historical";
    }*/

}