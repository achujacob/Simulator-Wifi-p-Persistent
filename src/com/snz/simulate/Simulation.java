package com.snz.simulate;

import com.snz.domain.Node;
import com.snz.utilities.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for common functionalities of Simulation.
 * Created by Ashwin on 11/24/2015.
 */
public class Simulation {
    private int cellTimer = 0;
    private boolean isDIFS = false;
    private boolean isSIFS = false;
    private boolean isSIFSAndACK = false;
    private boolean isPacket = false;
    public static Randomizer randomizer = new Randomizer();

    private List<Integer> generateRandomBackoff(Node... nodes) {
        ArrayList<Integer> backoffs = new ArrayList<>();
        for (Node node : nodes) {
            backoffs.add(randomizer.genRand(node.getContentionWindowMax()));
        }
        return backoffs;
    }

    public void decrementCellTimer() {
        cellTimer -= 1;
    }

    public void startDIFs(int time) {
        stopAll();
        isDIFS = true;
        cellTimer = time;
    }

    public void startSIFs(int time) {
        stopAll();
        isSIFS = true;
        cellTimer = time;
    }

    public void startSIFSAndACK(int time) {
        stopAll();
        isSIFSAndACK = true;
        cellTimer = time;
    }

    public void startPacket(int time) {
        stopAll();
        isPacket = true;
        cellTimer = time;
    }

    public void stopAll() {
        isDIFS = false;
        isSIFS = false;
        isSIFSAndACK = false;
        isPacket = false;
        cellTimer = 0;
    }

    public List<Integer> findTransmittingNodes(List<Integer> backoffCounterList) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < backoffCounterList.size(); i++) {
            if (backoffCounterList.get(i) == 0) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public boolean checkToSendOrNotToSend(float p) {
        if (randomizer.genRand() < p) {
            System.out.println("Persistant Check says : YES");
            return true;
        }
        System.out.println("Persistant Check says : NO");
        return false;
    }

    public int getCellTimer() {
        return cellTimer;
    }

    public void setCellTimer(int cellTimer) {
        this.cellTimer = cellTimer;
    }

    public String whichMode() {
        if (isDIFS()) return "DIFS";
        if (isSIFS()) return "SIFS";
        if (isSIFSAndACK()) return "SIFS & ACK";
        if (isPacket()) return "Packet";
        return "-";
    }

    public boolean isDIFS() {
        return isDIFS;
    }

    public void setDIFS(boolean DIFS) {
        isDIFS = DIFS;
    }

    public boolean isSIFS() {
        return isSIFS;
    }

    public void setSIFS(boolean SIFS) {
        isSIFS = SIFS;
    }

    public boolean isSIFSAndACK() {
        return isSIFSAndACK;
    }

    public void setSIFSAndACK(boolean SIFSAndACK) {
        isSIFSAndACK = SIFSAndACK;
    }

    public boolean isPacket() {
        return isPacket;
    }

    public void setPacket(boolean packet) {
        isPacket = packet;
    }
}
