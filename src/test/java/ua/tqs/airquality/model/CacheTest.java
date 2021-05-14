package ua.tqs.airquality.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.tqs.airquality.cache.Cache;

import java.util.HashMap;
import java.util.Map;
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

        assertEquals(0, cache.getLastRequests().size());
        assertEquals(0, cache.getExpiredRequests().size());
        assertEquals(0, cache.getNumOfRequests());
        assertEquals(0, cache.getNumOfHits());
        assertEquals(0, cache.getNumOfMisses());

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

        assertEquals(1, cache.getLastRequests().size());
        assertEquals(1, cache.getExpiredRequests().size());
        assertEquals(airQuality, cache.getLastRequests().get(city));
    }

    @Test
    void whenStoreRequestByCoords() {
        city.setLat("100");
        city.setLng("500");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);

        assertEquals(1, cache.getLastRequests().size());
        assertEquals(1, cache.getExpiredRequests().size());
        assertEquals(airQuality, cache.getLastRequests().get(city));
    }

    @Test
    void requestsIncr() {
        cache.requestsIncr();
        assertEquals(1, cache.getNumOfRequests());
    }

    @Test
    void hitsIncr() {
        cache.hitsIncr();
        assertEquals(1, cache.getNumOfHits());
    }

    @Test
    void missesIncr() {
        cache.missesIncr();
        assertEquals(1, cache.getNumOfMisses());
    }

    @Test
    void whenGetValidRequestByName() {
        String storedCity = "Test";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());


        HashMap<City, AirQuality> data = new HashMap<City, AirQuality>();
        data.put(city, airQuality);

        assertEquals(cache.getRequest(cache, storedCity), data);
        assertEquals(1, data.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(1, cache.getNumOfHits());
        assertEquals(0, cache.getNumOfMisses());
    }

    @Test
    void whenGetValidRequestByCoords() {
        String storedLat = "100";
        String storedLng = "200";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());


        HashMap<City, AirQuality> data = new HashMap<City, AirQuality>();
        data.put(city, airQuality);


        assertEquals(cache.getRequestLatLng(cache, storedLat, storedLng), data);
        assertEquals(1, data.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(1, cache.getNumOfHits());
        assertEquals(0, cache.getNumOfMisses());
    }

    @Test
    void whenGetInvalidRequestByName() {
        String NonStoredCity = "Non-stored-city";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());

        HashMap<City, AirQuality> data2 = new HashMap<City, AirQuality>();

        assertEquals(data2, cache.getRequest(cache, NonStoredCity));
        assertEquals(0, data2.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(0, cache.getNumOfHits());
        assertEquals(1, cache.getNumOfMisses());
    }

    @Test
    void whenGetInvalidRequestByCoords() {
        String NonStoredCityLat = "Non-stored-city";
        String NonStoredCityLng = "Non-stored-city";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());

        HashMap<City, AirQuality> data2 = new HashMap<City, AirQuality>();

        assertEquals(cache.getRequestLatLng(cache, NonStoredCityLat, NonStoredCityLng), data2);
        assertEquals(0, data2.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(0, cache.getNumOfHits());
        assertEquals(1, cache.getNumOfMisses());
    }

    @Test
    void whenGetExpiredRequestByName() throws InterruptedException {
        String search_city = "Test";
        city.setName("\"Test\"");
        airQuality.setCO("10");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());

        LOGGER.log(Level.INFO, "Waiting expiration time ...");
        TimeUnit.SECONDS.sleep(8);

        Map<City, AirQuality> data = cache.getRequest(cache, search_city);
        assertEquals(0, data.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(0, cache.getNumOfHits());
        assertEquals(1, cache.getNumOfMisses());
    }

    @Test
    void whenGetExpiredRequestByCoords() throws InterruptedException {
        String search_city_lat = "100";
        String search_city_lng = "200";

        city.setLat("100");
        city.setLng("200");
        airQuality.setCO("1");

        cache.storeRequest(city, airQuality);
        assertEquals(1, cache.getLastRequests().size());

        LOGGER.log(Level.INFO, "Waiting expiration time ...");
        TimeUnit.SECONDS.sleep(8);

        Map<City, AirQuality> data = cache.getRequestLatLng(cache, search_city_lat, search_city_lng);
        assertEquals(0, data.size());
        assertEquals(1, cache.getNumOfRequests());
        assertEquals(0, cache.getNumOfHits());
        assertEquals(1, cache.getNumOfMisses());
    }
}