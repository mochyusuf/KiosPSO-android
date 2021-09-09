package uniku.com.kios_pso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uniku.com.kios_pso.API.APIService;
import uniku.com.kios_pso.API.APIUrl;
import uniku.com.kios_pso.Adapter.GambarAdapter;
import uniku.com.kios_pso.Model.Gambar_Model;
import uniku.com.kios_pso.Model.Result_Gambar_Model;

import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class GambarKios extends AppCompatActivity {

    RecyclerView recyclerView;

    private String TAG = "Gambar_Kios";

    private Gambar_Model[] gambar_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gambar_kios);

        final ProgressDialog progressDialog = new ProgressDialog(GambarKios.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Intent intent = getIntent();
        int ID  = intent.getIntExtra("ID",0);

        if (checkInternet(GambarKios.this)){
            APIService apiService =
                    APIUrl.getClient().create(APIService.class);

            Call<Result_Gambar_Model> call = apiService.getGambar("gambar",ID);
            recyclerView =  findViewById(R.id.recyler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            call.enqueue(new Callback<Result_Gambar_Model>() {
                @Override
                public void onResponse(Call<Result_Gambar_Model>call, Response<Result_Gambar_Model> response) {

                    gambar_models = response.body().getGambar_models();
                    if (gambar_models.length > 0){
                        Log.i(TAG, "onResponse: " + gambar_models.length);
                        recyclerView.setAdapter(new GambarAdapter(gambar_models, R.layout.list_gambar, getApplicationContext()));
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(GambarKios.this, "Gambar Tidak Ditemukan", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Result_Gambar_Model>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                }
            });
        }else{
            progressDialog.dismiss();
            Toast.makeText(GambarKios.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
