/**
 * Created by Jimmy on 5/4/2015 in PACKAGE_NAME
 * 109259420
 * Homework 5
 * jimmy.ji@stonybrook.edu
 * Recitation 3: Sun Lin
 */
import com.google.code.geocoder.*;
import com.google.code.geocoder.model.*;
import latlng.LatLng;
import java.io.IOException;
import java.io.Serializable;

/**
 * City class that implements Serializable
 */
public class City implements Serializable {
    private String name;
    private LatLng location;
    private int indexPos;
    public static int cityCount;

    /**
     * Default constructor so that it doesn't add to city count
     */
    public City() {
    }

    /**
     * City constructor that adds to the city count
     * @param name is the name of the city
     * @throws IOException when geocoder doesn't work.
     */
    public City(String name)  throws IOException {
        this.indexPos = cityCount;
        cityCount++;
        this.name = name;
        this.location = obtainLocation(name);
    }

    /**
     * Obtains location of the city through geocoder
     * @param name is the name of the city
     * @return the LatLng object which is the location
     * @throws IOException when geocoder doesn't work
     */
    public LatLng obtainLocation(String name) throws IOException {

        Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest;
        GeocodeResponse geocodeResponse;
        double lat;
        double lng;

        geocoderRequest = new GeocoderRequestBuilder().setAddress(name).getGeocoderRequest();
        geocodeResponse = geocoder.geocode(geocoderRequest);
        lat = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
        lng = geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
        return new LatLng(lat, lng);
    }

    /**
     * Sets the city count
     * @param cityCount sets the city count to cityCount
     */
    public static void setCityCount(int cityCount) {
        City.cityCount = cityCount;
    }

    /**
     * Sets the index position
     * @param indexPos is the new index position
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * Returns the index position of the city
     * @return the index position of the city
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * Returns the LatLng object of the location
     * @return the LatLng object of the location
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Returns the name of the city
     * @return the name of the city
     */
    public String getName() {
        return name;
    }

    /**
     * String representation of the city for easy printing
     * @return the city and its longitude and latitude
     */
    @Override
    public String toString() {
        return String.format("%-27s%-15.6f%-6.6f", getName(), getLocation().getLat(), getLocation().getLng());
    }
}
