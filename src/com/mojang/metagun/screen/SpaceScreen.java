
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.mojang.metagun.Art;
import com.mojang.metagun.Constants;
import com.mojang.metagun.Input;
import com.mojang.metagun.Space2;
import com.mojang.metagun.Sound;
import com.mojang.metagun.model.SystemModel;
import com.mojang.metagun.service.GameService;

public class SpaceScreen extends Screen {
	private int time = 0;
	private int mLastPosX = -1;
	private int mLastPosY = -1;
	private int mPosX;
	private int mPosY;
	private int mTouch = -1;
	private int mTouchX;
	private int mTouchY;

	@Override
	public void render () {
		spriteBatch.begin();
		draw(Art.bg, mPosX / 2, mPosY / 2);

		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system : systems) {
			draw(Art.system, mPosX + system.getX(), mPosY + system.getY());
			String name = system.getName();
			drawString(name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6);
		}
		
		spriteBatch.end();
	}

	@Override
	public void tick (Input input) {
		time++;
			if (time > Constants.TOUCH_RECOVERY && (input.buttons[Input.SHOOT] && !input.oldButtons[Input.SHOOT] || Gdx.input.isTouched())) {

				int x = Gdx.input.getX() * Space2.GAME_WIDTH / Gdx.graphics.getWidth();
				int y = Gdx.input.getY() * Space2.GAME_HEIGHT / Gdx.graphics.getHeight();
				
				if (mTouch == -1) {
					mTouch = time;
					mTouchX = x;
					mTouchY = y;
				}
				
				if (mLastPosX != -1) {
					mPosX += mLastPosX - x;
					mPosY += mLastPosY - y;
				}
				mLastPosX = x;
				mLastPosY = y;
				
				input.releaseAllKeys();
		}
			
			if (Gdx.input.isTouched() == false && mTouch != -1) {
				mLastPosX = -1;

				if (mTouch + 60 > time) {
					SystemModel system = GameService.getInstance().getSystemAtPos(mTouchX, mTouchY);
					if (system != null) {
						System.out.println("Open system: " + system.getName());
						setScreen(new SystemScreen(system));
					}
				}
				mTouch = -1;
			}
	}

}
