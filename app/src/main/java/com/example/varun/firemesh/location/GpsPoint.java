package com.example.varun.firemesh.location;

import org.gavaghan.geodesy.*;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Max Yang on 3/31/2017.
 */

public class GpsPoint implements Serializable {
    private double _lat;
    private double _lon;
    private double _alt;

    private static final GeodeticCalculator calc = new GeodeticCalculator();

    public GpsPoint(double lat, double lon, double alt) {
        _lat = lat;
        _lon = lon;
        _alt = alt;
    }

    public GpsPoint(byte[] arr) {
        if (arr.length != 24) {
            throw new RuntimeException("Array input should be 24 bytes long");
        }
        _lat = ByteBuffer.wrap(arr).getDouble();
        _lon = ByteBuffer.wrap(arr).getDouble(8);
        _alt = ByteBuffer.wrap(arr).getDouble(16);
    }

    public double getLatitude() {
        return _lat;
    }

    public double getLongitude() {
        return _lon;
    }

    public double getAltitude() {
        return _alt;
    }

    public double getDistFrom(GpsPoint pt2) {

        GlobalPosition p1 = new GlobalPosition(_lat, _lon, _alt);
        GlobalPosition p2 = new GlobalPosition(pt2._lat, pt2._lon, pt2._alt);

        GeodeticMeasurement meas = calc.calculateGeodeticMeasurement(Ellipsoid.WGS84, p1, p2);
        return meas.getPointToPointDistance();
    }

    public byte[] serialize() {
        byte[] data = new byte[24];
        ByteBuffer.wrap(data).putDouble(_lat);
        ByteBuffer.wrap(data).putDouble(8, _lon);
        ByteBuffer.wrap(data).putDouble(16, _alt);
        return data;
    }
}
