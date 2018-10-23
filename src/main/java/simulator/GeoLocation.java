package simulator;

public class GeoLocation {
    private double latitude;
    private double longitude;
    private static double EARTH_RADIUS = 6371.393;

    public GeoLocation (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getDistance (GeoLocation d_GeoLocation) {
        double radLat1 = rad(this.latitude);
        double radLat2 = rad(d_GeoLocation.latitude);
        double a = radLat1 - radLat2;
        double b = rad(this.longitude) - rad(d_GeoLocation.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
