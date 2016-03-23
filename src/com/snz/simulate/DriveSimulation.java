package com.snz.simulate;

import com.snz.domain.Network;
import com.snz.domain.NetworkParameters;
import com.snz.domain.Node;
import com.snz.interfaces.SimulationInterface;
import com.snz.utilities.Randomizer;

import java.util.*;

/**
 * Class to drive the simulation.
 * Created by Ashwin on 11/19/2015.
 */
public class DriveSimulation {

    private static Randomizer rand = new Randomizer();
    private List<Integer> times = new ArrayList<>();

    /**
     * Begins the simulation process.
     *
     * @param numberOfNodes
     * @param numberOfPackets
     */
    public List<Integer> simulate(int numberOfNodes, List<Integer> numberOfPackets, int packetSize, int numSim, List<Integer> collisions) {
        Network network = new Network(numberOfNodes);
        NetworkParameters networkParameters = new NetworkParameters(3, 1, 1, 2, 0.5f);
        network.setParams(networkParameters);
        int i=0;
        for (Node node : network.getNodeList()) {
            node.setNumberOfPackets(20);
            node.setContentionWindowMax(8);
            node.setPacketSizeList(new LinkedList<Integer>(Collections.nCopies(numberOfPackets.get(i), packetSize)));
            i++;
        }

        SimulationInterface simulator;
        network.setNodePacketCounts(numberOfPackets);
        int totalPackets = 0;
        for (Integer packetCount : numberOfPackets) {
            totalPackets += packetCount;
        }
        network.setTotalNumberOfPackets(totalPackets);
        try {
            for (int j=1; j<numSim; j++) {
                simulator = new WiFiSimulation(network.clone());
                times.add(simulator.initiate(collisions, j));
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception Thrown at cloning Network");
            e.printStackTrace();
        }
        return times;
    }

    /**
     * Begins the simulation p-persistent process.
     *
     * @param numberOfNodes
     * @param numberOfPackets
     */
    public List<Integer> simulateP(int numberOfNodes, List<Integer> numberOfPackets, int packetSize, float p, int numSim, List<Integer> collisions) {
        Network network = new Network(numberOfNodes);
        NetworkParameters networkParameters = new NetworkParameters(3, 1, 1, 2, p);
        network.setParams(networkParameters);
        int i=0;
        for (Node node : network.getNodeList()) {
            node.setNumberOfPackets(20);
            node.setContentionWindowMax(8);
            node.setPacketSizeList(new LinkedList<Integer>(Arrays.asList(2, 2)));
            node.setPacketSizeList(new LinkedList<Integer>(Collections.nCopies(numberOfPackets.get(i), packetSize)));
            i++;
        }

        SimulationInterface simulator;
        network.setNodePacketCounts(numberOfPackets);
        int totalPackets = 0;
        for (Integer packetCount : numberOfPackets) {
            totalPackets += packetCount;
        }
        network.setTotalNumberOfPackets(totalPackets);
        try {
            for (int j = 0; j < numSim; j++) {
                simulator = new WiFiPersistantSimulation(network.clone());
                times.add(simulator.initiate(collisions, j));
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Exception Thrown at cloning Network");
            e.printStackTrace();
        }
        return times;
    }

}
