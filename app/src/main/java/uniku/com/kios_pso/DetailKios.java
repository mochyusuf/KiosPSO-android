package uniku.com.kios_pso;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import uniku.com.kios_pso.Galeri.Galeri_Kios;
import uniku.com.kios_pso.Model.Kios_Model;
import uniku.com.kios_pso.Singleton.Singleton;

import static uniku.com.kios_pso.Helper.checkInternet.Rupiah;
import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class DetailKios extends AppCompatActivity {

    TextView nama_kios;
    TextView luas_kios;
    TextView alamat;
    TextView nomor_telepon;
    TextView fasilitas;
    TextView harga_pembayaran;
    ImageView gambar_kios,button_telepon;


    private Button button_gambar,button_rute;

    LocationManager locationManager ;

    boolean GpsStatus ;

    int ID,position;
    private Kios_Model[] kios_models;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kios);

        nama_kios = findViewById(R.id.nama_kios);
        luas_kios = findViewById(R.id.luas_kios);
        alamat = findViewById(R.id.alamat);
        nomor_telepon = findViewById(R.id.nomor_telepon);
        fasilitas = findViewById(R.id.fasilitas);
        harga_pembayaran = findViewById(R.id.harga_pembayaran);
        gambar_kios = findViewById(R.id.gambar_kios);
        button_gambar = findViewById(R.id.button_gambar);
        button_telepon = findViewById(R.id.button_telepon);
        button_rute = findViewById(R.id.button_rute);

        GpsStatus = false;
        context = this;
        kios_models = Singleton.getInstance().getKios_models();

        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", 0);
        position = GetPosition(ID);

        nama_kios.setText(kios_models[position].getNama_kios());
        luas_kios.setText(kios_models[position].getLuas_kios()+" m²");
        alamat.setText(kios_models[position].getAlamat());
        nomor_telepon.setText(kios_models[position].getNomor_telepon());
        fasilitas.setText(Html.fromHtml(kios_models[position].getFasilitas()));
        String temp = "";
        switch (kios_models[position].getJenis_pembayaran()){
            case "bulanan":
                temp = " / Bulan";
                break;
            case "tahunan":
                temp = " / Tahun";
                break;
        }
        harga_pembayaran.setText(Rupiah(kios_models[position].getHarga_pembayaran()) + temp);
        Picasso.with(this).load(kios_models[position].getGambar_kios()).resize(645, 480).into(gambar_kios);

        button_gambar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(DetailKios.this,GambarKios.class);
                Intent intent = new Intent(DetailKios.this,Galeri_Kios.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        button_telepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(DetailKios.this)
                        .withPermission(
                                Manifest.permission.CALL_PHONE
                        )
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+nomor_telepon.getText()));
                                if (ActivityCompat.checkSelfPermission(DetailKios.this,
                                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                startActivity(callIntent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(DetailKios.this, "Aplikasi Membutuhkan Izin Telepon", Toast.LENGTH_SHORT).show();
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

        button_rute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(DetailHotel.this,Peta.class);
                Dexter.withActivity(DetailKios.this)
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
                                    if (checkInternet(DetailKios.this)){
                                        Intent intent = new Intent(DetailKios.this, Peta.class);
                                        intent.setAction(Intent.ACTION_VIEW);
                                        String latitude = String.valueOf(kios_models[position].getLatitude());
                                        String longitude = String.valueOf(kios_models[position].getLongitude());
                                        Uri gmmIntentUri = Uri.parse("geo:"+ latitude +","+longitude);
                                        intent.setData(gmmIntentUri);
                                        intent.putExtra("position",position);
                                        intent.setPackage("com.google.android.apps.maps");
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(DetailKios.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(DetailKios.this, "Aktifkan GPS", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(DetailKios.this, "Aplikasi Membutuhkan Izin GPS", Toast.LENGTH_SHORT).show();
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

    private int GetPosition(int ID){
        int result = 0;
        for (int i = 0; i < kios_models.length;i++){
            if (ID == kios_models[i].getId_kios()){
                return i;
            }
        }
        return result;
    }
}
