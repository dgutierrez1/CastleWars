package com.castlewars.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlewars.game.CastleWars;

/**
 * Created by Brent on 6/25/2015.
 */
public class MenuState extends State{
    Texture background;
    Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false,CastleWars.WIDTH/2, CastleWars.HEIGHT/2);

        background = new Texture("menu_bg.jpg");
        playBtn = new Texture("play_btn.png");
    }

    @Override
    public void handleInput() {
       if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
           dispose();
        }
    }

    @Override
    public void update(float dt) {

        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, (CastleWars.WIDTH / 2) - (playBtn.getWidth() / 2), CastleWars.HEIGHT / 6);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
