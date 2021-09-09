package uniku.com.kios_pso.Model;

import com.google.gson.annotations.SerializedName;

public class Result_Kios_Model {
    @SerializedName("kios")
    private Kios_Model[] kios_models;

    public Result_Kios_Model(Kios_Model[] kios_models) {
        this.kios_models = kios_models;
    }

    public Kios_Model[] getKios_models() {
        return kios_models;
    }

    public void setKios_models(Kios_Model[] kios_models) {
        this.kios_models = kios_models;
    }
}
