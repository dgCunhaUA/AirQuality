package ua.tqs.airquality.model;

public class AirQuality {

    private String CO;
    private String NO2;
    private String OZONE;
    private String PM10;
    private String PM25;
    private String SO2;


    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getOZONE() {
        return OZONE;
    }

    public void setOZONE(String OZONE) {
        this.OZONE = OZONE;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getPM25() {
        return PM25;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "CO='" + CO + '\'' +
                ", NO2='" + NO2 + '\'' +
                ", OZONE='" + OZONE + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", PM25='" + PM25 + '\'' +
                ", SO2='" + SO2 + '\'' +
                '}';
    }
}
