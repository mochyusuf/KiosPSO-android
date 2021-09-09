package uniku.com.kios_pso.Algorithm;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public class Swarm {
    private static String TAG = "PSO_ALGO";
    private int numOfParticles, epochs;
    private double inertia, cognitiveComponent, socialComponent;
    private Vector bestPosition;
    private double bestEval;
    public static final double Weight = 0.729844; // (Nilainya tidak terpakai)
    public static final double C_1 = 1.496180; // Cognitive component. (Nilainya tidak terpakai)
    public static final double C_2 = 1.496180; // Social component. (Nilainya tidak terpakai)
    private LatLng posisi_awal;
    private ArrayList<LatLng> koordinat;

    /**
     * When Particles are created they are given a random position.
     * The random position is selected from a specified range.
     * If the begin range is 0 and the end range is 10 then the
     * value will be between 0 (inclusive) and 10 (exclusive).
     */
    private int beginRange, endRange;
    private static final int DEFAULT_BEGIN_RANGE = -100;
    private static final int DEFAULT_END_RANGE = 101;

    /**
     * Construct the Swarm with default values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     */
    public Swarm (int particles, int epochs, LatLng posisi_awal, ArrayList<LatLng> koordinat) {
        this(particles, epochs, Weight, C_1, C_2,posisi_awal,koordinat);
    }

    /**
     * Construct the Swarm with custom values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     * @param inertia       the particles resistance to change (Tidak Berguna Tapi Ada Di Rumus)
     * @param cognitive     the cognitive component or introversion of the particle (Tidak Berguna Tapi Ada Di Rumus)
     * @param social        the social component or extroversion of the particle (Tidak Berguna Tapi Ada Di Rumus)
     */
    public Swarm (int particles, int epochs, double inertia, double cognitive, double social,LatLng posisi_awal,ArrayList<LatLng> koordinat) {

        this.posisi_awal = posisi_awal;
        this.koordinat = koordinat;
        this.numOfParticles = particles;
        this.epochs = epochs;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        double infinity = Double.POSITIVE_INFINITY;
        bestPosition =
                new Vector(infinity, infinity);
        bestEval = Double.POSITIVE_INFINITY;
        beginRange = DEFAULT_BEGIN_RANGE;
        endRange = DEFAULT_END_RANGE;
    }

    /**
     * Execute the algorithm.
     */
    public int run () {
        //Initialize Partikel
        Particle[] particles = initialize();

        double oldEval = bestEval;
        Log.i(TAG, "--------------------------EXECUTING-------------------------");
        Log.i(TAG, "Global Best Evaluation (Epoch " + 0 + "):\t"  + bestEval);
        int result = 0;

        //Ulang Sampai Kios Terakhir
        for (int i = 0; i < epochs; i++) {

            //Dapatkan Jarak
            for (Particle p : particles) {
                p.updatePersonalBest(koordinat.get(i));
                updateGlobalBest(p);
            }

            Log.i(TAG, "Global Best Evaluation (Epoch " + (i + 1) + "):\t" + bestEval);
            //Bandingkan Jarak terpendek dengan sekarang
            if (bestEval < oldEval) {
                oldEval = bestEval;
                result = i;
            }

            //Tidak Berguna Tapi Ada Di Rumus
            for (Particle p : particles) {
                updateVelocity(p);
                p.updatePosition();
            }
        }

        Log.i(TAG, "---------------------------RESULT---------------------------");
        Log.i(TAG, "x = " + bestPosition.getX());
        Log.i(TAG, "y = " + bestPosition.getY());

        Log.i(TAG,"Final Best Evaluation: " + bestEval);
        Log.i(TAG,"---------------------------COMPLETE-------------------------");
        return result;
    }

    /**
     * Create a set of particles, each with random starting positions.
     * @return  an array of particles
     */
    private Particle[] initialize () {
        Particle[] particles = new Particle[numOfParticles];
        for (int i = 0; i < numOfParticles; i++) {
            Particle particle = new Particle(beginRange, endRange,posisi_awal);
            particles[i] = particle;
            updateGlobalBest(particle);
        }
        return particles;
    }

    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * @param particle  the particle to analyze
     */
    private void updateGlobalBest (Particle particle) {
        if (particle.getBestEval() < bestEval) {
            bestPosition = particle.getBestPosition();
            bestEval = particle.getBestEval();
        }
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * @param particle  the particle to update
     */
    private void updateVelocity (Particle particle) {
        Vector oldVelocity = particle.getVelocity();
        Vector pBest = particle.getBestPosition();
        Vector gBest = bestPosition.clone();
        Vector pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // The first product of the formula.
        Vector newVelocity = oldVelocity.clone();
        newVelocity.mul(inertia);

        // The second product of the formula.
        pBest.sub(pos);
        pBest.mul(cognitiveComponent);
        pBest.mul(r1);
        newVelocity.add(pBest);

        // The third product of the formula.
        gBest.sub(pos);
        gBest.mul(socialComponent);
        gBest.mul(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }
}
