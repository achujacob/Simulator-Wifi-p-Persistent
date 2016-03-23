package com.snz.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Class to handle random number generation.
 * Created by Ashwin on 11/19/2015.
 */
public class Randomizer {
    private Random floatRand = new Random();
    private Random doubleRand = new Random();
    private Random intRand = new Random();

    /**
     * Method to generate a random float number
     * @return
     */
    public float genRand() {
            return floatRand.nextFloat();
        }

    /**
     * Method returns list of uniformly generated double random numbers
     * @param numberOfRands
     * @return
     */
    public List<Double> genRands(int numberOfRands) {
        List<Double> numbers = new ArrayList<Double>();

        for (int i = 0; i < numberOfRands; i++ ) {
           numbers.set(i, doubleRand.nextDouble());
        }
        return numbers;
    }


    /**
     * Method returns uniformly generated integer random number
     * @param max The max is exluded
     * @return
     */
    public Integer genRand(int max) {
        return intRand.nextInt(max);
    }

    /**
     * Method returns list of uniformly generated integer random numbers
     * @param numberOfRands
     * @param max The max is exluded
     * @return
     */
    public List<Integer> genRands(int numberOfRands, int max) {
        List<Integer> numbers = new ArrayList<Integer>();

        for (int i = 0; i < numberOfRands; i++ ) {
           numbers.set(i, intRand.nextInt(max));
        }
        return numbers;
    }


}
