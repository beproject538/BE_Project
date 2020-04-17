package com.example.arijitlahiri.be_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import com.google.zxing.Result;

public class QRScanActivity extends AppCompatActivity implements  ZXingScannerView.ResultHandler{

    ImageButton cancel;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        //setContentView(mScannerView);
        setContentView(R.layout.activity_qrscan);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(QRScanActivity.this, new String[] {android.Manifest.permission.CAMERA}, 1);

        }


        cancel = findViewById(R.id.cancel);
        mScannerView = (ZXingScannerView) findViewById(R.id.zxscan);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRScanActivity.this.startActivity(new Intent(QRScanActivity.this, MainActivity.class));
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("Scan Result", rawResult.getText()); // Prints scan results
        Log.v("Scan Format", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(QRScanActivity.this, AcceptActivity.class);
        i.putExtra("result",rawResult.getText());
        QRScanActivity.this.startActivity(i);

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
