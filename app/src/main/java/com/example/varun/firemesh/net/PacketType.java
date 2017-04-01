package com.example.varun.firemesh.net;

/**
 * Created by Max Yang on 4/1/2017.
 */

public enum PacketType {

    GPS_READING(1),
    RSSI_READING(2),
    USER_INFO(4);

    int packId;

    PacketType(int packId) {
        this.packId = packId;
    }

    public byte getPackId() {
        return (byte) packId;
    }
}
