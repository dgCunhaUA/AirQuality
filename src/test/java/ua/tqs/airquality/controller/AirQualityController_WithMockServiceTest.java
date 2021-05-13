package ua.tqs.airquality.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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


@WebMvcTest(AirQualityRestController.class)
class AirQualityController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService airQualityService;


    /*
    //@Test
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

        //TODO: Erro ao fazer get
        City testeCity = new City();
        testeCity.setName("\"Viseu\"");

        /*mvc.perform(get("/air-quality")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("city", testeCity))
                .andExpect(status().isOk())
                .andExpect(view().name("results"));


        //TODO:
        mvc.perform(get("/air-quality").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Viseu")
                )
                .andExpect(status().isOk());

        verify(airQualityService, times(1)).getCurrentAirQualityByCity("Viseu");

        /*
        mvc.perform(post("/air-quality").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Viseu"))
                .andDo( print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("city", Matchers.<City>
                        hasProperty("name", containsString("Viseu"))));

    }
   */



    //@Test
    /*
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

        when( airQualityService.getCurrentAirQualityByCity("Viseu") ).thenReturn(response);


        //TODO:
        mvc.perform(get("/air-quality?name=x;zx'zw").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"City{name='City Not Found', country='-', lat='-', lng='-', postalCode='-'}\":{\"co\":\"-\",\"no2\":\"-\",\"ozone\":\"-\",\"pm10\":\"-\",\"pm25\":\"-\",\"so2\":\"-\"}}")
                );

    }

     */

}
