package com.example.varun.firemesh.net;

import android.content.Context;
import android.os.AsyncTask;

import com.example.varun.firemesh.location.RssiMeasurement;
import com.example.varun.firemesh.location.UserLocationProvider;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by Max Yang on 4/1/2017.
 */

public class LocSrvAsyncTask extends AsyncTask {

    private Context _context;
    private UserLocationProvider _prov;
    private List<RssiMeasurement> _rssis;

    public LocSrvAsyncTask(Context context) {
        _context = context;
    }

    public void setLocationProvider(UserLocationProvider prov) {
        _prov = prov;
    }

    public void setRssiMeasurements(List<RssiMeasurement> rssis) {
        _rssis = rssis;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Socket client = null;
        try {
            ServerSocket sock = new ServerSocket(9110);
            client = sock.accept();
            //if GPS data exists, write GPS data
            if (_prov.gpsLocationExists()) {
                client.getOutputStream().write(PacketType.GPS_READING.getPackId());
                client.getOutputStream().write(_prov.getCurrLoc().serialize());
            }
            //write RSSI information
            if (_rssis != null) {
                for (RssiMeasurement m : _rssis) {
                    client.getOutputStream().write(PacketType.RSSI_READING.getPackId());
                    client.getOutputStream().write(m.serialize());
                }
            }
            //write rescuee information
            client.close();
            sock.close();

        } catch (Exception e) {

        }
    }
}
