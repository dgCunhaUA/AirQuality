package ua.tqs.airquality.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;
import ua.tqs.airquality.repository.AmbeeRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {

    @Mock
    private AmbeeRepository ambeeRepository;

    @InjectMocks
    private AirQualityService airQualityService;


    private static final Logger logger
            = Logger.getLogger(
            AirQualityServiceTest.class.getName());

    @Test
    void whenGetCityAirQualityByName_ThenReturnsAirQuality() throws IOException, InterruptedException {

        String response_body = "{\"message\":\"success\",\"stations\":[{\"_id\":\"60363d6c8f2bb86af93ba8fb\",\"placeId\":\"591a22fa84590ec0d7381c22d8b2d0849e4c3dd7f645170b3f6bebe1fab31ba8\",\"CO\":0.20208333333333334,\"NO2\":7.836,\"OZONE\":3.449,\"PM10\":24.814,\"PM25\":6.8,\"SO2\":3.384,\"city\":\"Viseu\",\"countryCode\":\"PT\",\"division\":\"Viseu\",\"lat\":40.661,\"lng\":-7.9097,\"placeName\":\"Viseu\",\"postalCode\":\"3500-004\",\"state\":\"Viseu\",\"updatedAt\":\"2021-04-23 11:00:00\",\"AQI\":28,\"aqiInfo\":{\"pollutant\":\"PM2.5\",\"concentration\":6.8,\"category\":\"Good\"}}]}\n";

        when( ambeeRepository.getCurrentAirQualityByCity("Viseu")).thenReturn(response_body);
        assertEquals( ambeeRepository.getCurrentAirQualityByCity("Viseu"), response_body );
        assertThat( ambeeRepository.getCurrentAirQualityByCity("Viseu") ).isInstanceOf(String.class);

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

        HashMap<City, AirQuality> expectedResponse = new HashMap<>();
        expectedResponse.put(cityObj, airQuality);

        HashMap<City, AirQuality> response = airQualityService.getCurrentAirQualityByCity("Viseu");
        assertThat(response).isInstanceOf(HashMap.class);
        assertEquals(response.toString(), expectedResponse.toString());
    }

    @Test
    void whenGetCityAirQualityByWrongName_ThenReturnsNotFoundMessage() throws IOException, InterruptedException {

        String response_body = "{\"message\":\"Sorry, we couldn't find the specified place\",\"data\":[]}";

        when( ambeeRepository.getCurrentAirQualityByCity("WrongName")).thenReturn(response_body);

        assertEquals( ambeeRepository.getCurrentAirQualityByCity("WrongName"), response_body );
        assertThat( ambeeRepository.getCurrentAirQualityByCity("WrongName") ).isInstanceOf(String.class);

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

        HashMap<City, AirQuality> expectedResponse = new HashMap<>();
        expectedResponse.put(cityObj, airQuality);

        logger.log(Level.INFO, String.valueOf(airQualityService.getCurrentAirQualityByCity("WrongName")));

        HashMap<City, AirQuality> response = airQualityService.getCurrentAirQualityByCity("WrongName");
        assertThat(response).isInstanceOf(HashMap.class);
        assertEquals(response.toString(), expectedResponse.toString());
    }

}