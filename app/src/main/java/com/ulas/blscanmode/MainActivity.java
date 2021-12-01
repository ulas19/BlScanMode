package com.ulas.blscanmode;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    IntentFilter scanIntentFilter=new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)){
                int modeValue = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);
                if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE){
                    textView.setText("The device is not in discoverable mode but can stillvreceive connection");
                }
                else if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                    textView.setText("The devicein discoverable mode.");
                }
                else if (modeValue == BluetoothAdapter.SCAN_MODE_NONE){
                    textView.setText("The device is not in discoverable mode and cannot receive connection");
                }
                else {
                    textView.setText("Eroor");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,8);
                startActivity(discoverable);
            }
        });
        registerReceiver(broadcastReceiver,scanIntentFilter);
    }
}