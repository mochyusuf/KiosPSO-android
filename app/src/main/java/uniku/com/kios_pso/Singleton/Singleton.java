package uniku.com.kios_pso.Singleton;

import uniku.com.kios_pso.Model.Kios_Model;

public class Singleton {

    private Kios_Model[] kios_models;

    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public Kios_Model[] getKios_models() {
        return kios_models;
    }

    public void setKios_models(Kios_Model[] kios_models) {
        this.kios_models = kios_models;
    }
}
