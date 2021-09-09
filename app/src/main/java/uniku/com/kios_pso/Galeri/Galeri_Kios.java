package uniku.com.kios_pso.Galeri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uniku.com.kios_pso.API.APIService;
import uniku.com.kios_pso.API.APIUrl;
import uniku.com.kios_pso.Adapter.GalleryAdapter;
import uniku.com.kios_pso.Adapter.GambarAdapter;
import uniku.com.kios_pso.GambarKios;
import uniku.com.kios_pso.Model.Gambar_Model;
import uniku.com.kios_pso.Model.Result_Gambar_Model;
import uniku.com.kios_pso.R;
import uniku.com.kios_pso.Model.Image_Model;

import static uniku.com.kios_pso.Helper.checkInternet.checkInternet;

public class Galeri_Kios extends AppCompatActivity {

    private String TAG = "Galeri_TAG";
    private ArrayList<Image_Model> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private Gambar_Model[] gambar_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri__kios);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(Galeri_Kios.this, images);

        final ProgressDialog progressDialog = new ProgressDialog(Galeri_Kios.this);
        progressDialog.setMessage("Proses");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Intent intent = getIntent();
        int ID  = intent.getIntExtra("ID",0);

        if (checkInternet(Galeri_Kios.this)){
            APIService apiService =
                    APIUrl.getClient().create(APIService.class);

            Call<Result_Gambar_Model> call = apiService.getGambar("gambar",ID);

            call.enqueue(new Callback<Result_Gambar_Model>() {
                @Override
                public void onResponse(Call<Result_Gambar_Model>call, Response<Result_Gambar_Model> response) {

                    gambar_models = response.body().getGambar_models();
                    if (gambar_models.length > 0){
                        Log.i(TAG, "onResponse: " + gambar_models.length);
                        progressDialog.dismiss();

                        for (int i = 0; i < gambar_models.length; i++) {
                            Image_Model image = new Image_Model();

                            image.set(gambar_models[i].getGambar());

                            images.add(image);
                        }

                        Log.i(TAG, "onResponse: "+recyclerView);

                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        Log.i(TAG, "onResponse: "+ mAdapter);
                        recyclerView.setAdapter(mAdapter);
//
                        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("images", images);
                                bundle.putInt("position", position);

                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                                newFragment.setArguments(bundle);
                                newFragment.show(ft, "slideshow");
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));
                    }else{
                        Toast.makeText(Galeri_Kios.this, "Gambar Tidak Ditemukan", Toast.LENGTH_LONG).show();
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
            Toast.makeText(Galeri_Kios.this, "Tidak Terhubung Dengan Internet", Toast.LENGTH_SHORT).show();
        }

    }

}
