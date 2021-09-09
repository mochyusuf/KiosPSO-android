package uniku.com.kios_pso.Model;

import com.google.gson.annotations.SerializedName;

public class Kios_Model {
    @SerializedName("id_kios")
    private int id_kios;

    @SerializedName("nama_kios")
    private String nama_kios;

    @SerializedName("luas_kios")
    private int luas_kios;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("nomor_telepon")
    private String nomor_telepon;

    @SerializedName("fasilitas")
    private String fasilitas;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("harga_pembayaran")
    private int harga_pembayaran;

    @SerializedName("jenis_pembayaran")
    private String jenis_pembayaran;

    @SerializedName("gambar_kios")
    private String gambar_kios;

    public Kios_Model(int id_kios, String nama_kios, int luas_kios, String alamat, String nomor_telepon, String fasilitas, double latitude, double longitude, int harga_pembayaran, String jenis_pembayaran, String gambar_kios) {
        this.id_kios = id_kios;
        this.nama_kios = nama_kios;
        this.luas_kios = luas_kios;
        this.alamat = alamat;
        this.nomor_telepon = nomor_telepon;
        this.fasilitas = fasilitas;
        this.latitude = latitude;
        this.longitude = longitude;
        this.harga_pembayaran = harga_pembayaran;
        this.jenis_pembayaran = jenis_pembayaran;
        this.gambar_kios = gambar_kios;
    }

    public int getId_kios() {
        return id_kios;
    }

    public void setId_kios(int id_kios) {
        this.id_kios = id_kios;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }

    public int getLuas_kios() {
        return luas_kios;
    }

    public void setLuas_kios(int luas_kios) {
        this.luas_kios = luas_kios;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomor_telepon() {
        return nomor_telepon;
    }

    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getHarga_pembayaran() {
        return harga_pembayaran;
    }

    public void setHarga_pembayaran(int harga_pembayaran) {
        this.harga_pembayaran = harga_pembayaran;
    }

    public String getJenis_pembayaran() {
        return jenis_pembayaran;
    }

    public void setJenis_pembayaran(String jenis_pembayaran) {
        this.jenis_pembayaran = jenis_pembayaran;
    }

    public String getGambar_kios() {
        return gambar_kios;
    }

    public void setGambar_kios(String gambar_kios) {
        this.gambar_kios = gambar_kios;
    }
}
