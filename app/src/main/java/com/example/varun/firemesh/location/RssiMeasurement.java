package com.example.varun.firemesh.location;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Max Yang on 4/1/2017.
 */

public class RssiMeasurement implements Serializable {
    private String _bssid;
    private int _channel;
    private int _rssi;

    public RssiMeasurement(String bssid, int channel, int rssi) {
        _bssid = bssid;
        _channel = channel;
        _rssi = rssi;
    }

    public RssiMeasurement(byte[] bytes) {
        if (bytes.length != 14) {
            throw new RuntimeException("byte array should be of length 14");
        }
        byte[] bss = new byte[6];
        ByteBuffer.wrap(bytes).get(bss, 0, 6);
        _bssid = new String(bss);
        _channel = ByteBuffer.wrap(bytes).getInt(6);
        _rssi = ByteBuffer.wrap(bytes).getInt(10);
    }

    public String getBssid() {
        return _bssid;
    }

    public int getChannel() {
        return _channel;
    }

    public int getRssi() {
        return _rssi;
    }

    public byte[] serialize() {
        byte[] arr = new byte[14];
        ByteBuffer.wrap(arr).put(_bssid.getBytes());
        ByteBuffer.wrap(arr).putInt(6, _channel);
        ByteBuffer.wrap(arr).putInt(10, _rssi);
        return arr;
    }

}
