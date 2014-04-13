package org.bluebox.space2.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bluebox.space2.engine.Art;
import org.bluebox.space2.engine.IArtManager;
import org.bluebox.space2.engine.MainGestureListener;
import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.engine.screen.BaseScreenLayer;
import org.bluebox.space2.game.ai.AI;
import org.bluebox.space2.game.model.PlanetModel;
import org.bluebox.space2.game.model.PlayerModel;
import org.bluebox.space2.game.screen.ErrorScreen;
import org.bluebox.space2.game.screen.FleetCreateScreen;
import org.bluebox.space2.game.screen.PauseScreen;
import org.bluebox.space2.game.screen.SpaceScreen;
import org.bluebox.space2.game.service.GameService;
import org.bluebox.space2.path.PathResolver;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Game implements ApplicationListener {
	private static final long 			serialVersionUID = 1L;

	private static boolean 				sNeedRendering;
	private static float					sRender;
	public static Random 				sRandom;
	private static boolean 				sNotifyChange;
	

	private LinkedList<BaseScreen> 	mScreens;
	private boolean 						mRunning = false;
	private BaseScreen 					mScreen;
	private final boolean				mStarted = false;
	private float 							mAccum = 0;
	private boolean 						mMenuIsOpen;
	private int 							mCycle;
	private double 						mGameTime;
	private long 							mLastBack;
	private MainGestureListener 		mGestureListener;
	private BaseScreen 					mOffScreen;
	private BaseScreenLayer 			mScreenBG;
	private GameData						mData;
	private int 							mRenderCount;
	protected Timer 						mTimer;
	private long 							mLastRender;

	private IArtManager mArt;

	private TextureRegion mBg;

	public enum Anim {
		NO_TRANSITION,
		FLIP_LEFT,
		FLIP_RIGHT,
		FLIP_BOTTOM,
		FLIP_TOP,
		GO_DOWN
	}

	public Game(IArtManager art) {
		mArt = art;
	}
	
	@Override
	public void create () {
		Art.init(mArt);
		//Sound.init();
		mRunning = true;
		
		mBg = Art.bg;

		sRandom = new Random(42);
		
		double r1 = 320f / Gdx.graphics.getHeight();
		double r2 = 480f / Gdx.graphics.getWidth();
		System.out.println("window ratio: " + r1 + ", " + r2);

		int ratio = 1;
		for (int i = 2; i < 10; i++) {
			System.out.println("window i: " + (Gdx.graphics.getWidth() / i));
			if ((Gdx.graphics.getWidth() / i) > 320) {
				ratio = i;
			}
		}
		
		Constants.GAME_WIDTH = (int)(Gdx.graphics.getWidth() / ratio);
		Constants.GAME_HEIGHT = (int)(Gdx.graphics.getHeight() / ratio);
		
		System.out.println("window: " + Constants.GAME_WIDTH + " x " + Constants.GAME_HEIGHT);

		initInput();
		
		mScreenBG = BaseScreenLayer.create(BaseScreenLayer.DYNAMIC);
		
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		mScreens = new LinkedList<BaseScreen>();

		mData = GameDataFactory.create();
		GameService.getInstance().setData(mData);
		
		GameService.getInstance().initDebug(0);

		PathResolver.getInstance().init(GameService.getInstance().getSystems(), GameService.getInstance().getTraveLines());

		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0, -1, 1);

		if (Constants.GAME_WIDTH > 480) {
			
		}
		
		if (Constants.GAME_WIDTH < 380 || Constants.GAME_HEIGHT < 240) {
			setScreen(new ErrorScreen(ErrorScreen.RESOLUTION_NOT_SUPPORTED));
		} else {
			setScreen(new SpaceScreen());
//			setScreen(new PanelCreateFleet(GameService.getInstance().getPlayer().getHome().getDock()));
		}
		
		
		mTimer = new Timer();
		mTimer.scheduleTask(new Task() {
			@Override
			public void run () {
				update();
			}
		}, Constants.UPDATE_INTERVAL, Constants.UPDATE_INTERVAL);
	}

	private void initInput () {
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		
		mGestureListener = new MainGestureListener(this);
		
      GestureDetector gd = new GestureDetector(20f, 0.4f, 0.6f, 0.15f, mGestureListener);
      Gdx.input.setInputProcessor(gd);
	}

	@Override
	public void pause () {
		mRunning = false;
	}

	@Override
	public void resume () {
		mRunning = true;
	}

	public void setScreen (BaseScreen newScreen) {
//		if (mScreen != null) mScreen.dispose();
		mScreen = newScreen;
		mGestureListener.setScreen(newScreen);
		if (mScreen != null) mScreen.init(this, (int)mGameTime);
	}

	public void addScreen (BaseScreen newScreen) {
		mScreens.add(mScreen);
		setScreen(newScreen);
	}

	public boolean isTop (BaseScreen screen) {
		return mScreens.isEmpty() || mScreens.getLast() != screen;
	}

	@Override
	public void render () {
		long time = System.currentTimeMillis();
//		if (time - mLastRender < 10) {
//			return;
//		}
//		mLastRender = time;
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mAccum += Gdx.graphics.getDeltaTime();
		//mGameTime += (accum * 1000);
		//System.out.println("" + accum);
		while (mAccum > 1.0f / 60.0f) {
			mScreen.tick((int)mGameTime, mCycle);
			mAccum -= 1.0f / 60.0f;
			mGameTime += (1.0f / 60.0f * 1000);
		}
		
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0, -1, 1);

		mScreenBG.setCamera(0, 0);
		mScreenBG.draw(0, 0, 1);
		mScreenBG.begin();
//		int width = mBg.getRegionWidth();
//		if (width < 0) width = -width;
		mScreenBG.draw(mBg, 0, 0);
		mScreenBG.end();
		
		if (sNotifyChange) {
			mScreen.notifyChange();
			sNotifyChange = false;
		}
		mScreen.render((int)mGameTime, mCycle, (int)sRender);
		
		if (mOffScreen != null) {
			if (mOffScreen.isEnded()) {
				mOffScreen.dispose();
				mOffScreen = null;
			} else {
				mOffScreen.render((int)mGameTime, mCycle, (int)sRender);
			}
		}
		
		// "fix" broken timers
		if (mRenderCount > 50 && mCycle == 0) {
			mTimer.clear();
			mTimer.scheduleTask(new Task() {
				@Override
				public void run () {
					update();
				}
			}, Constants.UPDATE_INTERVAL, Constants.UPDATE_INTERVAL);

			mRenderCount = 0;
		}
		
		mRenderCount++;
		
		sRender = (sRender * 7 + (System.currentTimeMillis() - time)) / 8;
		
// batch.begin();
// font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
// batch.end();
	}

	public void update () {
//		System.out.println("update");

		if (mGestureListener.isMoving()) {
			return;
		}

		//mScreen.tick((int)mGameTime, mCycle);
		
		List<PlanetModel> planets = GameService.getInstance().getPlanets();
		for (PlanetModel planet: planets) {
			planet.update();
		}

		List<PlayerModel> players = GameService.getInstance().getPlayers();
		for (PlayerModel player: players) {
			if (player.isAI()) {
				//AI.getInstance().play(player);
			}
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

	public BaseScreen goBack () {
		long time = System.currentTimeMillis();
		if (time - mLastBack < Constants.BACK_MIN_DELAY) {
			return null;
		}
		mLastBack = time;
		System.out.println("Go back");
		BaseScreen s = mScreens.pollLast();
		s.onReturn();
		if (s != null) {
			if (mScreen != null) {
				replaceScreen(mScreen, s, null);
			}

//			setScreen(s);
		} else {
			if (mMenuIsOpen) {
				s = new PauseScreen(mScreen);
				replaceScreen(mScreen, s, null);
//				setScreen(s);
			} else {
				s = new SpaceScreen();
				replaceScreen(mScreen, s, null);
//				setScreen(s);
			}
			mMenuIsOpen = !mMenuIsOpen;
		}
		return s;
	}

	public List<BaseScreen> getHistoryScreen () {
		return mScreens;
	}

	public static void requestRendering () {
		//Gdx.graphics.requestRendering();
		sNeedRendering = true;
	}

	public void replaceScreen (BaseScreen oldScreen, BaseScreen newScreen, Anim anim) {
		if (anim != null) {
			switch (anim) {
			case FLIP_LEFT:
				oldScreen.setOffset(0, -Constants.GAME_WIDTH);
				newScreen.setOffset(Constants.GAME_WIDTH, 0);
				break;
			case FLIP_RIGHT:
				oldScreen.setOffset(0, Constants.GAME_WIDTH);
				newScreen.setOffset(-Constants.GAME_WIDTH, 0);
				break;
			case GO_DOWN:
				oldScreen.setOffsetY(0, -Constants.GAME_HEIGHT);
	//			newScreen.setOffset(-Constants.GAME_WIDTH, 0);
				break;
			case FLIP_TOP:
				oldScreen.setOffset(0, Constants.GAME_WIDTH);
				newScreen.setOffset(-Constants.GAME_WIDTH, 0);
				break;
			}
		}
		
		if (mOffScreen != null) {
			mOffScreen.dispose();
			mOffScreen = null;
		}
		
		oldScreen.goOut(anim);
		
		mOffScreen = oldScreen;
		mScreen = newScreen;
		mGestureListener.setScreen(newScreen);
		if (mScreen != null) mScreen.init(this, (int)mGameTime);
	}

	public static void notifyChange () {
		sNotifyChange = true;
		
		System.out.println("========================================");
		System.out.println("========================================");
		System.out.println("============ GLOBAL CHANGE =============");
		System.out.println("========================================");
		System.out.println("========================================");
	}

	public BaseScreen getScreen () {
		return mScreen;
	}

	public void setBg (TextureRegion bg) {
		mBg = bg;
	}
}
