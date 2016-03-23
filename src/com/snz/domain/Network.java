package com.snz.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain class for the Network.
 * Created by Ashwin on 11/19/2015.
 */
public class Network implements Cloneable {
    private int numberOfNodes = 0;
    private int totalNumberOfPackets = 0;
    private NetworkParameters params;
    private List<Node> nodeList = new ArrayList<Node>();
    private List<Integer> nodeBackoffList = new ArrayList<>();

    public Network() {
        this.numberOfNodes = 0;
    }


    public Network(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        for (int i = 0; i < numberOfNodes; i++) {
            nodeList.add(i, new Node());
            nodeBackoffList.add(i, -1);
        }
    }

    public void setNodePacketCounts(List<Integer> packetCounts) {
        totalNumberOfPackets = 0;
        for (int i = 0; i < packetCounts.size(); i++) {
            int packetCount = packetCounts.get(i);
            nodeList.get(i).setNumberOfPackets(packetCount);
            totalNumberOfPackets = totalNumberOfPackets + packetCount;
            nodeBackoffList.set(i, -1);
        }
    }

    public Network clone() throws CloneNotSupportedException {

        super.clone();
        Network copyNetwork = new Network(numberOfNodes);
        copyNetwork.setNumberOfNodes(numberOfNodes);
        copyNetwork.setTotalNumberOfPackets(totalNumberOfPackets);
        if (numberOfNodes > 0) {
            List<Node> copyNodes = new ArrayList<>();
            for (int i = 0; i < numberOfNodes; i++) {
                Node node = new Node(nodeList.get(i));
                copyNodes.add(i, node);
                copyNetwork.getNodeBackoffList().set(i, nodeBackoffList.get(i));
            }
            copyNetwork.setNodeList(copyNodes);
        }
        if (params != null) {
            copyNetwork.setParams(new NetworkParameters(params));
        }

        return copyNetwork;
    }

    public void decrementPacketCount(int nodeIndex) {
        totalNumberOfPackets -= 1;
        nodeList.get(nodeIndex).decrementPacketCount();
    }

    public NetworkParameters getParams() {
        return params;
    }

    public void setParams(NetworkParameters params) {
        this.params = params;
    }

    public List<Integer> getNodeBackoffList() {
        return nodeBackoffList;
    }

    public void setNodeBackoffList(List<Integer> nodeBackoffList) {
        this.nodeBackoffList = nodeBackoffList;
    }

    public int getTotalNumberOfPackets() {
        return totalNumberOfPackets;
    }

    public void setTotalNumberOfPackets(int totalNumberOfPackets) {
        this.totalNumberOfPackets = totalNumberOfPackets;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
