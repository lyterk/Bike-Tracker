ackage us.bikelane.tracker.dao;

public class FetchedLocation {

    private static final String TAG = "dao.fetchedlocation";
    private long id, time;
    private double lat, lon, alt;
    private float speed, acc;

    public FetchedLocation(long id, double lat, double lon, long time, double alt, float speed, float acc) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.alt = alt;
        this.speed = speed;
        this.acc = acc;
    }

    @Override
    public String toString() {
        return id + ", " + lat + ", " + lon + ", " + time + ", " + alt + ", " + speed + ", " + acc;
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getAlt() {
        return alt;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcc() {
        return acc;
    }
}
