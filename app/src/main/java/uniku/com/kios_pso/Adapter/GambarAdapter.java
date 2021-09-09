package uniku.com.kios_pso.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniku.com.kios_pso.Model.Gambar_Model;
import uniku.com.kios_pso.R;

public class GambarAdapter extends RecyclerView.Adapter<GambarAdapter.GambarViewHolder>  {
    private Gambar_Model[] gambar_models;
    private int rowLayout;
    private Context context;
    public String TAG = "Gambar_Adapter";

    public static class GambarViewHolder extends RecyclerView.ViewHolder {
        ImageView gambar_kios;

        public GambarViewHolder(View v) {
            super(v);
            gambar_kios = v.findViewById(R.id.gambar_kios);
        }
    }

    public GambarAdapter(Gambar_Model[] gambar_models, int rowLayout, Context context) {

        Log.i(TAG, "onResponse: " + gambar_models.length);
        this.gambar_models = gambar_models;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public GambarAdapter.GambarViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new GambarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GambarViewHolder holder, int position) {
        Log.i(TAG, "onResponse: " + gambar_models[position].getGambar());
        Picasso.with(context).load(gambar_models[position].getGambar()).into(holder.gambar_kios);
    }

    @Override
    public int getItemCount() {
        return gambar_models.length;
    }
}
