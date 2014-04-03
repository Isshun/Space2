package com.mojang.metagun.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class View {

	public static interface OnClickListener {
		void onClick();
	}
	
	protected int 				mPosX;
	protected int 				mPosY;
	protected int 				mWidth;
	protected int 				mHeight;
	private OnClickListener mOnClickListener;
	
	public View (int x, int y) {
		mPosX = x;
		mPosY = y;
	}

	public boolean isClickable () {
		return true;
	}

	public boolean contains (int x, int y) {
		return x >= mPosX && x <= mPosX + mWidth && y >= mPosY && y <= mPosY + mHeight;
	}

	public void click () {
		if (mOnClickListener != null) {
			mOnClickListener.onClick();
		}
	}

	public abstract void draw (SpriteBatch spriteBatch);

	public void setOnClickListener (OnClickListener onClickListener) {
		mOnClickListener = onClickListener;
	}

}
