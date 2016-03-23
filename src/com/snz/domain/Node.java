package com.snz.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Domain class for the Node.
 * Created by Ashwin on 11/19/2015.
 */
public class Node {
    private int numberOfPackets = 0;
    private int contentionWindowMax = 8;
    private Queue<Integer> packetSizeList = new LinkedList<>();

    public Node() {
    }

    public Node(Node node) {
        this.numberOfPackets = node.numberOfPackets;
        this.contentionWindowMax = node.contentionWindowMax;
        this.packetSizeList = new LinkedList<>(node.getPacketSizeList());
    }

    public Node(int numberOfPackets) {
        this.numberOfPackets = numberOfPackets;
    }

    public void decrementPacketCount() {
        numberOfPackets = numberOfPackets - 1;
    }

    public void resetContentionWindow() {
        contentionWindowMax = 8;
    }

    public void doubleContentionWindow() {
        contentionWindowMax = contentionWindowMax*2;
    }

    public Queue<Integer> getPacketSizeList() {
        return packetSizeList;
    }

    public void setPacketSizeList(Queue<Integer> packetSizeList) {
        this.packetSizeList = packetSizeList;
    }

    public int getContentionWindowMax() {
        return contentionWindowMax;
    }

    public void setContentionWindowMax(int contentionWindowMax) {
        this.contentionWindowMax = contentionWindowMax;
    }

    public int getNumberOfPackets() {
        return numberOfPackets;
    }

    public void setNumberOfPackets(int numberOfPackets) {
        this.numberOfPackets = numberOfPackets;
    }
}
