package com.mojang.metagun;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mojang.metagun.screen.Screen;

public class MyInputProcessor implements InputProcessor {
	private long mTouch;
	private boolean mIsMoving;
	private int mTouchX;
	private int mTouchY;
	private boolean mIsLongTouch;
	private Screen mScreen;
	private Game mGame;

	public MyInputProcessor(Game game) {
		mGame = game;
	}

	public void setScreen (Screen screen) {
		mScreen = screen;
	}

   @Override
   public boolean keyDown (int keycode) {
      return false;
   }

   @Override
   public boolean keyUp (int keycode) {
   	if (keycode == Keys.BACK || keycode == Keys.BACKSPACE) {
			mScreen.onBack();
			mGame.goBack();
   	}
   	
   	return false;
//		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
//			onBack();
//			mGame.goBack();
//			mScreenTime = 0;
//			return;
//		}
   }

   @Override
   public boolean keyTyped (char character) {
      return false;
   }

   @Override
   public boolean touchDown (int x, int y, int pointer, int button) {
//		System.out.println("is touch down");
//		
//		mTouch = System.currentTimeMillis();
//		mTouchX = x;
//		mTouchY = y;
//		mIsLongTouch = false;
//		mIsMoving = false;
//
		return false;
   }

   @Override
   public boolean touchUp (int x, int y, int pointer, int button) {
//		System.out.println("is touch up");
//
		return false;
   }

   @Override
   public boolean touchDragged (int x, int y, int pointer) {
//		long time = System.currentTimeMillis();
//
//		if (mIsLongTouch == false && mIsMoving == false && (Math.abs(mTouchX - x) > 5 || Math.abs(mTouchY - y) > 5)) {
//			mIsMoving = true;
//		}
//		
//		if (mIsMoving) {
//			System.out.println("is touch moving");
//		}
//		
//		else if (mIsLongTouch == false && time - mTouch > 150) {
//			mIsLongTouch = true;
//			System.out.println("is touch long");
//		}
//
		return false;
   }

   @Override
   public boolean scrolled (int amount) {
      return false;
   }

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}
}