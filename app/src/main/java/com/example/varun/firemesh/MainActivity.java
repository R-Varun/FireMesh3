package com.example.varun.firemesh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.varun.firemesh.location.RssiMeasurement;
import com.example.varun.firemesh.location.RssiScanner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();
    WifiManager scanMngr;
    RssiScanner scanHandler;
    List<RssiMeasurement> scanMeasurements = new ArrayList<>();

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new BReceiver(mManager, mChannel, this);
        (findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast toast = Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT);
                        toast.show();
                        mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener () {
                            @Override
                            public void onPeersAvailable(WifiP2pDeviceList peers) {
                                Toast toast = Toast.makeText(getApplicationContext(), "peers: " + peers.toString(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }
                    @Override
                    public void onFailure(int reasonCode) {
                        Toast toast = Toast.makeText(getApplicationContext(), "error: " + reasonCode, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
        });
        */

        scanMngr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!scanMngr.isWifiEnabled()) {
            //enable wifi
        }

        RssiScanReceiver rssis = new RssiScanReceiver(scanMeasurements);
        registerReceiver(rssis, new IntentFilter( WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        scanMngr.startScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    public class RssiScanReceiver extends BroadcastReceiver {

        private List<RssiMeasurement> _measurements;

        public RssiScanReceiver(List<RssiMeasurement> meas) {
            _measurements = meas;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            for (ScanResult r : scanMngr.getScanResults()) {
                _measurements.add(new RssiMeasurement(r.BSSID, r.frequency, r.level));
            }
        }
    }
}
