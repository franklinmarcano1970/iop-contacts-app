package org.libertaria.world.global;

import java.io.Serializable;

public class GpsLocation implements Serializable {
    float latitude;
    float longitude;

    public GpsLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }
}