
package com.mojang.metagun;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mojang.metagun.screen.PauseScreen;
import com.mojang.metagun.screen.Screen;
import com.mojang.metagun.screen.SpaceScreen;
import com.mojang.metagun.service.GameService;

public class Game implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private LinkedList<Screen> mScreens;
	private boolean running = false;
	private Screen screen;
	private final boolean started = false;
	private float accum = 0;
	private boolean mMenuIsOpen;

	@Override
	public void create () {
		
		Art.load();
		Sound.load();
		running = true;
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		mScreens = new LinkedList<Screen>();
		
		GameService.getInstance().initDebug();

		setScreen(new SpaceScreen());
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

	public void addScreen (Screen newScreen) {
		mScreens.add(screen);
		setScreen(newScreen);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			screen.tick();
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

	public void goBack () {
		System.out.println("Go back");
		Screen s = mScreens.pollLast();
		if (s != null) {
			setScreen(s);
		} else {
			if (mMenuIsOpen) {
				setScreen(new PauseScreen(screen));
			} else {
				setScreen(new SpaceScreen());
			}
			mMenuIsOpen = !mMenuIsOpen;
		}
	}

	public List<Screen> getHistoryScreen () {
		return mScreens;
	}
}