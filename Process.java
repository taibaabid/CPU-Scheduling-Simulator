package com.simulator;

import java.util.Random;
/**
 * Represents a single process in the OS simulation.
 */
public class Process {
    public String pid;
    public int burstTime;
    public int priority;      // 1 is highest priority, 5 is lowest
    public int remainingTime;
    public int arrivalTime = 0;
    public int finishTime;
    /**
     * Constructor that randomizes CPU requirements and priority.
     */
    public Process(String pid) {
        Random rand = new Random();
        this.pid = pid;
        // Random Burst Time between 2 and 8 units
        this.burstTime = rand.nextInt(7) + 2;
        // Random Priority between 1 and 5
        this.priority = rand.nextInt(5) + 1;
        this.remainingTime = this.burstTime;
    }
}