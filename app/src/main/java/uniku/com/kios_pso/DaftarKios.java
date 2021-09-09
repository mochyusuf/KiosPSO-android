package uniku.com.kios_pso;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uniku.com.kios_pso.API.APIService;
import uniku.com.kios_pso.API.APIUrl;
import uniku.com.kios_pso.Adapter.KiosAdapter;
import uniku.com.kios_pso.Model.Kios_Model;
import uniku.com.kios_pso.Model.Result_Kios_Model;
import uniku.com.kios_pso.Singleton.Singleton;

import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class DaftarKios extends AppCompatActivity {
    EditText editTextCari;
    Button button_cari;
    List<Kios_Model> list_kios;
    String[] nama_kios;

    RecyclerView recyclerView;

    private String TAG = "DaftarKios";

    private Kios_Model[] kios_models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kios);

        kios_models = Singleton.getInstance().getKios_models();

        recyclerView = findViewById(R.id.recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextCari = findViewById(R.id.editCari);
        button_cari = findViewById(R.id.button_cari);

        list_kios = new ArrayList<>();

        recyclerView.setAdapter(new KiosAdapter(kios_models, R.layout.list_kios, DaftarKios.this));

        nama_kios = new String[kios_models.length];
        for (int x = 0; x < kios_models.length; x++) {
            nama_kios[x] = kios_models[x].getNama_kios();
        }

        AutoComplete();

        button_cari.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pattern = editTextCari.getText().toString().toLowerCase();
                Cari(pattern);
            }

        });
    }

    private void Cari(String cari){
        final ProgressDialog progressDialog = new ProgressDialog(DaftarKios.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();
        progressDialog.setCancelable(false);
        if (checkInternet(DaftarKios.this)){
            APIService apiService =
                    APIUrl.getClient().create(APIService.class);

            Call<Result_Kios_Model> call = apiService.cariKios("cari",cari);

            call.enqueue(new Callback<Result_Kios_Model>() {
                @Override
                public void onResponse(Call<Result_Kios_Model>call, Response<Result_Kios_Model> response) {
                    kios_models = response.body().getKios_models();
                    Log.i(TAG, "onResponse: " + kios_models.length);
                    if (kios_models.length == 0){
                        progressDialog.dismiss();
                        Toast.makeText(DaftarKios.this, "Kios Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Log.i(TAG, "onResponse: Visible");
                        recyclerView.setAdapter(new KiosAdapter(kios_models, R.layout.list_kios, DaftarKios.this));
                    }
                }

                @Override
                public void onFailure(Call<Result_Kios_Model>call, Throwable t) {
                    Log.e(TAG, t.toString());
                    progressDialog.dismiss();
                    Toast.makeText(DaftarKios.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(DaftarKios.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void AutoComplete() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, nama_kios);
        AutoCompleteTextView actv = findViewById(R.id.editCari);
        actv.setThreshold(1);
        actv.setAdapter(adapter);
    }
}
