package ua.tqs.airquality.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.tqs.airquality.cache.Cache;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


class CacheTest {

    @Autowired
    Cache cache;

    @Autowired
    City city;

    @Autowired
    AirQuality airQuality;


    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @BeforeEach
    void setUp() {
        cache = new Cache(5L);

        assertEquals(cache.getLastRequests().size(), 0);
        assertEquals(cache.getExpiredRequests().size(), 0);
        assertEquals(cache.getNumOfRequests(), 0);
        assertEquals(cache.getNumOfHits(), 0);
        assertEquals(cache.getNumOfMisses(), 0);

        city = new City();
        airQuality = new AirQuality();
    }

    @AfterEach
    void tearDown() {
        cache.getLastRequests().clear();
        cache.getExpiredRequests().clear();
        cache.setNumOfRequests(0);
        cache.setNumOfHits(0);
        cache.setNumOfMisses(0);
    }

    @Test
    void whenStoreRequestByName() {
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);

        assertEquals(cache.getLastRequests().size(), 1);
        assertEquals(cache.getExpiredRequests().size(), 1);
        assertEquals(cache.getLastRequests().get(city), airQuality);
    }

    @Test
    void whenStoreRequestByCoords() {
        city.setLat("100");
        city.setLng("500");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);

        assertEquals(cache.getLastRequests().size(), 1);
        assertEquals(cache.getExpiredRequests().size(), 1);
        assertEquals(cache.getLastRequests().get(city), airQuality);
    }

    @Test
    void requestsIncr() {
        cache.requestsIncr();
        assertEquals(cache.getNumOfRequests(), 1);
    }

    @Test
    void hitsIncr() {
        cache.hitsIncr();
        assertEquals(cache.getNumOfHits(), 1);
    }

    @Test
    void missesIncr() {
        cache.missesIncr();
        assertEquals(cache.getNumOfMisses(), 1);
    }

    @Test
    void whenGetValidRequestByName() {
        String storedCity = "Test";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);


        HashMap<City, AirQuality> data = new HashMap<City, AirQuality>();
        data.put(city, airQuality);

        assertEquals(cache.getRequest(cache, storedCity), data);
        assertEquals(data.size(), 1);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 1);
        assertEquals(cache.getNumOfMisses(), 0);
    }

    @Test
    void whenGetValidRequestByCoords() {
        String storedLat = "100";
        String storedLng = "200";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);


        HashMap<City, AirQuality> data = new HashMap<City, AirQuality>();
        data.put(city, airQuality);


        assertEquals(cache.getRequestLatLng(cache, storedLat, storedLng), data);
        assertEquals(data.size(), 1);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 1);
        assertEquals(cache.getNumOfMisses(), 0);
    }

    @Test
    void whenGetInvalidRequestByName() {
        String NonStoredCity = "Non-stored-city";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);

        HashMap<City, AirQuality> data2 = new HashMap<City, AirQuality>();

        assertEquals(cache.getRequest(cache, NonStoredCity), data2);
        assertEquals(data2.size(), 0);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 0);
        assertEquals(cache.getNumOfMisses(), 1);
    }

    @Test
    void whenGetInvalidRequestByCoords() {
        String NonStoredCityLat = "Non-stored-city";
        String NonStoredCityLng = "Non-stored-city";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);

        HashMap<City, AirQuality> data2 = new HashMap<City, AirQuality>();

        assertEquals(cache.getRequestLatLng(cache, NonStoredCityLat, NonStoredCityLng), data2);
        assertEquals(data2.size(), 0);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 0);
        assertEquals(cache.getNumOfMisses(), 1);
    }

    @Test
    void whenGetExpiredRequestByName() throws InterruptedException {
        String search_city = "Test";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);

        LOGGER.log(Level.INFO, "Waiting expiration time ...");
        TimeUnit.SECONDS.sleep(8);

        HashMap<City, AirQuality> data = cache.getRequest(cache, search_city);
        assertEquals(data.size(), 0);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 0);
        assertEquals(cache.getNumOfMisses(), 1);
    }

    @Test
    void whenGetExpiredRequestByCoords() throws InterruptedException {
        String search_city_lat = "100";
        String search_city_lng = "200";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(cache.getLastRequests().size(), 1);

        LOGGER.log(Level.INFO, "Waiting expiration time ...");
        TimeUnit.SECONDS.sleep(8);

        HashMap<City, AirQuality> data = cache.getRequestLatLng(cache, search_city_lat, search_city_lng);
        assertEquals(data.size(), 0);
        assertEquals(cache.getNumOfRequests(), 1);
        assertEquals(cache.getNumOfHits(), 0);
        assertEquals(cache.getNumOfMisses(), 1);
    }
}