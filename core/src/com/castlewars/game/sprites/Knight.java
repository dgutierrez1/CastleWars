package com.castlewars.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sun.media.sound.MidiUtils;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by dagut on 11/02/2017.
 */

public class Knight {

    private Vector3 position;
    private Vector3 velocity;
    private int health;
    private int shield;
    private int speed;
    private int streak;
    private int kill;
    private Rectangle boundsKnight;
    private static final double KNIGHT_SELECTION_RATIO = 1;
    private Animation knightAnimation;
    private Texture knight;
    private Texture knightAttack;
    private Texture knightWalk;
    private Texture knightHit;
    private Texture knightKilled;
    private int damage;
    private Animation attackAnimation;
    private Animation walkAnimation;
    private boolean attacking;
    private int lvl;
    private Sound lvl1;
    private Sound lvl2;
    private Sound lvl3;
    private Sound lvl4;
    private Sound canon;
    private Sound explocion_castillo;
    private Sound explocion_monedas;
    private Sound choque;

    private Sound audio;
    private Sound batalla;


    public Knight(int x, int y, int shield) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        health = 100;
        damage = 50;
        speed = 15;
        streak = 0;
        kill = 0;
        this.shield = shield;
        attacking = false;

        lvl1= Gdx.audio.newSound(Gdx.files.internal("lvl1.wav"));
        lvl2= Gdx.audio.newSound(Gdx.files.internal("lvl2.wav"));
        lvl3= Gdx.audio.newSound(Gdx.files.internal("lvl3.wav"));
        lvl4= Gdx.audio.newSound(Gdx.files.internal("lvl4.wav"));

        if (shield <= 1 * KNIGHT_SELECTION_RATIO) {
            knightWalk = new Texture("lvl1_spikesman_animation.png");
            knightAttack= new Texture("lvl1_spikesmanattack_animation.png");
            knightHit = new Texture("lvl1_spikesmanhit_animation.png");
            lvl=1;
        } else if (shield > 1 * KNIGHT_SELECTION_RATIO && shield <= 2 * KNIGHT_SELECTION_RATIO) {
            knightWalk = new Texture("lvl2_lord_animation.png");
            knightAttack = new Texture("lvl2_lordattack_animation.png");
            knightHit = new Texture("lvl2_lordhit_animation.png");
            lvl=2;
        } else if (shield > 2 * KNIGHT_SELECTION_RATIO && shield <= 3 * KNIGHT_SELECTION_RATIO){
            knightWalk = new Texture("lvl3_dragonrider_animation.png");
            knightAttack= new Texture("lvl3_dragonriderattack_animation.png");
            knightHit = new Texture("lvl3_dragonriderhit_animation.png");//C:\Users\dagut\Documents\Castle Wars\CastleWars\android\assets\lvl3_dragonriderhit_animation.png
            lvl=3;
        }else{
            knightWalk = new Texture("lvl4_dragon_animation.png");
            knightAttack= new Texture("lvl4_dragonattack_animation.png");
            knightHit = new Texture("lvl4_dragonhit_animation.png");
            lvl=4;
        }
        knight = knightWalk;
        knightAnimation = new Animation( (knight), 4, 0.5f);


        health = shield*health;
        damage = damage * shield;
        boundsKnight = new Rectangle(position.x,position.y,knightAnimation.getFrame().getRegionWidth(),knightAnimation.getFrame().getRegionHeight());
    }

    public void update(float dt){
        //Gdx.app.log("DT",dt+"");
        if(kill>1){
            dispose();
        }
        knightAnimation.update(dt);
        velocity.set(0, speed, 0);
        velocity.scl(dt);
        position.add(0,velocity.y,0);
        velocity.scl(1/dt);

        boundsKnight.setPosition(position.x,position.y);


    }
    public boolean hit(int damage){
        health -= damage;
        streak=45;
        if(health <= 0){
            kill++;
            return true;
        }
        return false;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public TextureRegion getKnight() {
        if(kill==1){
            return new TextureRegion(knightKilled);
        }else if(streak>0){
            streak--;
            return new TextureRegion(knightHit);
        }
        return knightAnimation.getFrame();
    }

    public Texture getKnightTexture() {
        return knight;
    }


    public void setKnight(Texture knight) {
        this.knight = knight;
    }

    public Rectangle getBoundsKnight() {
        return boundsKnight;
    }

    public void dispose(){
        knightHit.dispose();
        knightWalk.dispose();
        knightAttack.dispose();
        //knightKilled.dispose();
        knight.dispose();
        lvl1.dispose();
        lvl2.dispose();
        lvl3.dispose();
        lvl4.dispose();
    }

    public void startAttack(){
        knight.dispose();
        knightWalk.dispose();
        knightAnimation = null;
        int framesCount = 0;
        if(lvl ==1 || lvl ==4){
            lvl1.play();
            framesCount=10;
            if(lvl == 4){
                lvl4.play();

            }
            framesCount=10;
        }else if(lvl == 2){
            lvl2.play();
            framesCount = 9;
        }else if(lvl == 3){
            lvl3.play();
            framesCount = 8;
        }
        knightAnimation = new Animation(knightAttack,framesCount,1.625f);
        boundsKnight = new Rectangle(position.x,position.y,knightAnimation.getFrame().getRegionWidth(),knightAnimation.getFrame().getRegionHeight());
        speed = 0;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
