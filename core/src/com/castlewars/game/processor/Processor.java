package com.castlewars.game.processor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by dagut on 12/02/2017.
 */

public class Processor extends InputAdapter{

    private boolean pressed;
    private int count;

    public Processor(){
        pressed = false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pressed = true;
        run();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pressed = false;
        return pressed;
    }

    public void run(){
        boolean running = true;
        while(running) {
            running = pressed;
            if (Thread.interrupted()) {
                return;
            }
        }
    }
}
