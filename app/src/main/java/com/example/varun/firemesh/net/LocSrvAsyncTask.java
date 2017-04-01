package com.example.varun.firemesh.net;

import android.content.Context;
import android.os.AsyncTask;

import com.example.varun.firemesh.location.UserLocationProvider;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Max Yang on 4/1/2017.
 */

public class LocSrvAsyncTask extends AsyncTask {

    private Context _context;
    private UserLocationProvider _prov;

    public LocSrvAsyncTask(Context context) {
        _context = context;
    }

    public void setLocationProvider(UserLocationProvider prov) {
        _prov = prov;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            ServerSocket sock = new ServerSocket(9110);
            Socket client = sock.accept();
            //if GPS data exists, write GPS data
            if (_prov.gpsLocationExists()) {
                client.getOutputStream().write(PacketType.GPS_READING.getPackId());
                client.getOutputStream().write(_prov.getCurrLoc().serialize());
            }
            //write RSSI information

            //write rescuee information

        } catch (Exception e) {

        }
    }
}
