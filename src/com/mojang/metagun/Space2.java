
package com.mojang.metagun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mojang.metagun.screen.Screen;
import com.mojang.metagun.screen.SpaceScreen;
import com.mojang.metagun.screen.SystemScreen;
import com.mojang.metagun.screen.TitleScreen;
import com.mojang.metagun.service.GameService;

public class Space2 implements ApplicationListener {
	public static final int GAME_WIDTH = 320;
	public static final int GAME_HEIGHT = 240;
	public static final int SCREEN_SCALE = 2;

	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Screen screen;
	private final Input input = new Input();
	private final boolean started = false;
	private float accum = 0;

	@Override
	public void create () {
		
		GameService.getInstance().initDebug();
		
		Art.load();
		Sound.load();
		Gdx.input.setInputProcessor(input);
		running = true;
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		setScreen(new SystemScreen(GameService.getInstance().getSystems().get(0)));
	}

	@Override
	public void pause () {
		running = false;
	}

	@Override
	public void resume () {
		running = true;
	}

	public void setScreen (Screen newScreen) {
		if (screen != null) screen.removed();
		screen = newScreen;
		if (screen != null) screen.init(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			screen.tick(input);
			input.tick();
			accum -= 1.0f / 60.0f;
		}
		screen.render();
// batch.begin();
// font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
// batch.end();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void dispose () {
	}
}
