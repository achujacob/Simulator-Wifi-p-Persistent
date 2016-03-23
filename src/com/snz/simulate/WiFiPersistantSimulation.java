package com.snz.simulate;

import com.snz.domain.Network;
import com.snz.domain.NetworkParameters;
import com.snz.domain.Node;
import com.snz.interfaces.SimulationInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to simulate the 802.11 p-Persistant WiFi Network.
 * Created by Ashwin on 11/19/2015.
 */
public class WiFiPersistantSimulation extends Simulation implements SimulationInterface {
    private Network network;
    int collisionCounter = 0;

    public WiFiPersistantSimulation(Network network) {
        this.network = network;
    }

    @Override
    public int initiate(List<Integer> collisions, int simNum) {
        int t = 0;

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
                System.out.println("The time is : " + t + ":::" + whichMode());
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
//                    The packets must be transmitted now
                    List<Node> transmittingNodeList = new ArrayList<>();
                    List<Boolean> isTransmitNodeList = new ArrayList<>();

                    for (Node node : network.getNodeList()) {
                        if (node.getNumberOfPackets() > 0) {
//                            Check if the packet can be sent using the Persistant algorithm
                            if (checkToSendOrNotToSend(params.getP())) {
                                transmittingNodeList.add(node);
                            }
                        }
                    }

                    if (transmittingNodeList.size() > 1) {
//                        Nodes will collide
                            collisionCounter++;
//                        Find the largest packet being transmitted to realize the time lost in collision
                        int maxTime = 0;
                        for (Node transmittingNode : transmittingNodeList) {
                            int time = transmittingNode.getPacketSizeList().peek();
                            if (maxTime < time) {
                                maxTime = time;
                            }
                        }
                        System.out.println("Collision waste : " + maxTime);
//                        We assume that the DIFS and SIFS overlap
                        t += maxTime;
                        startDIFs(params.getTimeDIFS());
                        continue;

                    } else if (transmittingNodeList.size() == 1) {
//                        There is only one node transmitting
                        Node transmittingNode = transmittingNodeList.get(0);
                        int nodeIndex = network.getNodeList().indexOf(transmittingNode);
                        System.out.println("Node " + (nodeIndex + 1) + " is transmitting");
                        t += transmittingNode.getPacketSizeList().peek();
                        transmittingNode.getPacketSizeList().remove();
                        network.decrementPacketCount(nodeIndex);
                        startSIFSAndACK(params.getTimeSIFS() + params.getTimeACK());
                        continue;
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
