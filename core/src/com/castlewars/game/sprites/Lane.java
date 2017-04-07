package com.castlewars.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;
import com.sun.org.apache.bcel.internal.generic.CASTORE;
import com.badlogic.gdx.audio.Sound;
import java.util.Stack;

/**
 * Created by dagut on 11/02/2017.
 */

public class Lane {

    private Queue<Bullet> bullets;
    private Queue<Knight> knights;
    private Queue<Knight> attackingKnights;

    private int endX;
    private int startX;
    private int endY;
    private int startY;
    private int laneId;

    private Sound choque;

    private Rectangle boundsCastle;
    private Texture lane;

    private Castle castle;


    public Lane(int id, int startX, Castle castle) {
        this.castle = castle;
        laneId = id;
        this.startX = startX;
        endX = startX + 60;
        startY = 0;
        endY = 400;

        choque= Gdx.audio.newSound(Gdx.files.internal("pepo.wav"));


        bullets = new Queue<Bullet>();
        knights = new Queue<Knight>();
        attackingKnights = new Queue<Knight>();

        lane = new Texture("lane_bg.png");
        boundsCastle = new Rectangle(startX, castle.getPosition().y, endX - startX, castle.getCastle().getHeight());

        //Gdx.app.log("CASTLE LANE", "ID: " + id + " --- " + startX + " / " + castle.getPosition().y + " --- " + (endX - startX) + " / " + castle.getCastle().getHeight());
    }

    public Knight peekKnight() {
        return knights.first();
    }

    public Bullet peekBullet() {
        return bullets.first();
    }

    public void deployKnight(Knight kn) {
        knights.addLast(kn);
    }

    public void deployBullet(Bullet bl) {
        bullets.addLast(bl);
    }

    public void update(float dt) {
        //Gdx.app.log("DT",dt+"");
        for (Knight kn : knights) {
            kn.update(dt);
        }
        for (Bullet bl : bullets) {
            bl.update(dt);
        }
        for (Knight kn : attackingKnights) {
            kn.update(dt);
        }

        if ((knights.size > 0 && bullets.size > 0)) {
            Knight kn = knights.first();
            Bullet bl = bullets.first();
            if (bl.getPosition().y < 0) {
                bl.dispose();
                bullets.removeFirst();
            }
            if (collides(kn.getBoundsKnight(), bl.getBoundsBullet())) {
                choque.play(1f);
                boolean kill = knights.first().hit(bl.getDamage());
                bl.hit();
                bullets.removeFirst();
                if (kill) {
                    knights.removeFirst();
                }
            }

        }
        if((attackingKnights.size > 0 && bullets.size > 0)){
            Knight akn = attackingKnights.first();
            Bullet bl = bullets.first();
            if (collides(akn.getBoundsKnight(), bl.getBoundsBullet())) {
                boolean kill = attackingKnights.first().hit(bl.getDamage());
                bl.hit();
                bullets.removeFirst();
                if (kill) {
                    attackingKnights.removeFirst();
                }
            }
        }
        if (knights.size > 0) {
            Knight kn = knights.first();
            if (collides(kn.getBoundsKnight(), boundsCastle)) {
                if(!kn.isAttacking()){
                    kn.startAttack();
                    kn.setAttacking(true);
                    attackingKnights.addLast(knights.removeFirst());
                }
                castle.hit(kn.getDamage());
                if(castle.getHealth()<0){
                    dispose();
                }
            }

            if(kn.getPosition().y>endY){
                kn.dispose();
                knights.removeValue(kn,true);
            }
        }




    }

    public boolean collides(Rectangle knight, Rectangle bullet) {
        return knight.overlaps(bullet) || bullet.overlaps(knight);
    }


    public int getEndX() {
        return endX;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndY() {
        return endY;
    }

    public int getStartY() {
        return startY;
    }

    public Texture getLane() {
        return lane;
    }

    public Queue<Bullet> getBullets() {
        return bullets;
    }

    public Queue<Knight> getKnights() {
        return knights;
    }

    public int getLaneId() {
        return laneId;
    }

    public void dispose() {
        for (Knight kn : knights) {
            kn.dispose();
        }
        for (Bullet bl : bullets) {
            bl.dispose();
        }
        for (Knight kn:attackingKnights) {
            kn.dispose();
        }
        lane.dispose();
        choque.dispose();
    }

    public Queue<Knight> getAttackingKnights() {
        return attackingKnights;
    }


}
