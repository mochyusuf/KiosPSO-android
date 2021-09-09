package uniku.com.kios_pso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uniku.com.kios_pso.API.APIService;
import uniku.com.kios_pso.API.APIUrl;
import uniku.com.kios_pso.Model.Kios_Model;
import uniku.com.kios_pso.Model.Result_Kios_Model;
import uniku.com.kios_pso.Singleton.Singleton;

import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class SplashScreen extends AppCompatActivity {

    private Singleton Singleton;

    private Kios_Model[] kios_models;
    private String TAG = "Splash_Screen";
    CardView btn_Try_Again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Singleton = Singleton.getInstance();

        btn_Try_Again = findViewById(R.id.try_again);


        Intent intent = getIntent();
        String status = intent.getStringExtra("EXIT");
        if (status != null){
            finish();
        }else{
            TryAgain();
        }

        btn_Try_Again.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TryAgain();
            }
        });
    }

    private void TryAgain(){
        if (checkInternet(SplashScreen.this)){
            APIService apiService =
                    APIUrl.getClient().create(APIService.class);

            Call<Result_Kios_Model> call = apiService.getKios("kios");

            call.enqueue(new Callback<Result_Kios_Model>() {
                @Override
                public void onResponse(Call<Result_Kios_Model>call, Response<Result_Kios_Model> response) {
                    Log.i(TAG, "onResponse: "+ response);
                    kios_models = response.body().getKios_models();
                    Singleton.setKios_models(kios_models);
                    Log.i(TAG, "onResponse: " + kios_models.length);
                    if (kios_models.length == 0){
                        Toast.makeText(SplashScreen.this, "Jaringan Bermasalah", Toast.LENGTH_SHORT).show();
                        btn_Try_Again.setVisibility(View.VISIBLE);
                    }else{
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Result_Kios_Model> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Log.i(TAG, call.toString());
                    //btn_Main.setVisibility(View.VISIBLE);
                    Toast.makeText(SplashScreen.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    btn_Try_Again.setVisibility(View.VISIBLE);
                }
            });
        }else{
            Toast.makeText(SplashScreen.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
            btn_Try_Again.setVisibility(View.VISIBLE);
        }
    }
}
