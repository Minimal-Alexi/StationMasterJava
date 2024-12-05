package Model.simulation.framework;

import java.util.Random;

public class SeedSingleton {
    private static SeedSingleton seedSingleton;
    private static final long DEFAULT_SEED = new Random().nextLong();
    private final long seedValue;
    private SeedSingleton(){
        this.seedValue = DEFAULT_SEED;
    }
    public static SeedSingleton getSeed(){
        if(seedSingleton == null){
            seedSingleton = new SeedSingleton();
        }
        return seedSingleton;
    }
    public static void resetSeed() {
        seedSingleton = new SeedSingleton();
    }
    public long getSeedValue(){
        return seedValue;
    }
}
