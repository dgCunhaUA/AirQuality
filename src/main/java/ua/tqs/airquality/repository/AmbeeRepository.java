package ua.tqs.airquality.repository;

import org.springframework.stereotype.Repository;
import ua.tqs.airquality.model.City;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        System.out.println(response.body());

        if (response.statusCode() == 200)
            return response.body();
        else
            return "{\"message\":\"Error\"}";
    }


    /*
    public String getHistoricalAirQualityByCity(City city) throws IOException, InterruptedException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));


        System.out.println(city.getLat());
        System.out.println(city.getLng());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ambeedata.com/history/by-lat-lng?lat=" + city.getLat() + "&lng=" + city.getLng() + "&from=2021-05-05" + "%2012%3A16%3A44" + "&to=2021-05-12" + "%2012%3A16%3A44"))
                .header("x-api-key", "qjbhJdGl3w5bcR5Cyb07E7ymH3q29diM5mxLgGHg")
                .header("Content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        if (response.statusCode() == 200)
            return response.body();
        else
            return "{\"message\":\"Error\"}";
    }*/
}
