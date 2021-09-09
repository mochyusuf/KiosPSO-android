package uniku.com.kios_pso.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniku.com.kios_pso.DetailKios;
import uniku.com.kios_pso.Model.Kios_Model;
import uniku.com.kios_pso.R;

public class KiosAdapter extends RecyclerView.Adapter<KiosAdapter.KiosViewHolder> {

    private Kios_Model[] kios_models;
    private int rowLayout;
    private Context context;
    public String TAG = "Kios_Adapter";

    public static class KiosViewHolder extends RecyclerView.ViewHolder {
        LinearLayout kios_layout;
        TextView nama_kios;
        TextView alamat;
        ImageView gambar_kios;

        public KiosViewHolder(View v) {
            super(v);
            kios_layout = v.findViewById(R.id.kios_layout);
            nama_kios = v.findViewById(R.id.nama_kios);
            alamat = v.findViewById(R.id.alamat);
            gambar_kios = v.findViewById(R.id.gambar_kios);
        }
    }

    public KiosAdapter(Kios_Model[] hotel, int rowLayout, Context context) {
        this.kios_models = hotel;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public KiosAdapter.KiosViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new KiosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KiosViewHolder holder, final int position) {
        holder.nama_kios.setText(kios_models[position].getNama_kios());
        String temp = (kios_models[position].getAlamat());
        holder.alamat.setText(temp);
        Log.i(TAG, "onBindViewHolder: " + kios_models[position].getGambar_kios());
        Picasso.with(context).load(kios_models[position].getGambar_kios()).resize(215, 160).into(holder.gambar_kios);
        holder.kios_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailKios.class);
                intent.putExtra("ID",kios_models[position].getId_kios());
                context.startActivity(intent);
            }
        });
    }

    public String cutText(String Text){
        if (Text.length() < 40){
            return Text;
        }else{
            String temp = Text.substring(0,35) + "...";
            return temp;
        }
    }

    @Override
    public int getItemCount() {
        return kios_models.length;
    }

}
