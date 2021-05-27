package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * exclusively handles getting a random number based on
 * upper bound passed into randomNumber() method
 *
 * @author Mike Kreutz
 */

class RandomNumberGenerator {
    private final Random rand;

    public RandomNumberGenerator()
    {
        rand = new Random();
    }

    //source (generating random numbers): https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
    public int randomNumber(int bound){
        return rand.nextInt(bound);
    }
}//ca.cmpt213.a2.model.RandomNumberGenerator Class
