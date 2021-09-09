package uniku.com.kios_pso.Model;

import java.io.Serializable;

public class Image_Model implements Serializable {
    private String large;

    public Image_Model() {
    }

    public Image_Model(String large) {
        this.large = large;
    }

    public String get() {
        return large;
    }

    public void set(String large) {
        this.large = large;
    }
}
