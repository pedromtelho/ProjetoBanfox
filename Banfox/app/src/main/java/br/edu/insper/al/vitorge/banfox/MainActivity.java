package br.edu.insper.al.vitorge.banfox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 0;
    private static final String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Global) this.getApplication()).setPictureNumber(0);

        askPermissions(this, permissions);

        if (checkPermissions(this, permissions)) {
            GPSTracker tracker = new GPSTracker(this);
            double longitude = tracker.getLongitude();
            double latitude = tracker.getLatitude();
            tracker.killGPS();
            ((Global) this.getApplication()).setUserLatitude(latitude);
            ((Global) this.getApplication()).setUserLongitude(longitude);
        }
        Button button = findViewById(R.id.home_btn);
        button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExplanationActivity.class)));
    }

    private static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void askPermissions(Context context, String... permissions) {
        if (checkPermissions(context, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }

}
