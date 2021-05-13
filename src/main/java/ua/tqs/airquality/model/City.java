package ua.tqs.airquality.model;

public class City {

    private String name;
    private String country;
    private String lat;
    private String lng;
    private String postalCode;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
