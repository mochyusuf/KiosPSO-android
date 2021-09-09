package uniku.com.kios_pso.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class checkInternet {

    public static boolean checkInternet(Context context){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;
        }
        else {
            connectStatus = false;
        }
        return connectStatus;
    }

    public static String Rupiah(int harga){

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String result = kursIndonesia.format(harga) + ",00";
        return result;
    }
}
