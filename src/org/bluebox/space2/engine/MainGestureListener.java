package org.bluebox.space2.engine;

import org.bluebox.space2.engine.screen.BaseScreen;
import org.bluebox.space2.game.Constants;
import org.bluebox.space2.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MainGestureListener implements GestureListener {

	private static float RATIO_X;
	private static float RATIO_Y;
	
	private BaseScreen 	mScreen;
	private int 		mLastTouchX;
	private int 		mLastTouchY;
	private Game 		mGame;
	private boolean 	mIsMoving;
	private boolean 	mIsPinch;
	private int mTouchX;
	private int mTouchY;

	public MainGestureListener (Game game) {
		RATIO_X = (float)Constants.GAME_WIDTH / Gdx.graphics.getWidth();
		RATIO_Y = (float)Constants.GAME_HEIGHT / Gdx.graphics.getHeight();
		
		mGame = game;
	}

	public void setScreen (BaseScreen screen) {
		mScreen = screen;
	}

	@Override
	public boolean touchDown (float x, float y, int pointer, int button) {
		mTouchX = mLastTouchX = (int)(x * RATIO_X);
		mTouchY = mLastTouchY = (int)(y * RATIO_Y);

		return false;
	}

	@Override
	public boolean tap (float x, float y, int count, int button) {
		System.out.println("tap: " + (int)x + " x " + (int)y);
		
		int x2 = (int)(x * RATIO_X);
		int y2 = (int)(y * RATIO_Y);

		if (mScreen != null) {
			mScreen.tap(x2, y2);
		}
		
		return false;
	}

	@Override
	public boolean longPress (float x, float y) {
		System.out.println("longpress");

		int x2 = (int)(x * RATIO_X);
		int y2 = (int)(y * RATIO_Y);

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
		
		if (velocityY > 1000) {
			mScreen.onDown(mTouchX, mTouchY);
		} else if (velocityY < -1000) {
			mScreen.onUp(mTouchX, mTouchY);
		}

		return false;
	}

	@Override
	public boolean pan (float x, float y, float deltaX, float deltaY) {
		if (mIsPinch) {
			return false;
		}

		System.out.println("pan");

		int x2 = (int)(x * RATIO_X);
		int y2 = (int)(y * RATIO_Y);

		if (mScreen != null) {
			mScreen.onMove(mTouchX, mTouchY, (int)x2 - mLastTouchX, (int)y2 - mLastTouchY);
		}
		
		mLastTouchX = (int)x2;
		mLastTouchY = (int)y2;
		mIsMoving = true;
		
		return false;
	}

	@Override
	public boolean panStop (float x, float y, int pointer, int button) {
		System.out.println("pan stop");

		int x2 = (int)(x * RATIO_X);
		int y2 = (int)(y * RATIO_Y);

		if (mIsPinch == false && mScreen != null) {
			mScreen.onMoveEnd(x2, y2);
		}

		mIsMoving = false;
		mIsPinch = false;
		
		return false;
	}

	@Override
	public boolean zoom (float initialDistance, float distance) {
		System.out.println("zoom: " + distance);

		return false;
	}

	@Override
	public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		int initDistance = (int)Math.abs(initialPointer1.x - initialPointer2.x);
		int distance = (int)Math.abs(pointer1.x - pointer2.x);
		
		if (distance > initDistance) {
			mScreen.onZoom();
		} else {
			mScreen.onPinch();
		}
		
		mIsPinch = true;
		
		System.out.println("pinch: " + initDistance + ", " + distance);
		
		return false;
	}

	public boolean isMoving () {
		return mIsMoving;
	}

}
