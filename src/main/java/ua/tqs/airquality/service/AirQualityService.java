package ua.tqs.airquality.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.cache.Cache;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.repository.AmbeeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap<City, AirQuality> getCurrentAirQualityByCity(String city) throws IOException, InterruptedException {

        // Verificar se ja existe em cache
        logger.log(Level.INFO, "Cache verification ...");
        HashMap<City, AirQuality> data = cache.getRequest(cache, city);

        if( data.size() == 1 ) {
            logger.log(Level.INFO, "Found in cache");
            return data;
        }

        logger.log(Level.INFO, "Not found in cache");

        // Make the request to the external API
        logger.log(Level.INFO, "External API Request");
        String response = ambeeRepository.getCurrentAirQualityByCity(city);
        logger.log(Level.INFO, "External API Response: " + response);


        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

        if( jsonMap.get("message").equals("success") ) {
            logger.log(Level.INFO, "Response with success");
        }
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
        ArrayList response_array = (ArrayList) jsonMap.get("stations");
        JsonObject jsonObject = gson.toJsonTree(response_array.get(0)).getAsJsonObject();


        // Recolher os valores
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

    public HashMap<City, AirQuality> getCurrentAirQualityByLatLng(String lat, String lng) throws IOException, InterruptedException {

        logger.log(Level.INFO, "Cache verification ...");
        HashMap<City, AirQuality> data = cache.getRequestLatLng(cache, lat, lng);


        if( data.size() == 1 ) {
            logger.log(Level.INFO, "Found in cache");
            return data;
        }
        logger.log(Level.INFO, "Not found in cache");


        // Make the request to the external API
        logger.log(Level.INFO, "External API Request");
        String response = ambeeRepository.getCurrentAirQualityByLatLng(lat, lng);
        logger.log(Level.INFO, "External API Response: " + response);


        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

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
        ArrayList response_array = (ArrayList) jsonMap.get("stations");
        JsonObject jsonObject = gson.toJsonTree(response_array.get(0)).getAsJsonObject();


        // Recolher os valores
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

        logger.log(Level.INFO, data.toString());

        return data;
    }

    /*
    public HashMap<City, AirQuality> getHistoricalAirQualityByCity(City city) throws IOException, InterruptedException {

        String response = null;
        for(City i : cache.getLastRequests().keySet() ) {
            System.out.println(i.getName());
            System.out.println(city.getName());
            if( i.getName().equals(city.getName())) {
                logger.log(Level.INFO, "External API Request");
                response = ambeeRepository.getHistoricalAirQualityByCity(city);
            }
        }

        if (response == null)
            logger.log(Level.WARNING, "City not found in cache, cant make history error.");


        // Make the request to the external API
        logger.log(Level.INFO, "External API Request");
        //String response = ambeeRepository.getHistoricalAirQualityByCity(city);
        logger.log(Level.INFO, "External API Response: " + response);

        // Incr number os Request
        //cache.requestsIncr();
        //logger.log(Level.INFO, "Cache requests increased");

        //HashMap<City, AirQuality> data = cache.getRequest(cache, city.getName());
        HashMap<City, AirQuality> data = new HashMap<>();


        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

        if( jsonMap.get("message").equals("success") ) {
            //cache.hitsIncr();
            logger.log(Level.INFO, "Response with success");
        }
        else {
            //cache.missesIncr();
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

            //data.put(cityObj, airQuality);

            //return data;
        }

        logger.log(Level.INFO, "Converting response...");
        // Converter o http response to json
        ArrayList response_array = (ArrayList) jsonMap.get("stations");
        JsonObject jsonObject = gson.toJsonTree(response_array.get(0)).getAsJsonObject();


        // Recolher os valores
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
    */


    public Cache getCacheStatistics() {
        logger.log(Level.INFO, "Returning cache statistics");
        return cache;
    }
}


