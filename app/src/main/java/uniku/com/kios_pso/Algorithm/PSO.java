package uniku.com.kios_pso.Algorithm;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public class PSO {
    static LatLng posisi_awal;
    static ArrayList<LatLng> koordinat = new ArrayList<>();

    public int Run () {
        Swarm swarm;
        int particles, epochs;

        //Jumlah Partikel (tidak ada bedanya antara 1 dan 100000000000000000)
        particles = 1;

        //Sesuai dengan jumlah kios
        epochs = koordinat.size();

        //Buat Swarm (Kawanan)
        swarm = new Swarm(particles, epochs,posisi_awal,koordinat);

        return swarm.run();
    }

    public void SetPosisiAwal(LatLng Awal){
        posisi_awal = Awal;
    }

    public void AddPosisiKoordinat(LatLng Koordinat){
        koordinat.add(Koordinat);
    }
}
