package com.snz.simulate;

import com.snz.domain.Network;
import com.snz.domain.NetworkParameters;
import com.snz.domain.Node;
import com.snz.interfaces.SimulationInterface;

import java.util.List;

/**
 * Class to simulate the 802.11 WiFi Network.
 * Created by Ashwin on 11/19/2015.
 */
public class WiFiSimulation extends Simulation implements SimulationInterface {
    private Network network;
    int collisionCounter = 0;

    public WiFiSimulation(Network network) {
        this.network = network;
    }

    @Override
    public int initiate(List<Integer> collisions, int simNum) {
        int t = 0;

        List<Integer> backoffList = network.getNodeBackoffList();
        NetworkParameters params = network.getParams();
//         Beginning Simulation
        startDIFs(params.getTimeDIFS());
        for (int i = 0; i < network.getNodeList().size(); i++) {
            Node node = network.getNodeList().get(i);
            if (node.getNumberOfPackets() > 0) {
                network.getNodeBackoffList().set(i, randomizer.genRand(node.getContentionWindowMax()));
            }
        }
        try {
            while (network.getTotalNumberOfPackets() > 0) {
                System.out.println("The time is : " + t + " and the Backoffs : " + backoffList.toString() + ":::" + whichMode());
//            Check if DIFS or SIFS or ACK is going on
                if (isDIFS() || isSIFS() || isSIFSAndACK()) {
                    if (getCellTimer() > 0) {
                        decrementCellTimer();
                        t++;
                        continue;
                    }
                    if (isSIFSAndACK()) {
                        startDIFs(params.getTimeDIFS());
                    } else {
                        stopAll();
                    }
                    continue;
                }

                if (!isDIFS() && !isSIFS() && !isPacket() && !isSIFSAndACK()) {
                    if (backoffList.contains(0)) {
//                    The packets must be transmitted now
                        List<Integer> nodeIndexes = findTransmittingNodes(backoffList);

                        if (nodeIndexes.size() > 1) {
//                        Nodes will collide
                            collisionCounter++;
//                        Find the largest packet being transmitted to realize the time lost in collision
                            int maxTime = 0;
                            for (Integer transmittingNodeIndex : nodeIndexes) {
                                int time = network.getNodeList().get(transmittingNodeIndex).getPacketSizeList().peek();
                                if (maxTime < time) {
                                    maxTime = time;
                                }
//                            Increase Contention Window
                                network.getNodeList().get(transmittingNodeIndex).doubleContentionWindow();
//                            Generate the new backoff counter
                                backoffList.set(transmittingNodeIndex, randomizer.genRand(network.getNodeList().get(transmittingNodeIndex).getContentionWindowMax()));
                            }
                            System.out.println("Collision waste : " + maxTime);
//                        We assume that the DIFS and SIFS overlap
                            t += maxTime;
                            startDIFs(params.getTimeDIFS());
                            continue;

                        } else {
//                        There is only one node transmitting
                            int nodeIndex = nodeIndexes.get(0);
                            Node transmittingNode = network.getNodeList().get(nodeIndex);
                            System.out.println("Node " + (nodeIndex + 1) + " is transmitting");
                            t += transmittingNode.getPacketSizeList().peek();
                            transmittingNode.getPacketSizeList().remove();
                            network.decrementPacketCount(nodeIndex);
                            if (transmittingNode.getNumberOfPackets() > 0) {
                                transmittingNode.resetContentionWindow();
                                backoffList.set(nodeIndex, randomizer.genRand(transmittingNode.getContentionWindowMax()));
                            } else {
                                backoffList.set(nodeIndex, -1);
                            }
                            startSIFSAndACK(params.getTimeSIFS() + params.getTimeACK());
                            continue;
                        }


                    } else {
//                    Decrement all backoff counters
                        for (int i = 0; i < backoffList.size(); i++) {
                            if (backoffList.get(i).compareTo(-1) != 0) {
                                backoffList.set(i, backoffList.get(i).intValue() - 1);
                            }
                        }
                    }
                }
                t++;
            }
        } catch (Exception e) {
            throw e;
        }
        collisions.set(simNum, collisionCounter);
        System.out.println("This simulation took : " + t);
        return t;
    }

}
