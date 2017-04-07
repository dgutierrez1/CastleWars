package com.castlewars.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by dagut on 11/02/2017.
 */

public class Castle {

    private Vector3 position;
    private Texture castle;
    private Animation castleAnimation;
    private Rectangle boundsCastle;
    private int health;

    public Castle(int y){
        castle = new Texture("castle.png");
        castleAnimation = new Animation(castle,4,0.5f);
        position = new Vector3(0,y-castle.getHeight(),0);
        Gdx.app.log("X/Y",position.x+" / "+position.y+ "----"+castle.getWidth()+" / "+castle.getHeight());
        health = 1000;
        boundsCastle = new Rectangle(position.x, position.y, castle.getWidth(),castle.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getCastle() {
        return castle;
    }

    public Rectangle getBoundsCastle() {
        return boundsCastle;
    }

    public Animation getCastleAnimation() {
        return castleAnimation;
    }
    public void hit(int damage){
        health-=damage;
        if(health<0){
            castle.dispose();
            //GAME OVER
        }
    }

    public int getHealth() {
        return health;
    }
}
