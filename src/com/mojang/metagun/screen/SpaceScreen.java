
package com.mojang.metagun.screen;

import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
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
		mSpriteBatch.begin();
		
		System.out.println(mPosX / 2 % 320);
		
		int posX = mPosX / 2 % 320;
		int posY = mPosY / 2 % 240;
		
		draw(Art.bg, posX - 320, posY - 240);
		draw(Art.bg, posX, posY - 240);
		draw(Art.bg, posX + 320, posY - 240);

		draw(Art.bg, posX - 320, posY);
		draw(Art.bg, posX, posY);
		draw(Art.bg, posX + 320, posY);

		draw(Art.bg, posX - 320, posY + 240);
		draw(Art.bg, posX, posY + 240);
		draw(Art.bg, posX + 320, posY + 240);

		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system : systems) {
			draw(Art.system, mPosX + system.getX(), mPosY + system.getY());
			String name = system.getName();
			drawString(name, mPosX + system.getX() + Constants.SYSTEM_SIZE / 2 - name.length() * 3, mPosY + system.getY() + Constants.SYSTEM_SIZE + 6);
		}
		
		drawGeneralInfos(6, 6);
		
		mSpriteBatch.end();
	}

	private void drawGeneralInfos (int posX, int posY) {
		draw(Art.flag_planets, 6, 6);
		drawString("GOV.", posX + 2, posY + 34);

		draw(Art.flag_relations, 44, 6);
		drawString("RELS.", posX + 40, posY + 34);

		draw(Art.map, Space2.GAME_WIDTH - 64 - 6, 6);
		drawRectangle(Space2.GAME_WIDTH - 64 - 6 - mPosX / 50, 6 - mPosY / 50, 14, 10, Color.rgba8888(0.5f, 0.5f, 0.8f, 0.8f));
		drawString("Cycle:  42", Space2.GAME_WIDTH - 64 - 4, posY + 49);
	}

	@Override
	public void onTouch (int x, int y) {

		// bt government
		if (x >= 6 && x <= 6 + 32 && y >= 2 && y <= 44) {
			addScreen(new PanelGovernmentScreen());
		}
		
		// bt relations
		if (x >= 44 && x <= 44 + 32 && y >= 2 && y <= 44) {
			addScreen(new PanelRealtionScreen());
		}
		
		SystemModel system = GameService.getInstance().getSystemAtPos(x - mPosX, y - mPosY);
		if (system != null) {
			addScreen(new SystemScreen(system));
		}
	}

	@Override
	public void onMove (int offsetX, int offsetY) {
		mPosX += offsetX;
		mPosY += offsetY;
	}

}
