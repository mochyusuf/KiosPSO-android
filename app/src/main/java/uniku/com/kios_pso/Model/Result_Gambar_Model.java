package uniku.com.kios_pso.Model;

import com.google.gson.annotations.SerializedName;

public class Result_Gambar_Model {

    @SerializedName("gambar")
    private Gambar_Model[] gambar_models;

    public Gambar_Model[] getGambar_models() {
        return gambar_models;
    }

    public void setGambar_models(Gambar_Model[] gambar_models) {
        this.gambar_models = gambar_models;
    }

    public Result_Gambar_Model(Gambar_Model[] gambar_models) {
        this.gambar_models = gambar_models;
    }
}
