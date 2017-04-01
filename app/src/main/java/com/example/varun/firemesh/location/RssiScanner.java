package com.example.varun.firemesh.location;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Yang on 4/1/2017.
 */

public class RssiScanner extends BroadcastReceiver {

    private List<ScanResult> _measurements;

    public RssiScanner() {
    }

    public List<RssiMeasurement> getMeasurements() {
        List<RssiMeasurement> ret = new ArrayList<>();
        for (ScanResult r : _measurements) {
            ret.add(new RssiMeasurement(r.BSSID, r.frequency, r.level));
        }
        return ret;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
