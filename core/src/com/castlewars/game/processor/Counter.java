package com.castlewars.game.processor;

import java.util.concurrent.Semaphore;

/**
 * Created by dagut on 12/02/2017.
 */

public class Counter extends Thread {
    private Semaphore sem;

    public Counter(Semaphore sem){
        this.sem = sem;
    }

    public void run() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
