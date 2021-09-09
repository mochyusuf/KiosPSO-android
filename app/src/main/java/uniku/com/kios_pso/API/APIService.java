package uniku.com.kios_pso.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import uniku.com.kios_pso.Model.Kios_Model;
import uniku.com.kios_pso.Model.Result_Gambar_Model;
import uniku.com.kios_pso.Model.Result_Kios_Model;

public interface APIService {

    @GET("api.php")
    Call<Result_Kios_Model> getKios(
            @Query("data") String data
    );

    @GET("api.php")
    Call<Kios_Model> getKiosID(
            @Query("data") String data,
            @Query("id_kios") int id_kios
    );

    @GET("api.php")
    Call<Result_Kios_Model> cariKios(
            @Query("data") String data,
            @Query("nama_kios") String nama_kios
    );

    @GET("api.php")
    Call<Result_Gambar_Model> getGambar(
            @Query("data") String data,
            @Query("id_kios") int id_kios
    );
}
