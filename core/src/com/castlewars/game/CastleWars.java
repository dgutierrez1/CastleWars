package com.castlewars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.castlewars.game.processor.Processor;
import com.castlewars.game.states.GameStateManager;
import com.castlewars.game.states.MenuState;
import com.badlogic.gdx.audio.Sound;
import com. badlogic.gdx.audio.Music;

public class CastleWars extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "CastleWars";

	private GameStateManager gsm;
	private SpriteBatch batch;
	private Music sonidoFondo;




	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		sonidoFondo=Gdx.audio.newMusic(Gdx.files.internal("audiocortado.mp3"));
		sonidoFondo.setLooping(true);
		sonidoFondo.setVolume(0.5f);
		sonidoFondo.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		//Processor p = new Processor();
		//Gdx.input.setInputProcessor(p);



	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		sonidoFondo.dispose();
        batch.dispose();

	}
}
