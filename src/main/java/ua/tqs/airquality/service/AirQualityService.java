package ua.tqs.airquality.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.cache.Cache;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.repository.AmbeeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AirQualityService {

    @Autowired
    private AmbeeRepository ambeeRepository;

    private final Cache cache = new Cache(100L);

    private static final Logger logger
            = Logger.getLogger(
            AirQualityService.class.getName());

    public Map<City, AirQuality> getCurrentAirQualityByCity(String city) throws IOException, InterruptedException {

        // Verificar se ja existe em cache
        logger.log(Level.INFO, "Cache verification ...");
        Map<City, AirQuality> data = cache.getRequest(cache, city);

        if( data.size() == 1 ) {
            logger.log(Level.INFO, "Found in cache");
            return data;
        }

        logger.log(Level.INFO, "Not found in cache");

        // Make the request to the external API
        logger.log(Level.INFO, "External API Request");
        String response = ambeeRepository.getCurrentAirQualityByCity(city);


        // Recolher os valores
        return collectValues(data, response);
    }

    public Map<City, AirQuality> getCurrentAirQualityByLatLng(String lat, String lng) throws IOException, InterruptedException {

        logger.log(Level.INFO, "Cache verification ...");
        Map<City, AirQuality> data = cache.getRequestLatLng(cache, lat, lng);


        if( data.size() == 1 ) {
            logger.log(Level.INFO, "Found in cache");
            return data;
        }
        logger.log(Level.INFO, "Not found in cache");


        // Make the request to the external API
        logger.log(Level.INFO, "External API Request");
        String response = ambeeRepository.getCurrentAirQualityByLatLng(lat, lng);



        // Recolher os valores
        return collectValues(data, response);
    }


    public Cache getCacheStatistics() {
        logger.log(Level.INFO, "Returning cache statistics");
        return cache;
    }

    public Map<City, AirQuality> collectValues(Map<City, AirQuality> data, String response ) {
        logger.log(Level.INFO, "External API Response: {0}", response);


        var gson = new Gson();
        var jsonMap = gson.fromJson(response, Map.class);

        if( jsonMap.get("message").equals("success") )
            logger.log(Level.INFO, "Response with success");
        else {
            logger.log(Level.INFO, "Response with failure");

            AirQuality airQuality = new AirQuality();
            airQuality.setCO("-");
            airQuality.setNO2("-");
            airQuality.setOZONE("-");
            airQuality.setPM10("-");
            airQuality.setPM25("-");
            airQuality.setSO2("-");

            City cityObj = new City();
            cityObj.setName("City Not Found");
            cityObj.setPostalCode("-");
            cityObj.setCountry("-");
            cityObj.setLat("-");
            cityObj.setLng("-");

            data.put(cityObj, airQuality);

            return data;
        }

        logger.log(Level.INFO, "Converting response...");
        // Converter o http response to json
        var responseArray = (ArrayList) jsonMap.get("stations");
        var jsonObject = gson.toJsonTree(responseArray.get(0)).getAsJsonObject();


        AirQuality airQuality = new AirQuality();
        airQuality.setCO(jsonObject.get("CO").toString());
        airQuality.setNO2(jsonObject.get("NO2").toString());
        airQuality.setOZONE(jsonObject.get("OZONE").toString());
        airQuality.setPM10(jsonObject.get("PM10").toString());
        airQuality.setPM25(jsonObject.get("PM25").toString());
        airQuality.setSO2(jsonObject.get("SO2").toString());

        City cityObj = new City();
        cityObj.setName(jsonObject.get("city").toString());
        cityObj.setCountry(jsonObject.get("countryCode").toString());
        cityObj.setLat(jsonObject.get("lat").toString());
        cityObj.setLng(jsonObject.get("lng").toString());
        cityObj.setPostalCode(jsonObject.get("postalCode").toString());

        // Store Request Response In Cache
        cache.storeRequest(cityObj, airQuality);
        logger.log(Level.INFO, "Response stored in cache");


        data.put(cityObj, airQuality);
        return data;
    }

}


