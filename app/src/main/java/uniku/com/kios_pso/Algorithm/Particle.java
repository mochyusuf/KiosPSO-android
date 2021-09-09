package uniku.com.kios_pso.Algorithm;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a particle from the Particle Swarm Optimization algorithm.
 */
public class Particle {

    private static String TAG = "PSO_ALGO";
    private Vector position;        // Current position.
    private Vector velocity;
    private Vector bestPosition;    // Personal best solution.
    private double bestEval;        // Personal best value.
    private LatLng posisi_awal;

    /**
     * Construct a Particle with a random starting position.
     * @param beginRange    the minimum xyz values of the position (inclusive)
     * @param endRange      the maximum xyz values of the position (exclusive)
     */
    Particle (int beginRange, int endRange,LatLng posisi_awal) {
        if (beginRange >= endRange) {
            throw new IllegalArgumentException("Begin range must be less than end range.");
        }
        this.posisi_awal = posisi_awal;

        position = new Vector();
        velocity = new Vector();
        setRandomPosition(beginRange, endRange);
        bestPosition = velocity.clone();
        bestEval = Double.MAX_VALUE;
    }

    private void setRandomPosition (int beginRange, int endRange) {
        int x = rand(beginRange, endRange);
        int y = rand(beginRange, endRange);
        position.set(x, y);
    }

    /**
     * Generate a random number between a certain range.
     * @param beginRange    the minimum value (inclusive)
     * @param endRange      the maximum value (exclusive)
     * @return              the randomly generated value
     */
    private static int rand (int beginRange, int endRange) {
        Random r = new java.util.Random();
        return r.nextInt(endRange - beginRange) + beginRange;
    }

    /**
     * Update the personal best if the current evaluation is better.
     */
    void updatePersonalBest (LatLng Koordinat) {
        LatLng Awal = posisi_awal;
        double eval = Distance(Awal,Koordinat);

        Log.i(TAG, "eval: " + eval);
        if (eval < bestEval) {
            bestPosition = position.clone();
            bestEval = eval;
        }
    }

    /**
     * Get a copy of the position of the particle.
     * @return  the x position
     */
    Vector getPosition () {
        return position.clone();
    }

    /**
     * Get a copy of the velocity of the particle.
     * @return  the velocity
     */
    Vector getVelocity () {
        return velocity.clone();
    }

    /**
     * Get a copy of the personal best solution.
     * @return  the best position
     */
    Vector getBestPosition() {
        return bestPosition.clone();
    }

    /**
     * Get the value of the personal best solution.
     * @return  the evaluation
     */
    double getBestEval () {
        return bestEval;
    }

    /**
     * Update the position of a particle by adding its velocity to its position.
     */
    void updatePosition () {
        this.position.add(velocity);
    }

    /**
     * Haversine formula
     * Set the velocity of the particle.
     * @param velocity  the new velocity
     */
    void setVelocity (Vector velocity) {
        this.velocity = velocity.clone();
    }

    public static double Distance(LatLng Awal, LatLng Koordinat) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(Koordinat.latitude - Awal.latitude);
        double lonDistance = Math.toRadians(Koordinat.longitude - Awal.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(Awal.latitude)) * Math.cos(Math.toRadians(Koordinat.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        Log.i(TAG, "Distance: ");
        System.out.println("distance = " + Math.sqrt(distance));
        return Math.sqrt(distance);
    }
}