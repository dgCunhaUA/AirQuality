package ua.tqs.airquality.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceConvertersTest {

    @Test
    void whenConvertingSimpleString() {

        String response = "{\"message\":\"success\"}";

        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

        assertEquals("success", jsonMap.get("message").toString());
    }

    @Test
    void whenConvertingSimpleInvalidString() {

        String response = "{\"message\":\"success\"";
        String response2 = "{\"message\" \"success\"}";

        var gson = new Gson();
        assertThrows( com.google.gson.JsonSyntaxException.class, () -> { gson.fromJson(response, Map.class); });
        assertThrows( com.google.gson.JsonSyntaxException.class, () -> { gson.fromJson(response2, Map.class); });

    }

    @Test
    void whenConvertingArrayInString() {

        String response = "{\"message\":\"success\",\"stations\":[{\"_id\":\"1\",\"Array\":\"Yes\"}]}";

        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

        assertEquals("success", jsonMap.get("message").toString());

        ArrayList response_array = (ArrayList) jsonMap.get("stations");
        JsonObject jsonObject = gson.toJsonTree(response_array.get(0)).getAsJsonObject();

        assertEquals("\"1\"", jsonObject.get("_id").toString());
        assertEquals("\"Yes\"", jsonObject.get("Array").toString());
    }

    @Test
    void whenConvertingResponse() {

        String response = "{\"message\":\"success\",\"stations\":[{\"_id\":\"60363d6c8f2bb86af93ba8fb\",\"placeId\":\"591a22fa84590ec0d7381c22d8b2d0849e4c3dd7f645170b3f6bebe1fab31ba8\",\"CO\":0.20208333333333334,\"NO2\":7.836,\"OZONE\":3.449,\"PM10\":24.814,\"PM25\":6.8,\"SO2\":3.384,\"city\":\"Viseu\",\"countryCode\":\"PT\",\"division\":\"Viseu\",\"lat\":40.661,\"lng\":-7.9097,\"placeName\":\"Viseu\",\"postalCode\":\"3500-004\",\"state\":\"Viseu\",\"updatedAt\":\"2021-04-23 11:00:00\",\"AQI\":28,\"aqiInfo\":{\"pollutant\":\"PM2.5\",\"concentration\":6.8,\"category\":\"Good\"}}]}\n";

        var gson = new Gson();
        Map jsonMap = gson.fromJson(response, Map.class);

        // Converter o http response to json
        ArrayList response_array = (ArrayList) jsonMap.get("stations");
        JsonObject jsonObject = gson.toJsonTree(response_array.get(0)).getAsJsonObject();

        assertThat( jsonObject ).isInstanceOf(JsonObject.class);

        assertEquals("0.20208333333333334", jsonObject.get("CO").toString());
        assertEquals("7.836", jsonObject.get("NO2").toString());
        assertEquals("3.449", jsonObject.get("OZONE").toString());
        assertEquals("24.814", jsonObject.get("PM10").toString());
        assertEquals("6.8", jsonObject.get("PM25").toString());
        assertEquals("3.384", jsonObject.get("SO2").toString());

        assertEquals("\"Viseu\"", jsonObject.get("city").toString());
        assertEquals("\"PT\"", jsonObject.get("countryCode").toString());
        assertEquals("\"3500-004\"", jsonObject.get("postalCode").toString());
        assertEquals("\"Viseu\"", jsonObject.get("state").toString());
        assertEquals("40.661", jsonObject.get("lat").toString());
        assertEquals("-7.9097", jsonObject.get("lng").toString());
    }

}