package ua.tqs.airquality.cache;

import ua.tqs.airquality.model.AirQuality;
import ua.tqs.airquality.model.City;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cache {

    private long timeToLive;
    private int numOfRequests = 0;
    private int numOfHits = 0;
    private int numOfMisses = 0;
    private HashMap<City, AirQuality> lastRequests ;
    private HashMap<City, Long> expiredRequests ;

    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Cache(long expireTime) {
        this.lastRequests = new HashMap<City, AirQuality>();
        this.expiredRequests = new HashMap<>();
        this.timeToLive = expireTime;
    }


    public void storeRequest(City city, AirQuality airQuality) {
        LOGGER.log(Level.INFO, "Storing Request ...");
        this.lastRequests.put(city, airQuality);
        this.expiredRequests.put(city, System.currentTimeMillis() + timeToLive * 1000);
        LOGGER.log(Level.INFO, "Requests stored");
    }

    public HashMap<City, AirQuality> getRequest(Cache cache, String city) {
        LOGGER.log(Level.INFO, "Getting Request ...");
        HashMap<City, AirQuality> data = new HashMap<>();

        // Incr number os Request
        requestsIncr();

        for (City i : cache.getLastRequests().keySet()) {
            if( i.getName().substring(1, i.getName().length() - 1).equals(city) ) {

                if ( hasExpired(i) ) {
                    removeExpiredRequest(i);
                } else {
                    data.put(i, cache.getLastRequests().get(i));

                    hitsIncr();
                    return data;
                }

            }
        }

        missesIncr();
        return data;
    }

    public HashMap<City, AirQuality> getRequestLatLng(Cache cache, String lat, String lng) {
        LOGGER.log(Level.INFO, "Getting Request ...");
        HashMap<City, AirQuality> data = new HashMap<>();

        // Incr number os Request
        requestsIncr();

        for (City i : cache.getLastRequests().keySet()) {
            if( i.getLat().equals(lat) && i.getLng().equals(lng) ) {
                if ( hasExpired(i) ) {
                    LOGGER.log(Level.INFO, "Expired ...");
                    removeExpiredRequest(i);
                } else {
                    data.put(i, cache.getLastRequests().get(i));

                    hitsIncr();
                    return data;
                }
            }
        }

        missesIncr();
        return data;
    }

    public void requestsIncr() { numOfRequests++; }

    public void hitsIncr() { numOfHits++; }

    public void missesIncr() { numOfMisses++; }

    private boolean hasExpired(City city) {
        Long expireTime = expiredRequests.get(city);

        return System.currentTimeMillis() > expireTime;
    }

    private void removeExpiredRequest(City city) {
        LOGGER.log(Level.INFO, "Removing Expired Requests ...");

        lastRequests.remove(city);
        expiredRequests.remove(city);

        LOGGER.log(Level.INFO, "Expired Requests Removed");
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getNumOfRequests() {
        return numOfRequests;
    }

    public void setNumOfRequests(int numOfRequests) {
        this.numOfRequests = numOfRequests;
    }

    public int getNumOfHits() {
        return numOfHits;
    }

    public void setNumOfHits(int numOfHits) {
        this.numOfHits = numOfHits;
    }

    public int getNumOfMisses() {
        return numOfMisses;
    }

    public void setNumOfMisses(int numOfMisses) {
        this.numOfMisses = numOfMisses;
    }

    public HashMap<City, AirQuality> getLastRequests() {
        return lastRequests;
    }

    public void setLastRequests(HashMap<City, AirQuality> cache) {
        this.lastRequests = cache;
    }

    public HashMap<City, Long> getExpiredRequests() {
        return expiredRequests;
    }

    public void setExpiredRequests(HashMap<City, Long> expiredRequests) {
        this.expiredRequests = expiredRequests;
    }
}
