package ua.tqs.airquality.model;

public class AirQuality {

    private String cO;
    private String nO2;
    private String oZONE;
    private String pM10;
    private String pM25;
    private String sO2;


    public String getCO() {
        return cO;
    }

    public void setCO(String cO) {
        this.cO = cO;
    }

    public String getNO2() {
        return nO2;
    }

    public void setNO2(String nO2) {
        this.nO2 = nO2;
    }

    public String getOZONE() {
        return oZONE;
    }

    public void setOZONE(String oZONE) {
        this.oZONE = oZONE;
    }

    public String getPM10() {
        return pM10;
    }

    public void setPM10(String pM10) {
        this.pM10 = pM10;
    }

    public String getPM25() {
        return pM25;
    }

    public void setPM25(String pM25) {
        this.pM25 = pM25;
    }

    public String getSO2() {
        return sO2;
    }

    public void setSO2(String sO2) {
        this.sO2 = sO2;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "CO='" + cO + '\'' +
                ", NO2='" + nO2 + '\'' +
                ", OZONE='" + oZONE + '\'' +
                ", PM10='" + pM10 + '\'' +
                ", PM25='" + pM25 + '\'' +
                ", SO2='" + sO2 + '\'' +
                '}';
    }
}
