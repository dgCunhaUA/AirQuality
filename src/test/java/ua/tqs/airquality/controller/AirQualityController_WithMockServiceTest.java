package ua.tqs.airquality.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.service.AirQualityService;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AirQualityController.class)
class AirQualityController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService airQualityService;



    @Test
    void whenGetAirQualityByCityName_thenReturnData() throws Exception {

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


        when(airQualityService.getCurrentAirQualityByCity("Viseu")).thenReturn(response);


        mvc.perform(get("/air-quality?name=Viseu").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("name", containsString("Viseu"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lat", containsString("40.661"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lng", containsString("-7.9097"))))
                .andExpect(view().name("results"));

        verify(airQualityService, times(1)).getCurrentAirQualityByCity("Viseu");
    }


    @Test
    void whenGetAirQualityByWrongCityName_thenReturnData() throws Exception {

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

        when( airQualityService.getCurrentAirQualityByCity("WrongName") ).thenReturn(response);


        mvc.perform(get("/air-quality?name=WrongName").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("name", containsString("City Not Found"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lat", containsString("-"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lng", containsString("-"))))
                .andExpect(view().name("results"));

        verify(airQualityService, times(1)).getCurrentAirQualityByCity("WrongName");
    }



    @Test
    void whenGetAirQualityByLatLng_thenReturnData() throws Exception {

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


        when( airQualityService.getCurrentAirQualityByLatLng("40.661", "-7.9097") ).thenReturn(response);


        mvc.perform(get("/air-quality-lat-lng?lat=40.661&lng=-7.9097").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("name", containsString("Viseu"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lat", containsString("40.661"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lng", containsString("-7.9097"))))
                .andExpect(view().name("results"));

        verify(airQualityService, times(1)).getCurrentAirQualityByLatLng("40.661", "-7.9097");
    }

    @Test
    void whenGetAirQualityByInvalidLatLng_thenReturnData() throws Exception {

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


        when( airQualityService.getCurrentAirQualityByLatLng("--", "----") ).thenReturn(response);


        mvc.perform(get("/air-quality-lat-lng?lat=--&lng=----").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("name", containsString("City Not Found"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lat", containsString("-"))))
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("lng", containsString("-"))))
                .andExpect(view().name("results"));


        verify(airQualityService, times(1)).getCurrentAirQualityByLatLng("--", "----");
    }
}
