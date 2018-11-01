package techmatics.gogetmydrink;

/**
 * Created by ashish.kumar on 18-10-2018.
 */

public class DeliveryLocationModel {
    String locationName;String lat; String lon;
    public DeliveryLocationModel(String locationName,String lat, String lon)
    {
        this.lat=lat;
        this.locationName=locationName;
        this.lon=lon;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
