package uniku.com.kios_pso.Model;

import com.google.gson.annotations.SerializedName;

public class Gambar_Model {
    @SerializedName("id_gambar")
    private int id_gambar;
    @SerializedName("id_kios")
    private int id_kios;
    @SerializedName("gambar_kios")
    private String gambar;

    public int getId_gambar() {
        return id_gambar;
    }

    public void setId_gambar(int id_gambar) {
        this.id_gambar = id_gambar;
    }

    public int getId_kios() {
        return id_kios;
    }

    public void setId_kios(int id_kios) {
        this.id_kios = id_kios;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


    public Gambar_Model(int id_gambar, int id_kios, String gambar) {

        this.id_gambar = id_gambar;
        this.id_kios = id_kios;
        this.gambar = gambar;
    }
}
