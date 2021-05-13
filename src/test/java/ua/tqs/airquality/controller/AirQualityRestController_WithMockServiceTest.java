package ua.tqs.airquality.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.airquality.cache.Cache;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.service.AirQualityService;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirQualityRestController.class)
public class AirQualityRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService airQualityService;


    @Test
    public void whenGetAirQualityByCityName_thenReturnData( ) throws Exception {

        AirQuality airQuality = new AirQuality();
        airQuality.setCO("0.20208333333333334");
        airQuality.setNO2("7.836");
        airQuality.setOZONE("3.449");
        airQuality.setPM10("24.814");
        airQuality.setPM25("6.8");
        airQuality.setSO2("3.384");

        City cityObj = new City();
        cityObj.setName("\"Viseu\"");
        cityObj.setCountry("\"PT\"");
        cityObj.setLat("40.661");
        cityObj.setLng("-7.9097");
        cityObj.setPostalCode("\"3500-004\"");

        HashMap<City, AirQuality> response = new HashMap<>();
        response.put(cityObj, airQuality);

        when( airQualityService.getCurrentAirQualityByCity("Viseu") ).thenReturn(response);

        mvc.perform(get("/api/air-quality/Viseu").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"City{name='\\\"Viseu\\\"', country='\\\"PT\\\"', lat='40.661', lng='-7.9097', postalCode='\\\"3500-004\\\"'}\":{\"co\":\"0.20208333333333334\",\"no2\":\"7.836\",\"ozone\":\"3.449\",\"pm10\":\"24.814\",\"pm25\":\"6.8\",\"so2\":\"3.384\"}}")
                );

        verify(airQualityService, times(1)).getCurrentAirQualityByCity("Viseu");
    }

    @Test
    public void whenGetAirQualityByWrongCityName_thenReturnData( ) throws Exception {

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

        HashMap<City, AirQuality> response = new HashMap<>();
        response.put(cityObj, airQuality);

        when( airQualityService.getCurrentAirQualityByCity("Errorrrrr") ).thenReturn(response);

        mvc.perform(get("/api/air-quality/Errorrrrr").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"City{name='City Not Found', country='-', lat='-', lng='-', postalCode='-'}\":{\"co\":\"-\",\"no2\":\"-\",\"ozone\":\"-\",\"pm10\":\"-\",\"pm25\":\"-\",\"so2\":\"-\"}}")
                );

        verify(airQualityService, times(1)).getCurrentAirQualityByCity("Errorrrrr");
    }

    @Test
    public void whenGetStats_thenReturnEmptyData() throws Exception {

        Cache returnedCache = new Cache(100L);
        returnedCache.setTimeToLive(100);
        returnedCache.setNumOfHits(0);
        returnedCache.setNumOfMisses(0);
        returnedCache.setNumOfRequests(0);


        when( airQualityService.getCacheStatistics() ).thenReturn(returnedCache);

        mvc.perform(get("/api/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"timeToLive\":100,\"numOfRequests\":0,\"numOfHits\":0,\"numOfMisses\":0,\"lastRequests\":{},\"expiredRequests\":{}}")
                );

        verify(airQualityService, times(1)).getCacheStatistics();
    }

    @Test
    public void whenGetStats_thenReturnDataWithLatestRequests() throws Exception {

        AirQuality airQuality = new AirQuality();
        airQuality.setCO("0.20208333333333334");
        airQuality.setNO2("7.836");
        airQuality.setOZONE("3.449");
        airQuality.setPM10("24.814");
        airQuality.setPM25("6.8");
        airQuality.setSO2("3.384");

        City city = new City();
        city.setName("\"Viseu\"");
        city.setCountry("\"PT\"");
        city.setLat("40.661");
        city.setLng("-7.9097");
        city.setPostalCode("\"3500-004\"");

        HashMap<City, AirQuality> lastRequests = new HashMap<>();
        lastRequests.put(city, airQuality);

        HashMap<City, Long> expiredRequests = new HashMap<>();
        expiredRequests.put(city, 1620839194093L);

        Cache returnedCache = new Cache(100L);
        returnedCache.setTimeToLive(100);
        returnedCache.setNumOfHits(0);
        returnedCache.setNumOfMisses(1);
        returnedCache.setNumOfRequests(1);
        returnedCache.setLastRequests(lastRequests);
        returnedCache.setExpiredRequests(expiredRequests);

        when( airQualityService.getCacheStatistics() ).thenReturn(returnedCache);

        mvc.perform(get("/api/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"timeToLive\":100,\"numOfRequests\":1,\"numOfHits\":0,\"numOfMisses\":1,\"lastRequests\":{\"City{name='\\\"Viseu\\\"', country='\\\"PT\\\"', lat='40.661', lng='-7.9097', postalCode='\\\"3500-004\\\"'}\":{\"co\":\"0.20208333333333334\",\"no2\":\"7.836\",\"ozone\":\"3.449\",\"pm10\":\"24.814\",\"pm25\":\"6.8\",\"so2\":\"3.384\"}},\"expiredRequests\":{\"City{name='\\\"Viseu\\\"', country='\\\"PT\\\"', lat='40.661', lng='-7.9097', postalCode='\\\"3500-004\\\"'}\":1620839194093}}")
                );

        verify(airQualityService, times(1)).getCacheStatistics();
    }
}
