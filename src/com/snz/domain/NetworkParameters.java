package com.snz.domain;

/**
 * Class with Network Parameters.
 * Created by Ashwin on 11/19/2015.
 */
public class NetworkParameters {
    private int timeDIFS = 0;
    private int timeSIFS = 0;
    private int timeACK = 0;
    private int timeCollision = 0;
    private float p = 0;

    public NetworkParameters(NetworkParameters p) {
        this.timeDIFS = p.timeDIFS;
        this.timeSIFS = p.timeSIFS;
        this.timeACK = p.timeACK;
        this.timeCollision = p.timeCollision;
        this.p = p.p;
    }

    public NetworkParameters(int timeDIFS, int timeSIFS, int timeACK, int timeCollision, float p) {
        this.timeDIFS = timeDIFS;
        this.timeSIFS = timeSIFS;
        this.timeACK = timeACK;
        this.timeCollision = timeCollision;
        this.p = p;
    }

    public float getP() {
        return p;
    }

    public void setP(float p) {
        this.p = p;
    }

    public int getTimeDIFS() {
        return timeDIFS;
    }

    public void setTimeDIFS(int timeDIFS) {
        this.timeDIFS = timeDIFS;
    }

    public int getTimeSIFS() {
        return timeSIFS;
    }

    public void setTimeSIFS(int timeSIFS) {
        this.timeSIFS = timeSIFS;
    }

    public int getTimeACK() {
        return timeACK;
    }

    public void setTimeACK(int timeACK) {
        this.timeACK = timeACK;
    }

    public int getTimeCollision() {
        return timeCollision;
    }

    public void setTimeCollision(int timeCollision) {
        this.timeCollision = timeCollision;
    }
}
