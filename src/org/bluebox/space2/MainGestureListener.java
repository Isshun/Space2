package org.bluebox.space2;

import org.bluebox.space2.screen.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MainGestureListener implements GestureListener {

	private Screen 	mScreen;
	private int 		mLastTouchX;
	private int 		mLastTouchY;
	private Game 		mGame;
	private boolean 	mIsMoving;

	public MainGestureListener (Game game) {
		mGame = game;
	}

	public void setScreen (Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean touchDown (float x, float y, int pointer, int button) {
		int x2 = (int)(x * Constants.GAME_WIDTH / Gdx.graphics.getWidth());
		int y2 = (int)(y * Constants.GAME_HEIGHT / Gdx.graphics.getHeight());

		mLastTouchX = (int)x2;
		mLastTouchY = (int)y2;

		return false;
	}

	@Override
	public boolean tap (float x, float y, int count, int button) {
		System.out.println("tap: " + (int)x + " x " + (int)y);
		
		int x2 = (int)(x * Constants.GAME_WIDTH / Gdx.graphics.getWidth());
		int y2 = (int)(y * Constants.GAME_HEIGHT / Gdx.graphics.getHeight());

		if (mScreen != null) {
			mScreen.tap(x2, y2);
		}
		
		return false;
	}

	@Override
	public boolean longPress (float x, float y) {
		System.out.println("longpress");

		int x2 = (int)(x * Constants.GAME_WIDTH / Gdx.graphics.getWidth());
		int y2 = (int)(y * Constants.GAME_HEIGHT / Gdx.graphics.getHeight());

		if (mScreen != null) {
			mScreen.onLongTouch((int)x2, (int)y2);
		}
		
		return false;
	}

	@Override
	public boolean fling (float velocityX, float velocityY, int button) {
		System.out.println("fling: " + velocityX + " x " + velocityY);
		
		if (velocityX > 1000) {
			mScreen.onPrev();
		} else if (velocityX < -1000) {
			mScreen.onNext();
		}

		return false;
	}

	@Override
	public boolean pan (float x, float y, float deltaX, float deltaY) {
		System.out.println("pan");

		int x2 = (int)(x * Constants.GAME_WIDTH / Gdx.graphics.getWidth());
		int y2 = (int)(y * Constants.GAME_HEIGHT / Gdx.graphics.getHeight());

		if (mScreen != null) {
			mScreen.onMove((int)x2 - mLastTouchX, (int)y2 - mLastTouchY);
		}
		
		mLastTouchX = (int)x2;
		mLastTouchY = (int)y2;
		mIsMoving = true;
		
		return false;
	}

	@Override
	public boolean panStop (float x, float y, int pointer, int button) {
		System.out.println("pan stop");

		mIsMoving = false;
		
		return false;
	}

	@Override
	public boolean zoom (float initialDistance, float distance) {
		System.out.println("zoom");

		return false;
	}

	@Override
	public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		System.out.println("pinch");
		
		mScreen.onBack();
		mGame.goBack();

		return false;
	}

	public boolean isMoving () {
		return mIsMoving;
	}

}
