package com.castlewars.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by dagut on 11/02/2017.
 */

public class Bullet {

    private Vector3 position;
    private Vector3 velocity;
    private int damage;
    private static final int SPEED = -40;
    private Rectangle boundsBullet;

    private Texture bullet;

    public Bullet(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        bullet = new Texture("bullet.png");
        damage = 50;
        boundsBullet = new Rectangle(position.x, position.y, bullet.getWidth(), bullet.getHeight());
    }

    public void update(float dt){
        velocity.set(0, SPEED, 0);
        velocity.scl(dt);
        position.add(0,velocity.y,0);
        velocity.scl(1/dt);
        boundsBullet.setPosition(position.x,position.y);
    }
    public void hit(){
        bullet.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public int getDamage() {
        return damage;
    }

    public Texture getBullet() {
        return bullet;
    }

    public Rectangle getBoundsBullet() {
        return boundsBullet;
    }

    public void dispose(){
        bullet.dispose();
    }
}
