package com.snz.main;

import com.snz.simulate.DriveSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Main function to the program
 * Created by Ashwin on 11/19/2015.
 */
public class MainController {
    public static void main(String[] args) {
        int numberOfNodes = 60;
        int numberOfPacketsEach = 10;
        int numberOfSimulations = 5;
        int packetSize = 10;
        float p = 0.1f;

        List<Integer> numberOfPackets = Collections.nCopies(numberOfNodes, numberOfPacketsEach);
        List<Integer> times = new ArrayList<>(Collections.nCopies(numberOfSimulations, 0));
        List<Integer> collisions = new ArrayList<>(Collections.nCopies(numberOfSimulations, 0));

        DriveSimulation driver = new DriveSimulation();

        times = driver.simulate(numberOfNodes, numberOfPackets, packetSize, numberOfSimulations, collisions);
//        times = driver.simulateP(numberOfNodes, numberOfPackets, packetSize, p, numberOfSimulations, collisions);

        System.out.println(times);
        double averageCollisions = 0;
        double averageTime = 0;
        for (int i =0; i<numberOfSimulations-1; i++) {
            averageCollisions += collisions.get(i);
            averageTime += times.get(i);
        }
        averageCollisions /= numberOfSimulations;
        System.out.println("AVERAGE COLLISIONS :" + averageCollisions);
        averageTime /= numberOfSimulations;
        System.out.println("AVERAGE TIMES :" + averageTime);

        System.out.println("CHECK");
    }
}
