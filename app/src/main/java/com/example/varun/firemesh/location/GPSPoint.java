package com.example.varun.firemesh.location;

import org.gavaghan.geodesy.*;
/**
 * Created by Max Yang on 3/31/2017.
 */

public class GPSPoint {
    private double _lat;
    private double _lon;
    private double _alt;

    private static final GeodeticCalculator calc = new GeodeticCalculator();

    public GPSPoint(double lat, double lon, double alt) {
        _lat = lat;
        _lon = lon;
        _alt = alt;
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

    public double getDistFrom(GPSPoint pt2) {

        GlobalPosition p1 = new GlobalPosition(_lat, _lon, _alt);
        GlobalPosition p2 = new GlobalPosition(pt2._lat, pt2._lon, pt2._alt);

        GeodeticMeasurement meas = calc.calculateGeodeticMeasurement(Ellipsoid.WGS84, p1, p2);
        return meas.getPointToPointDistance();
    }
}
