
package com.mojang.metagun;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mojang.metagun.model.PlanetModel;
import com.mojang.metagun.model.PlayerModel;
import com.mojang.metagun.screen.ErrorScreen;
import com.mojang.metagun.screen.PauseScreen;
import com.mojang.metagun.screen.Screen;
import com.mojang.metagun.screen.SpaceScreen;
import com.mojang.metagun.service.GameService;
import com.mojang.metagun.service.PathResolver;

public class Game implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private LinkedList<Screen> mScreens;
	private boolean running = false;
	private Screen screen;
	private final boolean started = false;
	private float accum = 0;
	private boolean mMenuIsOpen;
	private int mCycle;
	private double mGameTime;

	private static boolean sNeedRendering;

	public static long sRender;

	@Override
	public void create () {
		
		Art.load();
		Sound.load();
		running = true;
		
		double r1 = 320f / Gdx.graphics.getHeight();
		double r2 = 480f / Gdx.graphics.getWidth();
		System.out.println("window ratio: " + r1 + ", " + r2);

		int ratio = 1;
		for (int i = 2; i < 10; i++) {
			System.out.println("window i: " + (Gdx.graphics.getWidth() / i));
			if ((Gdx.graphics.getWidth() / i) > 600) {
				ratio = i;
			}
		}
		
		Constants.GAME_WIDTH = (int)(Gdx.graphics.getWidth() / ratio);
		Constants.GAME_HEIGHT = (int)(Gdx.graphics.getHeight() / ratio);
		
		System.out.println("window: " + Constants.GAME_WIDTH + " x " + Constants.GAME_HEIGHT);

		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		mScreens = new LinkedList<Screen>();
		
		GameService.getInstance().initDebug(0);

		PathResolver.getInstance().getPath(GameService.getInstance().getPlayers().get(0).getHome().getSystem(), GameService.getInstance().getPlayer().getHome().getSystem());
		
		if (Constants.GAME_WIDTH > 480) {
			
		}
		
		if (Constants.GAME_WIDTH < 380 || Constants.GAME_HEIGHT < 240) {
			setScreen(new ErrorScreen(ErrorScreen.RESOLUTION_NOT_SUPPORTED));
		} else {
			setScreen(new SpaceScreen());
		}
		
		Timer timer = new Timer();
		timer.scheduleTask(new Task() {
			@Override
			public void run () {
				update();
			}
		}, Constants.UPDATE_INTERVAL, Constants.UPDATE_INTERVAL);
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
		if (screen != null) screen.init(this, (int)mGameTime);
	}

	public void addScreen (Screen newScreen) {
		mScreens.add(screen);
		setScreen(newScreen);
	}

	public boolean isTop (Screen screen) {
		return mScreens.getLast() != screen;
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		//mGameTime += (accum * 1000);
		//System.out.println("" + accum);
		while (accum > 1.0f / 60.0f) {
			screen.tick((int)mGameTime, mCycle);
			accum -= 1.0f / 60.0f;
			mGameTime += (1.0f / 60.0f * 1000);
		}
		screen.render((int)mGameTime, mCycle, sRender);
// batch.begin();
// font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
// batch.end();
	}

	void update () {
		List<PlanetModel> planets = GameService.getInstance().getPlanets();
		for (PlanetModel planet: planets) {
			planet.update();
		}

		List<PlayerModel> players = GameService.getInstance().getPlayers();
		for (PlayerModel player: players) {
			player.update();
		}

		mCycle++;	
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void dispose () {
	}

	public Screen goBack () {
		System.out.println("Go back");
		Screen s = mScreens.pollLast();
		if (s != null) {
			setScreen(s);
		} else {
			if (mMenuIsOpen) {
				s = new PauseScreen(screen);
				setScreen(s);
			} else {
				s = new SpaceScreen();
				setScreen(s);
			}
			mMenuIsOpen = !mMenuIsOpen;
		}
		return s;
	}

	public List<Screen> getHistoryScreen () {
		return mScreens;
	}

	public static void requestRendering () {
		//Gdx.graphics.requestRendering();
		sNeedRendering = true;
	}
}
