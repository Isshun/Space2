
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
	private int mPosX;
	private int mPosY;

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
	public void onTouch (int x, int y) {
		SystemModel system = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
		if (system != null) {
			System.out.println("Open system: " + system.getName());
			setScreen(new SystemScreen(system));
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mPosX += offsetX;
		mPosY += offsetY;
	}

}
