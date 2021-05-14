package ua.tqs.airquality.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmbeeRepositoryTest {

    @InjectMocks
    private AmbeeRepository ambeeRepository;

    @Test
    void whenGetCityAirQuality_thenReturnAirQuality() throws IOException, InterruptedException {
        assertThat( ambeeRepository.getCurrentAirQualityByCity("Viseu") ).isInstanceOf(String.class);
    }

    @Test
    void whenGetInvalidCityNameAirQuality_thenReturnError() throws IOException, InterruptedException {
        String invalidCityName = "-z;'qy-0)x";
        assertThat( ambeeRepository.getCurrentAirQualityByCity(invalidCityName) ).isInstanceOf(String.class);
        assertEquals( "{\"message\":\"Error\"}", ambeeRepository.getCurrentAirQualityByCity(invalidCityName) );
    }

    @Test
    void whenGetCityAirQualityByLatLng_thenReturnAirQuality() throws IOException, InterruptedException {
        assertThat( ambeeRepository.getCurrentAirQualityByLatLng("---", "---") ).isInstanceOf(String.class);
    }

    @Test
    void whenGetAirQualityByInvalidLatLng_thenReturnError() throws IOException, InterruptedException {
        String invalidCityLat = "-z;'qy-0)x";
        String invalidCityLng = "----";
        assertThat( ambeeRepository.getCurrentAirQualityByLatLng(invalidCityLat, invalidCityLng) ).isInstanceOf(String.class);
        assertEquals( "{\"message\":\"Error\"}", ambeeRepository.getCurrentAirQualityByLatLng(invalidCityLat, invalidCityLng) );
    }
}