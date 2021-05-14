package ua.tqs.airquality.repository;

import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class AmbeeRepository {

    public String getCurrentAirQualityByCity(String city) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ambeedata.com/latest/by-city?city=" + city))
                .header("x-api-key", "qjbhJdGl3w5bcR5Cyb07E7ymH3q29diM5mxLgGHg")
                .header("Content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200)
            return response.body();
        else
            return "{\"message\":\"Error\"}";
    }


    public String getCurrentAirQualityByLatLng(String lat, String lng) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ambeedata.com/latest/by-lat-lng?lat=" + lat + "&lng=" + lng ))
                .header("x-api-key", "qjbhJdGl3w5bcR5Cyb07E7ymH3q29diM5mxLgGHg")
                .header("Content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200)
            return response.body();
        else
            return "{\"message\":\"Error\"}";
    }

}
