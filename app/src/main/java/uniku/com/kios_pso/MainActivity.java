package uniku.com.kios_pso;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import uniku.com.kios_pso.Model.Kios_Model;

import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class MainActivity extends AppCompatActivity {
    private CardView btn_kios_terdekat,btn_daftar_kios,btn_info_aplikasi;

    Context context;
    LocationManager locationManager ;

    boolean GpsStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        GpsStatus = false;
        btn_kios_terdekat = findViewById(R.id.btn_kios_terdekat);
        btn_daftar_kios = findViewById(R.id.btn_daftar_kios);
        btn_info_aplikasi = findViewById(R.id.btn_info_aplikasi);

        btn_kios_terdekat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

                                GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                if(GpsStatus == true)
                                {
                                    if (checkInternet(MainActivity.this)){
                                        Intent intent = new Intent(MainActivity.this,KiosTerdekat.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(MainActivity.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(MainActivity.this, "Aktifkan GPS", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(MainActivity.this, "Aplikasi Membutuhkan Izin GPS", Toast.LENGTH_SHORT).show();
                                if (response.isPermanentlyDenied()) {
                                    // open device settings when the permission is
                                    // denied permanently
                                    openSettings();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        btn_info_aplikasi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoAplikasi.class);
                startActivity(intent);
            }
        });

        btn_daftar_kios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DaftarKios.class);
                startActivity(intent);
            }
        });
    }



    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
