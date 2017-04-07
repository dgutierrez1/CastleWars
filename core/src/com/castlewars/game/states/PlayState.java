package com.castlewars.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.castlewars.game.CastleWars;
import com.castlewars.game.sprites.Bullet;
import com.castlewars.game.sprites.Castle;
import com.castlewars.game.sprites.Knight;
import com.castlewars.game.sprites.Lane;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;



public class PlayState extends State {


    private Array<Lane> lanes;
    private static final int LANE_WIDTH = (CastleWars.WIDTH/2)/4;
    private  static final int KNIGHT_DEPLOYMENT_TIME_SCALE = 30;


    private int pressedCounter;
    private boolean touched;
    private boolean touchState;
    private int xTouch;
    private int yTouch;
    private Castle castle;


    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private boolean timeUp;

    private Label countDownLabel;
    private Label timeLabel;


    private boolean gameover;

    public PlayState(GameStateManager gsm){
        super(gsm);
        castle = new Castle(CastleWars.HEIGHT/2);
        lanes = new Array<Lane>();
        int xAxis = 0;

        viewport = new FitViewport(CastleWars.WIDTH, CastleWars.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());

        worldTimer = 60;
        timeCount = 0;

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);


        table.add(timeLabel).expandX().padTop(10);


        table.row();
        table.add(countDownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        for(int i = 1; i < 5; i++){
            Lane lane = new Lane(i,xAxis,castle);
            lanes.add(lane);
            xAxis+= 60;
        }

        cam.setToOrtho(false,CastleWars.WIDTH/2, CastleWars.HEIGHT/2);
        touched = false;
        touchState = false;
        gameover = false;
        xTouch = 0;
        yTouch = 0;
    }
    @Override
    public void handleInput() {
        yTouch = Gdx.input.getY();

        if(CastleWars.HEIGHT/2 <= yTouch/2  ){
            if(Gdx.input.isTouched()){
                touchState = true;
                touched = true;
                xTouch = Gdx.input.getX();
                pressedCounter++;

            }else{
                touchState = false;
            }

            if(touched && !touchState){
                selectedKnightLane(xTouch/4,yTouch/4,pressedCounter);
                //Gdx.app.log("TOUCH", xTouch + "-" + yTouch);
                pressedCounter = 0;
                touchState = false;
                touched = false;
            }
        }else{
            if(Gdx.input.justTouched()){
                yTouch = Gdx.input.getY();
                xTouch = Gdx.input.getX();
                selectedBulletLane(xTouch/4, yTouch/4);
                //Gdx.app.log("TOUCH", xTouch + "-" + yTouch);
            }
        }
    }

    @Override
    public void update(float dt) {
        //Gdx.app.log("DT",dt+"");

        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        handleInput();
        for (Lane ln: lanes) {
            ln.update(dt);
        }
        if(castle.getHealth()<0){
            gsm.set(new MenuState(gsm));
            dispose();
        }

    }
    public void selectedBulletLane(int x, int y){
        for (Lane ln: lanes) {
            if (ln.getStartX() < x && x <= ln.getEndX()) {
                Bullet bl = new Bullet(ln.getStartX(), ln.getEndY()-13);
                ln.deployBullet(bl);
                //Gdx.app.log("BULLET X:", x+"");
                //Gdx.app.log("SELECTED  BULLET LANE", ln.getLaneId()+"");
            }
        }
    }
    public  void selectedKnightLane(int x , int y, int pressTime){
        for (Lane ln: lanes) {
            if (ln.getStartX() < x && x <= ln.getEndX()) {
                Knight kn = new Knight(ln.getStartX(), ln.getEndY()/10, pressTime/KNIGHT_DEPLOYMENT_TIME_SCALE);
                ln.deployKnight(kn);
                //Gdx.app.log("ID: ", ln.getLaneId()+"");
                //Gdx.app.log("KNIGHT X:", x+"");
                //Gdx.app.log("SELECTED KNIGHT LANE", ln.getLaneId()+"");

            }
        }
    }




    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();



        for (Lane ln:lanes) {
            sb.draw(ln.getLane(), ln.getStartX(), 0);
            sb.draw(castle.getCastle(),castle.getPosition().x,castle.getPosition().y);
        }
        for (Lane ln:lanes) {
            for (Knight kn:ln.getKnights()) {
                sb.draw(kn.getKnight(),(ln.getStartX() + LANE_WIDTH/2) - kn.getKnight().getRegionWidth()/2 ,kn.getPosition().y);
            }
            for (Knight kn:ln.getAttackingKnights()) {
                sb.draw(kn.getKnight(),(ln.getStartX() + LANE_WIDTH/2) - kn.getKnight().getRegionWidth()/2 ,kn.getPosition().y);
            }
            for (Bullet bl: ln.getBullets()) {
                sb.draw(bl.getBullet(),ln.getStartX() + ((LANE_WIDTH/2) - (bl.getBullet().getWidth()/2)), bl.getPosition().y);
            }

        }

        sb.end();
    }

    @Override
    public void dispose() {
        for (Lane ln: lanes) {
            ln.dispose();
        }
        castle.getCastle().dispose();

    }
}
